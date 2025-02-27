package org.woodwhales.generator.core.service.impl.connection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.woodwhales.generator.core.controller.vo.DataBaseSimpleInfoVO;
import org.woodwhales.generator.core.entity.TableInfo;

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
            tableInfoList = getTableInfoList(statement, metaData, catalog, schema, dataBaseInfoKey);

            for (TableInfo tableInfo : tableInfoList) {
                String dbTableName = tableInfo.getDbName();

                log.info("dbTableName => {}", dbTableName);

                stopWatch.start("dbTableName => " + dbTableName);

                stopWatch.stop();
            }
        } finally {
            closeResource(connection);
            log.info(stopWatch.prettyPrint());
        }

        return tableInfoList;
    }

    @Override
    public List<TableInfo> getTableInfoList(Statement statement, DatabaseMetaData metaData, String catalog, String schema, String dataBaseInfoKey) throws SQLException {

        List<TableInfo> tableInfoList = new ArrayList<>();

        String sql = "SELECT table_schema, table_name FROM information_schema.tables  WHERE table_type = 'BASE TABLE' AND table_schema NOT IN ('pg_catalog', 'information_schema')";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            String tableSchema = resultSet.getString("table_schema");
            String dbTableName = resultSet.getString("table_name");
            TableInfo tableInfo = new TableInfo(dbTableName, dataBaseInfoKey);
            fillNameAndPropertyName(tableInfo, dbTableName);
            tableInfoList.add(tableInfo);
        }

        return tableInfoList;
    }
}
