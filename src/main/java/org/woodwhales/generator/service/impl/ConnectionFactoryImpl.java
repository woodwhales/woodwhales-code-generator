package org.woodwhales.generator.service.impl;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woodwhales.generator.config.ColumnConfig;
import org.woodwhales.generator.config.TableConfig;
import org.woodwhales.generator.entity.Column;
import org.woodwhales.generator.entity.DataBaseInfo;
import org.woodwhales.generator.entity.TableInfo;
import org.woodwhales.generator.exception.GenerateException;
import org.woodwhales.generator.service.ConnectionFactory;
import org.woodwhales.generator.util.StringTools;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.8.29 17:15
 * @description:
 */
@Slf4j
@Service
public class ConnectionFactoryImpl implements ConnectionFactory {

    @Autowired
    private ColumnConfig columnConfig;

    @Autowired
    private TableConfig tableConfig;

    /**
     * 创建数据库链接，如果抛出异常则链接失败
     * @param dataBaseInfo
     * @return
     * @throws Exception
     */
    @Override
    public Connection buildConnection(DataBaseInfo dataBaseInfo) throws Exception {
        Class.forName(dataBaseInfo.getDriveClassName());
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
    public void closeResource(Connection connection) throws SQLException {
        if(Objects.nonNull(connection)) {
            connection.close();
        }
    }

    @Override
    public List<String> listSchemas(Connection connection) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();

        ResultSet resultSet = metaData.getCatalogs();

        List<String> schemaList = new ArrayList<>();
        while (resultSet.next()) {
            schemaList.add(resultSet.getString(1));
        }

        closeResource(connection, resultSet);

        return schemaList;
    }

    @Override
    public List<TableInfo> listTables(Connection connection, String schema, String dataBaseInfoKey) throws SQLException {
        Objects.requireNonNull(connection, "数据库链接不允许为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(dataBaseInfoKey), "数据库连接信息缓存key不允许为空");

        ResultSet resultSet = null;
        List<TableInfo> tableInfos = null;

        try {
            final String catalog = connection.getCatalog();
            DatabaseMetaData metaData = connection.getMetaData();

            resultSet = metaData.getTables(catalog, null, null, new String[] {"TABLE"});
            tableInfos = new ArrayList<>();
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                TableInfo tableInfo = new TableInfo(tableName, dataBaseInfoKey);
                tableInfo.setComment(resultSet.getString("REMARKS"));

                // 格式化表名
                String tempTableName = StringTools.substringAfter(tableName, tableConfig.getPrefix());
                tableInfo.setName(StringTools.upper(tempTableName));

                // setKeys
                List<String> primaryKeys = getPrimaryKey(metaData, catalog, schema, tableName);
                tableInfo.setKeys(primaryKeys);

                // setColumns
                List<Column> columns = getColumns(metaData, schema, tableInfo);
                tableInfo.setColumns(columns);

                // setKeyTypes
                List<String> primaryKeyTypes = getPrimaryKeyTypes(primaryKeys, columns);
                tableInfo.setKeyTypes(primaryKeyTypes);

                tableInfos.add(tableInfo);
            }
        } finally {
            closeResource(connection, resultSet);
        }

        return tableInfos;
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
     * @param tableInfo
     * @return
     * @throws Exception
     */
    private List<Column> getColumns(DatabaseMetaData metaData, String schema, TableInfo tableInfo) throws SQLException {
        ResultSet resultSet = metaData.getColumns(schema, null, tableInfo.getDbName(), null);
        List<Column> columns = new ArrayList<>();
        while (resultSet.next()) {
            String columnName = resultSet.getString("COLUMN_NAME");
            String typeName = resultSet.getString("TYPE_NAME");
            String remarks = resultSet.getString("REMARKS");
            String defaultValue = resultSet.getString("COLUMN_DEF");
            int columnSize = resultSet.getInt("COLUMN_SIZE");
            boolean nullable = BooleanUtils.toBoolean(resultSet.getInt("NULLABLE"));
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
            column.setType(convertType(columnName, column.getDbType()));
            column.setName(StringTools.upperWithOutFisrtChar(columnName));
            column.setPrimaryKey(checkPrimaryKey(columnName, tableInfo.getKeys()));

            columns.add(column);
        }

        return columns;
    }

    private boolean checkPrimaryKey(String columnName, List<String> keys) {
        if(CollectionUtils.isEmpty(keys)) {
            return false;
        }
        return keys.contains(columnName);
    }

    private String convertType(String columnName,String dbType) {
        Map<String, String> typeMap = columnConfig.getType();
        String type = typeMap.get(dbType);

        if(StringUtils.isNotBlank(type)) {
            return type;
        }

        for(String key : typeMap.keySet()){
            if(StringUtils.containsIgnoreCase(dbType, key)) {
                type = typeMap.get(key);
            }
        }

        Preconditions.checkArgument(StringUtils.isNotBlank(type), "数据字段[%s]类型[%s]未匹配", columnName, dbType);
        return type;
    }

    private List<String> getPrimaryKey(DatabaseMetaData metaData, String catalog,
                                       String schema, String tableName) throws SQLException {
        ResultSet primaryKeys = metaData.getPrimaryKeys(catalog, schema, tableName);

        List<String> keys = new ArrayList<>();
        while (primaryKeys.next()) {
            String keyName = primaryKeys.getString("COLUMN_NAME");
            keys.add(keyName);
        }
        return keys;
    }

}
