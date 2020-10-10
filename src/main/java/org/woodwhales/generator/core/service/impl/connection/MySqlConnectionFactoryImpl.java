package org.woodwhales.generator.core.service.impl.connection;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.woodwhales.generator.core.entity.Column;
import org.woodwhales.generator.core.entity.TableInfo;
import org.woodwhales.generator.core.service.ConnectionFactory;
import org.woodwhales.generator.core.util.StringTools;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.woodwhales.generator.core.constant.DbTypeConstant.MYSQL_SERVICE_NAME;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.8.29 17:15
 * @description:
 */
@Slf4j
@Service(MYSQL_SERVICE_NAME)
public class MySqlConnectionFactoryImpl extends BaseConnectionFactory implements ConnectionFactory {

    @Override
    public List<String> listSchemas(Connection connection) throws SQLException {

        DatabaseMetaData metaData = connection.getMetaData();

        ResultSet resultSet = metaData.getCatalogs();

        List<String> schemaList = new ArrayList<>();
        while (resultSet.next()) {
            schemaList.add(resultSet.getString("TABLE_CAT"));
        }

        closeResource(connection, resultSet);

        return schemaList;
    }

    @Override
    public List<TableInfo> listTables(Connection connection, String schema, String dataBaseInfoKey) throws SQLException {

        checkArgument(connection, schema, dataBaseInfoKey);

        ResultSet resultSet = null;
        List<TableInfo> tableInfos;

        try {
            final String catalog = connection.getCatalog();
            DatabaseMetaData metaData = connection.getMetaData();

            resultSet = metaData.getTables(catalog, schema, null, new String[] {"TABLE"});
            tableInfos = new ArrayList<>();
            while (resultSet.next()) {
                String dbTableName = resultSet.getString("TABLE_NAME");

                TableInfo tableInfo = new TableInfo(dbTableName, dataBaseInfoKey);

                tableInfo.setComment(resultSet.getString("REMARKS"));

                fillNameAndPropertyName(tableInfo, dbTableName);

                // setKeys
                List<String> primaryKeys = getPrimaryKey(metaData, catalog, schema, dbTableName);
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
            column.setName(StringTools.upperWithOutFirstChar(columnName));
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

    private String convertType(String columnName, String dbType) {
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

}
