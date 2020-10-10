package org.woodwhales.generator.core.service.impl.connection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.woodwhales.generator.core.service.ConnectionFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

}
