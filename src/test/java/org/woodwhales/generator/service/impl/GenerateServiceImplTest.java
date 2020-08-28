package org.woodwhales.generator.service.impl;

import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author woodwhales on 2020-08-28
 * @description
 */
class GenerateServiceImplTest {

    @Test
    public void testOracle() throws ClassNotFoundException {
        Class.forName("oracle.jdbc.OracleDriver");
        String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
        Properties props = new Properties();
        props.put("user", "root");
        props.put("password", "root1234");

        try {
            DriverManager.getConnection(url, props);
        } catch (SQLException exception) {
            exception.printStackTrace();
            // 1017
            System.out.println(exception.getErrorCode());
        }
    }

    @Test
    public void testMysql() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://127.0.0.1:3306?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai&useInformationSchema=true";
        Properties props = new Properties();
        props.put("user", "root");
        props.put("password", "root1234");

        try {
            DriverManager.getConnection(url, props);
        } catch (SQLException exception) {
            exception.printStackTrace();
            // 28000
            System.out.println(exception.getSQLState());
        }
    }

}