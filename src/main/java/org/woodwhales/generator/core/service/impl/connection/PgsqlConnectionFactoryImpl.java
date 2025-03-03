package org.woodwhales.generator.core.service.impl.connection;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.woodwhales.generator.core.controller.vo.DataBaseSimpleInfoVO;
import org.woodwhales.generator.core.entity.Column;
import org.woodwhales.generator.core.entity.TableInfo;
import org.woodwhales.generator.core.util.StringTools;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.woodwhales.generator.core.constant.DbTypeConstant.PGSQL_SERVICE_NAME;

/**
 * @author woodwhales on 2025-02-27 23:42
 */
@Slf4j
@Service(PGSQL_SERVICE_NAME)
public class PgsqlConnectionFactoryImpl extends BaseConnectionFactory {

    @Override
    public DataBaseSimpleInfoVO getDataBaseVersion(Connection connection) throws SQLException {

        DatabaseMetaData metaData = connection.getMetaData();

        String sql = "SELECT datname FROM pg_database WHERE datistemplate = false";
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        List<String> schemas = new ArrayList<>();
        while (resultSet.next()) {
            String schemaName = resultSet.getString("datname");
            schemas.add(schemaName);
        }
        closeResource(connection, resultSet);

        DataBaseSimpleInfoVO vo = new DataBaseSimpleInfoVO(this.getDbVersion(metaData), schemas);
        return vo;
    }

    @Override
    public List<TableInfo> listTables(Connection connection, String schema, String dataBaseInfoKey) throws SQLException {
        checkArgument(connection, schema, dataBaseInfoKey);

        List<TableInfo> tableInfoList = new ArrayList<>();

        StopWatch stopWatch = new StopWatch("获取数据库表信息对象");
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            final String catalog = connection.getCatalog();

            final Statement statement = connection.createStatement();
            tableInfoList = this.computeTableInfoList(statement, metaData, catalog, schema, dataBaseInfoKey);

            for (TableInfo tableInfo : tableInfoList) {
                String dbTableName = tableInfo.getDbName();

                log.info("dbTableName => {}", dbTableName);

                stopWatch.start("dbTableName => " + dbTableName);

                // computeCreateSql
                String createSql = this.computeCreateSql(statement, metaData, tableInfo);
                tableInfo.setCreateTableSql(createSql);

                // computeColumns
                List<Column> columns = this.computeColumns(statement, metaData, tableInfo);
                tableInfo.setColumns(columns);

                stopWatch.stop();
            }

        } finally {
            closeResource(connection);
            log.info(stopWatch.prettyPrint());
        }

