package org.woodwhales.generator.core.service.impl.connection;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.woodwhales.generator.core.entity.TableInfo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.woodwhales.generator.core.constant.DbTypeConstant.ORACLE_SERVICE_NAME;

/**
 * @author woodwhales
 * @create 2020-10-09 22:24
 */
@Slf4j
@Service(ORACLE_SERVICE_NAME)
public class OracleConnectionFactoryImpl extends BaseConnectionFactory {

    @Override
    public List<String> listSchemas(Connection connection) throws SQLException {

        DatabaseMetaData metaData = connection.getMetaData();

        ResultSet resultSet = metaData.getSchemas();

        List<String> schemaList = new ArrayList<>();
        while (resultSet.next()) {
            String schema = resultSet.getString("TABLE_SCHEM");
            schemaList.add(schema);
        }

        closeResource(connection, resultSet);

        return schemaList;
    }

    @Override
    public List<TableInfo> listTables(Connection connection, String schema, String dataBaseInfoKey) throws SQLException {
        Objects.requireNonNull(connection, "数据库链接不允许为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(dataBaseInfoKey), "数据库连接信息缓存key不允许为空");

        String catalog = connection.getCatalog();
        DatabaseMetaData metaData = connection.getMetaData();

        ResultSet resultSet = metaData.getTables(catalog, schema, null, new String[] {"TABLE"});
        List<TableInfo> tableInfoList = new ArrayList<>();
        while (resultSet.next()) {
            String dbTableName = resultSet.getString("TABLE_NAME");

            TableInfo tableInfo = new TableInfo(dbTableName, dataBaseInfoKey);

            tableInfo.setComment(resultSet.getString("REMARKS"));

            fillNameAndPropertyName(tableInfo, dbTableName);

            // setKeys
            List<String> primaryKeys = getPrimaryKey(metaData, catalog, schema, dbTableName);
            tableInfo.setKeys(primaryKeys);

            ResultSet rsMetaData = metaData.getColumns(catalog, schema, dbTableName, null);

            while (rsMetaData.next()) {
                String columnName = rsMetaData.getString("COLUMN_NAME");
                String typeName = rsMetaData.getString("TYPE_NAME");
                String remarks = rsMetaData.getString("REMARKS");
                String defaultValue = rsMetaData.getString("COLUMN_DEF");
                int columnSize = rsMetaData.getInt("COLUMN_SIZE");
                boolean nullable = BooleanUtils.toBoolean(rsMetaData.getInt("NULLABLE"));

            }

            tableInfoList.add(tableInfo);
        }

        return tableInfoList;
    }
}
