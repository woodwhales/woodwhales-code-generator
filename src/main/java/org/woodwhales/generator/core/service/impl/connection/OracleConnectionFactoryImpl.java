package org.woodwhales.generator.core.service.impl.connection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.woodwhales.generator.core.controller.vo.DataBaseSimpleInfoVO;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.woodwhales.generator.core.constant.DbTypeConstant.ORACLE_SERVICE_NAME;

/**
 * @author woodwhales
 * @create 2020-10-09 22:24
 */
@Slf4j
@Service(ORACLE_SERVICE_NAME)
public class OracleConnectionFactoryImpl extends BaseConnectionFactory {

    @Override
    public DataBaseSimpleInfoVO getDataBaseVersion(Connection connection) throws SQLException {

        DatabaseMetaData metaData = connection.getMetaData();

        ResultSet resultSet = metaData.getSchemas();

        List<String> schemaList = new ArrayList<>();
        while (resultSet.next()) {
            String schema = resultSet.getString("TABLE_SCHEM");
            schemaList.add(schema);
        }

        closeResource(connection, resultSet);

        return new DataBaseSimpleInfoVO(this.getDbVersion(metaData), schemaList);
    }
}