        return tableInfoList;
    }

    private String computeCreateSql(Statement statement, DatabaseMetaData metaData, TableInfo tableInfo) throws SQLException {
        String dbTableName = tableInfo.getDbName();
        String schemaName = tableInfo.getSchemaName();
        String createSql = "WITH table_definition AS (\n" +
                "    SELECT \n" +
                "        'CREATE TABLE ' || n.nspname || '.' || c.relname || ' (' || E'\\n' ||\n" +
                "        string_agg(\n" +
                "            '    ' || a.attname || ' ' || \n" +
                "            REPLACE(pg_catalog.format_type(a.atttypid, a.atttypmod), 'character varying', 'varchar') ||\n" +
                "            CASE WHEN a.attnotnull THEN ' NOT NULL' ELSE '' END ||\n" +
                "            CASE WHEN a.atthasdef THEN ' DEFAULT ' || pg_get_expr(ad.adbin, ad.adrelid) ELSE '' END,\n" +
                "            ',' || E'\\n'\n" +
                "        ) || E'\\n' || ');' AS table_def\n" +
                "    FROM \n" +
                "        pg_attribute a\n" +
                "    JOIN pg_class c ON a.attrelid = c.oid\n" +
                "    JOIN pg_namespace n ON c.relnamespace = n.oid\n" +
                "    LEFT JOIN pg_attrdef ad ON a.attrelid = ad.adrelid AND a.attnum = ad.adnum\n" +
                "    WHERE \n" +
                "        c.relname = '"+dbTableName+"'\n" +
                "        AND n.nspname = '"+schemaName+"'\n" +
                "        AND a.attnum > 0\n" +
                "        AND NOT a.attisdropped\n" +
                "    GROUP BY n.nspname, c.relname\n" +
                "),\n" +
                "column_comments AS (\n" +
                "    SELECT \n" +
                "        'COMMENT ON COLUMN ' || n.nspname || '.' || c.relname || '.' || a.attname || ' IS ' || \n" +
                "        '''' || REPLACE(d.description, '''', '''''') || ''';' AS column_comment\n" +
                "    FROM \n" +
                "        pg_attribute a\n" +
                "    JOIN pg_class c ON a.attrelid = c.oid\n" +
                "    JOIN pg_namespace n ON c.relnamespace = n.oid\n" +
                "    LEFT JOIN pg_description d ON d.objoid = c.oid AND d.objsubid = a.attnum\n" +
                "    WHERE \n" +
                "        c.relname = '"+dbTableName+"'\n" +
                "        AND n.nspname = '"+schemaName+"'\n" +
                "        AND a.attnum > 0\n" +
                "        AND NOT a.attisdropped\n" +
                "        AND d.description IS NOT NULL\n" +
                "),\n" +
                "index_definitions AS (\n" +
                "    SELECT \n" +
                "        'CREATE INDEX ' || c2.relname || ' ON ' || n.nspname || '.' || c.relname || ' USING ' || \n" +
                "        CASE WHEN i.indisunique THEN 'UNIQUE ' ELSE '' END ||\n" +
                "        am.amname || ' (' || \n" +
                "        string_agg(a.attname, ', ') || ');' AS index_def\n" +
                "    FROM \n" +
                "        pg_index i\n" +
                "    JOIN pg_class c ON i.indrelid = c.oid\n" +
                "    JOIN pg_class c2 ON i.indexrelid = c2.oid\n" +
                "    JOIN pg_namespace n ON c.relnamespace = n.oid\n" +
                "    JOIN pg_am am ON c2.relam = am.oid\n" +
                "    JOIN pg_attribute a ON a.attrelid = c.oid AND a.attnum = ANY(i.indkey)\n" +
                "    WHERE \n" +
                "        c.relname = '"+dbTableName+"'\n" +
                "        AND n.nspname = '"+schemaName+"'\n" +
                "    GROUP BY n.nspname, c.relname, c2.relname, i.indisunique, am.amname\n" +
                "),\n" +
                "table_comment AS (\n" +
                "    SELECT \n" +
                "        'COMMENT ON TABLE ' || n.nspname || '.' || c.relname || ' IS ' || \n" +
                "        '''' || REPLACE(d.description, '''', '''''') || ''';' AS table_comment\n" +
                "    FROM \n" +
                "        pg_class c\n" +
                "    JOIN pg_namespace n ON c.relnamespace = n.oid\n" +
                "    LEFT JOIN pg_description d ON d.objoid = c.oid AND d.objsubid = 0\n" +
                "    WHERE \n" +
                "        c.relname = '"+dbTableName+"'\n" +
                "        AND n.nspname = '"+schemaName+"'\n" +
                "        AND d.description IS NOT NULL\n" +
                ")\n" +
                "SELECT \n" +
                "    (SELECT table_def FROM table_definition) || \n" +
                "    E'\\n' || \n" +
                "    COALESCE((SELECT string_agg(index_def, E'\\n') FROM index_definitions), '') || \n" +
                "    E'\\n' || \n" +
                "    COALESCE((SELECT string_agg(column_comment, E'\\n') FROM column_comments), '') || \n" +
                "    E'\\n' || \n" +
                "    COALESCE((SELECT table_comment FROM table_comment), '') \n" +
                "AS full_table_definition;";

        ResultSet createResultSet = statement.executeQuery(createSql);
        try {
            if (createResultSet.next()) {
                String createTableSql = createResultSet.getString("full_table_definition");
                return createTableSql;
            }

            return "";
        } finally {
            closeResource(createResultSet);
        }

    }

    private List<Column> computeColumns(Statement statement, DatabaseMetaData metaData, TableInfo tableInfo) throws SQLException {
        String dbTableName = tableInfo.getDbName();
        String queryColumnSql = "SELECT \n" +
                "    a.attname AS column_name, \n" +
                "    pg_catalog.format_type(a.atttypid, a.atttypmod) AS data_type, \n" +
                "    a.attnotnull AS not_null, \n" +
                "    (SELECT EXISTS (\n" +
                "        SELECT 1 \n" +
                "        FROM pg_index i \n" +
                "        JOIN pg_attribute ia ON i.indexrelid = ia.attrelid \n" +
                "        WHERE i.indrelid = a.attrelid \n" +
                "          AND ia.attnum = a.attnum \n" +
                "          AND i.indisprimary\n" +
                "    )) AS is_primary_key,  \n" +
                "    CASE \n" +
                "        WHEN a.attlen = -1 THEN (a.atttypmod - 4) \n" +
                "        ELSE a.attlen\n" +
                "    END AS column_length,\n" +
                "    COALESCE(pg_get_expr(ad.adbin, ad.adrelid), '') AS column_default,\n" +
                "    d.description AS column_comment\n" +
                "FROM \n" +
                "    pg_attribute a\n" +
                "JOIN \n" +
                "    pg_class c ON a.attrelid = c.oid\n" +
                "JOIN \n" +
                "    pg_namespace n ON c.relnamespace = n.oid\n" +
                "LEFT JOIN \n" +
                "    pg_attrdef ad ON a.attrelid = ad.adrelid AND a.attnum = ad.adnum\n" +
                "LEFT JOIN \n" +
                "    pg_description d ON d.objoid = c.oid AND d.objsubid = a.attnum\n" +
                "WHERE \n" +
                "    c.relname = '"+ dbTableName + "'\n" +
                "    AND n.nspname = '"+ tableInfo.getSchemaName() +"'\n" +
                "    AND a.attnum > 0\n" +
                "    AND NOT a.attisdropped\n" +
                "ORDER BY \n" +
                "    a.attnum;";
        ResultSet resultSet = null;
        List<Column> columns = new ArrayList<>();
        try {
            log.info("queryColumnSql={}", queryColumnSql);
            resultSet = statement.executeQuery(queryColumnSql);
            while (resultSet.next()) {
                String columnName = resultSet.getString("column_name");
                String typeName = StringUtils.replaceIgnoreCase(resultSet.getString("data_type"), "character varying", "varchar");
                boolean nullable = StringUtils.equalsAnyIgnoreCase(resultSet.getString("not_null"), "t") ? false : true;
                String nullableString = BooleanUtils.toString(nullable, "是", "否");
                boolean isPrimaryKey = resultSet.getBoolean("is_primary_key");
                int columnLength = resultSet.getInt("column_length");
                Integer columnSize = columnLength <= 0 ? null : columnLength;
                String defaultValue = resultSet.getString("column_default");
                String comment = resultSet.getString("column_comment");

                Column column = Column.builder()
                        .dbName(columnName)
                        .dbType(typeName)
                        .nullAble(nullable)
                        .nullableString(nullableString)
                        .columnSize(columnSize)
                        .defaultValue(defaultValue)
                        .comment(comment)
                        .primaryKey(isPrimaryKey)
                        .build();
                // 将数据库表的字段类型转成 java 变量类型
                column.setType(this.convertType(dbTableName, columnName, column.getDbType()));
                column.setName(StringTools.upperWithOutFirstChar(columnName));
                columns.add(column);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeResource(resultSet);
        }
        return columns;
    }

    @Override
    public List<TableInfo> computeTableInfoList(Statement statement, DatabaseMetaData metaData, String catalog, String schema, String dataBaseInfoKey) throws SQLException {
        List<TableInfo> tableInfoList = new ArrayList<>();
        String sql = "SELECT \n" +
                "    n.nspname AS schema_name, \n" +
                "    c.relname AS table_name, \n" +
                "    d.description AS table_comment\n" +
                "FROM \n" +
                "    pg_class c\n" +
                "JOIN \n" +
                "    pg_namespace n ON c.relnamespace = n.oid\n" +
                "LEFT JOIN \n" +
                "    pg_description d ON d.objoid = c.oid AND d.objsubid = 0\n" +
                "WHERE \n" +
                "    c.relkind = 'r'\n" +
                "    AND n.nspname NOT IN ('pg_catalog', 'information_schema');";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            String schemaName = resultSet.getString("schema_name");
            String dbTableName = resultSet.getString("table_name");
            String dbTableComment = resultSet.getString("table_comment");
            TableInfo tableInfo = new TableInfo(dbTableName, dataBaseInfoKey);
            log.debug("tableSchema={}, dbTableName={}, dbTableComment={}", dbTableName, dbTableName, dbTableComment);
            fillNameAndPropertyName(tableInfo, dbTableName);
            tableInfo.setComment(dbTableComment);
            tableInfo.setSchemaName(schemaName);
            tableInfoList.add(tableInfo);
        }
        return tableInfoList;
    }
}
