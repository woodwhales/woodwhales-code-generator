package org.woodwhales.generator.core.service;

import org.woodwhales.generator.core.entity.DataBaseInfo;
import org.woodwhales.generator.core.entity.TableInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.8.29 17:15
 * @description:
 */
public interface ConnectionFactory {

    /**
     * 创建数据库链接
     * @param dataBaseInfo 数据链接信息
     * @return
     * @throws Exception
     */
    Connection buildConnection(DataBaseInfo dataBaseInfo) throws Exception;

    /**
     * 关闭数据库链接资源
     * @param connection 数据库链接
     * @param resultSet 结果集
     * @throws SQLException
     */
    void closeResource(Connection connection, ResultSet resultSet) throws SQLException;

    void closeResource(ResultSet resultSet) throws SQLException;

    /**
     * 关闭数据库链接资源
     * @param connection 数据库链接
     * @throws SQLException
     */
    void closeResource(Connection connection) throws SQLException;

    /**
     * 查询所有数据库名
     * @param connection 数据库链接
     * @return
     * @throws SQLException
     */
    List<String> listSchemas(Connection connection) throws SQLException;

    /**
     * 获取所有数据库表信息
     * @param connection
     * @param schema
     * @param dataBaseInfoKey
     * @return
     * @throws SQLException
     */
    List<TableInfo> listTables(Connection connection, String schema, String dataBaseInfoKey) throws SQLException;
}
