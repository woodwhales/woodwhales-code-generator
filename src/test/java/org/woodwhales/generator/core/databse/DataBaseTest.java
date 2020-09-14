package org.woodwhales.generator.core.databse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.woodwhales.generator.core.entity.DataBaseInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.7.26 13:18
 * @description:
 */
public class DataBaseTest {

    private static Connection connection = null;
    private static DataBaseInfo dataBaseInfo = null;

    @Test
    public void test() throws SQLException {

        String catalog = connection.getCatalog();
        DatabaseMetaData metaData = connection.getMetaData();
        String schema = dataBaseInfo.getSchema();

        ResultSet schemas = metaData.getSchemas();
        while (schemas.next()) {
            String tableSchem = schemas.getString(0);
            System.out.println(tableSchem);
        }

        ResultSet resultSet = metaData.getTables(schema, null, null, new String[] {"TABLE"});

        System.out.println("tableName\t\tremarks\t\tkeys");
        while (resultSet.next()) {
            String tableName = resultSet.getString("TABLE_NAME");
            String remarks = resultSet.getString("REMARKS");
            ResultSet primaryKeys = metaData.getPrimaryKeys(catalog, schema, tableName);

            // 获取所有主键
            List<String> keys = new ArrayList<> ();
            while (primaryKeys.next()) {
                String keyName = primaryKeys.getString("COLUMN_NAME");
                keys.add(keyName);
            }
            System.out.printf("%s\t\t%s\t\t%s\n", tableName, remarks, Arrays.toString(keys.toArray()));
        }
    }

    @BeforeAll
    @Test
    public static void init() throws ClassNotFoundException, SQLException {
        dataBaseInfo = DataBaseInfo.builder()
                                            .ip("127.0.0.1")
                                            .port(3306)
                                            .username("root")
                                            .password("root")
                                            .schema("miaosha")
                                            .build();

        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(dataBaseInfo.getUrl(), dataBaseInfo.getProperties());
    }
}
