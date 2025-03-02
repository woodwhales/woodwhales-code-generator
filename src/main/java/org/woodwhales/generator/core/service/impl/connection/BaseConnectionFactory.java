package org.woodwhales.generator.core.service.impl.connection;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.woodwhales.generator.core.config.ColumnConfig;
import org.woodwhales.generator.core.config.TableConfig;
import org.woodwhales.generator.core.entity.Column;
import org.woodwhales.generator.core.entity.DataBaseInfo;
import org.woodwhales.generator.core.entity.TableInfo;
import org.woodwhales.generator.core.exception.GenerateException;
import org.woodwhales.generator.core.service.ConnectionFactory;
import org.woodwhales.generator.core.util.StringTools;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author woodwhales
 * @create 2020-10-09 22:26
 */
@Slf4j
public abstract class BaseConnectionFactory implements ConnectionFactory {

    @Autowired
    protected ColumnConfig columnConfig;

    @Autowired
    protected TableConfig tableConfig;

    private final String[] types = new String[] {"TABLE"};

    protected String getDbVersion(DatabaseMetaData metaData) throws SQLException {
        final String databaseProductName = metaData.getDatabaseProductName();
        final String databaseProductVersion = metaData.getDatabaseProductVersion();
        return String.format("%s %s", databaseProductName, databaseProductVersion);
    }

    /**
     * 创建数据库链接，如果抛出异常则链接失败
     * @param dataBaseInfo
     * @return
     * @throws Exception
     */
    @Override
    public Connection buildConnection(DataBaseInfo dataBaseInfo) throws Exception {
        Class.forName(dataBaseInfo.getDriverClassName());
        Connection connection;
        try {
            connection = DriverManager.getConnection(dataBaseInfo.getUrl(), dataBaseInfo.getProperties());
        } catch (SQLException exception) {
            log.error("cause by : {}", exception.getMessage(), exception);
            if (StringUtils.equals("28000", exception.getSQLState()) || exception.getErrorCode() == 1017) {
                throw new GenerateException("账号或密码不正确！");
            }
            throw new GenerateException("数据库链接失败！");
        }

        return connection;
    }

    @Override
    public void closeResource(Connection connection, ResultSet resultSet) throws SQLException {
        if(Objects.nonNull(resultSet)) {
            resultSet.close();
        }

        closeResource(connection);
    }

    @Override
    public void closeResource(ResultSet resultSet) throws SQLException {
        if(Objects.nonNull(resultSet)) {
            resultSet.close();
        }
    }

    @Override
    public void closeResource(Connection connection) throws SQLException {
        if(Objects.nonNull(connection)) {
            connection.close();
        }
    }

    /**
     * 格式化数据库表名称
     * @param dbTableName
     * @return
     */
    protected void fillNameAndPropertyName(TableInfo tableInfo, String dbTableName) {
        String tempTableName = StringTools.substringAfter(dbTableName, tableConfig.getPrefix());

        // 格式化表名
        tableInfo.setName(StringTools.upper(tempTableName));

        // 格式化表名首字母小写
        tableInfo.setPropertyName(StringTools.upperWithOutFirstChar(tempTableName));
    }

    /**
     * 获取主键名称集合
     * @param metaData
     * @param catalog
     * @param schema
     * @param dbTableName
     * @return
     * @throws SQLException
     */
    private List<String> getPrimaryKey(DatabaseMetaData metaData, String catalog,
                                         String schema, String dbTableName) throws SQLException {
        List<String> primaryKeys = new ArrayList<>();

        ResultSet primaryKeysResultSet = metaData.getPrimaryKeys(catalog, schema, dbTableName);

        try {

            while (primaryKeysResultSet.next()) {
                String keyName = primaryKeysResultSet.getString("COLUMN_NAME");
                primaryKeys.add(keyName);
            }

        } catch (SQLException e) {
            log.error("get primaryKeys happen exception, errorMsg = {}", e.getMessage(), e);
        } finally {
            closeResource(primaryKeysResultSet);
        }

        return primaryKeys;
    }

    protected void checkArgument(Connection connection, String schema, String dataBaseInfoKey) {
        Objects.requireNonNull(connection, "数据库链接不允许为空");
        Preconditions.checkArgument(isNotBlank(dataBaseInfoKey), "数据库连接信息缓存key不允许为空");
        Preconditions.checkArgument(isNotBlank(schema), "schema不允许为空");
    }

    @Override
    public List<TableInfo> listTables(Connection connection, String schema, String dataBaseInfoKey) throws SQLException {

        checkArgument(connection, schema, dataBaseInfoKey);

        List<TableInfo> tableInfoList;

        StopWatch stopWatch = new StopWatch("获取数据库表信息对象");

        try {
            DatabaseMetaData metaData = connection.getMetaData();
            final String catalog = connection.getCatalog();

            final Statement statement = connection.createStatement();
            tableInfoList = getTableInfoList(statement, metaData, catalog, schema, dataBaseInfoKey);

            for (TableInfo tableInfo : tableInfoList) {
                String dbTableName = tableInfo.getDbName();

                log.info("dbTableName => {}", dbTableName);

                stopWatch.start("dbTableName => " + dbTableName);

                // setKeys
                List<String> primaryKeys = getPrimaryKey(metaData, catalog, schema, dbTableName);
                tableInfo.setKeys(primaryKeys);

                // setColumns
                List<Column> columns = getColumns(metaData, schema, dbTableName, primaryKeys);
                tableInfo.setColumns(columns);

                // setKeyTypes
                List<String> primaryKeyTypes = getPrimaryKeyTypes(primaryKeys, columns);
                tableInfo.setKeyTypes(primaryKeyTypes);

                stopWatch.stop();
            }
        } finally {
            closeResource(connection);
            log.info(stopWatch.prettyPrint());
        }

        return tableInfoList;
    }

    public List<TableInfo> getTableInfoList(Statement statement, DatabaseMetaData metaData, String catalog,
                                             String schema, String dataBaseInfoKey) throws SQLException {
        List<TableInfo> tableInfoList = new ArrayList<>();

        ResultSet tableResultSet = metaData.getTables(catalog, schema, null, this.types);

        try {
            while (tableResultSet.next()) {
                String dbTableName = tableResultSet.getString("TABLE_NAME");
                String comment = tableResultSet.getString("REMARKS");

                TableInfo tableInfo = new TableInfo(dbTableName, dataBaseInfoKey);
                fillNameAndPropertyName(tableInfo, dbTableName);
                ResultSet rs = statement.executeQuery(String.format("show create table `%s`", dbTableName));
                if(rs.next()) {
                    tableInfo.setCreateTableSql(rs.getString(2));
                }
                tableInfo.setComment(comment);
                tableInfoList.add(tableInfo);
            }
        } finally {
            closeResource(tableResultSet);
        }

        return tableInfoList;
    }

    private List<String> getPrimaryKeyTypes(List<String> primaryKeys, List<Column> columns) {
        List<String> primaryKeyTypes = new ArrayList<>();
        for (String primaryKey : primaryKeys) {
            for (Column column : columns) {
                if(column.isPrimaryKey() && column.getDbName().equals(primaryKey)) {
                    primaryKeyTypes.add(column.getType());
                }
            }
        }

        return primaryKeyTypes;
    }

    /**
     * 获取当前数据库表的所有字段信息
     * @param metaData
     * @param schema
     * @param dbTableName
     * @return
     * @throws SQLException
     */
    private List<Column> getColumns(DatabaseMetaData metaData,
                                    String schema, String dbTableName, List<String> primaryKeys) throws SQLException {

        ResultSet columnResultSet = metaData.getColumns(schema, null, dbTableName, null);

        List<Column> columns = new ArrayList<>();

        try {

            while (columnResultSet.next()) {
                String columnName = columnResultSet.getString("COLUMN_NAME");
                String typeName = columnResultSet.getString("TYPE_NAME");
                String remarks = columnResultSet.getString("REMARKS");
                String defaultValue = columnResultSet.getString("COLUMN_DEF");
                int columnSize = columnResultSet.getInt("COLUMN_SIZE");
                boolean nullable = BooleanUtils.toBoolean(columnResultSet.getInt("NULLABLE"));
                String nullableString = BooleanUtils.toString(nullable, "是", "否");

                Column column = Column.builder()
                        .dbName(columnName)
                        .dbType(typeName)
                        .nullAble(nullable)
                        .nullableString(nullableString)
                        .columnSize(columnSize)
                        .defaultValue(defaultValue)
                        .comment(remarks).build();

                // 将数据库表的字段类型转成 java 变量类型
                column.setType(this.convertType(dbTableName, columnName, column.getDbType()));
                column.setName(StringTools.upperWithOutFirstChar(columnName));
                column.setPrimaryKey(checkPrimaryKey(columnName, primaryKeys));

                columns.add(column);
            }

        } catch (SQLException e) {
            log.error("get columns happen exception, errorMsg = {}", e.getMessage(), e);
        } finally {
            closeResource(columnResultSet);
        }

        return columns;
    }

    private boolean checkPrimaryKey(String columnName, List<String> keys) {
        if(CollectionUtils.isEmpty(keys)) {
            return false;
        }
        return keys.contains(columnName);
    }

    private String convertType(String dbTableName, String columnName, String dbType) {
        Map<String, String> typeMap = columnConfig.getType();
        String type = typeMap.get(dbType);

        if(isNotBlank(type)) {
            return type;
        }

        for(String key : typeMap.keySet()){
            if(StringUtils.containsIgnoreCase(dbType, key)) {
                type = typeMap.get(key);
            }
        }

        Preconditions.checkArgument(isNotBlank(type), "数据库表 = [%s] 数据字段 columnName = [%s] 的字段类型 = [%s] 未匹配", dbTableName, columnName, dbType);
        return type;
    }

}
