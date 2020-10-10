package org.woodwhales.generator.core.service.impl.connection;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.woodwhales.generator.core.config.ColumnConfig;
import org.woodwhales.generator.core.config.TableConfig;
import org.woodwhales.generator.core.entity.DataBaseInfo;
import org.woodwhales.generator.core.entity.TableInfo;
import org.woodwhales.generator.core.exception.GenerateException;
import org.woodwhales.generator.core.service.ConnectionFactory;
import org.woodwhales.generator.core.util.StringTools;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author woodwhales
 * @create 2020-10-09 22:26
 */
@Slf4j
public abstract class BaseConnectionFactory implements ConnectionFactory {

    @Autowired
    protected ColumnConfig columnConfig;

    @Autowired
    protected TableConfig tableConfig;

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

    /**
     * 格式化数据库表名称
     * @param dbTableName
     * @return
     */
    protected void fillNameAndPropertyName(TableInfo tableInfo, String dbTableName) {
        String tempTableName = StringTools.substringAfter(dbTableName, tableConfig.getPrefix());

        // 格式化表名
        tableInfo.setName(StringTools.upper(tempTableName));

        // 格式化表名首字母小写
        tableInfo.setPropertyName(StringTools.upperWithOutFirstChar(tempTableName));
    }

    /**
     * 获取主键名称集合
     * @param metaData
     * @param catalog
     * @param schema
     * @param dbTableName
     * @return
     * @throws SQLException
     */
    protected List<String> getPrimaryKey(DatabaseMetaData metaData, String catalog,
                                       String schema, String dbTableName) throws SQLException {
        ResultSet primaryKeys = metaData.getPrimaryKeys(catalog, schema, dbTableName);

        List<String> keys = new ArrayList<>();
        while (primaryKeys.next()) {
            String keyName = primaryKeys.getString("COLUMN_NAME");
            keys.add(keyName);
        }
        return keys;
    }


    protected void checkArgument(Connection connection, String schema, String dataBaseInfoKey) {
        Objects.requireNonNull(connection, "数据库链接不允许为空");
        Preconditions.checkArgument(isNotBlank(dataBaseInfoKey), "数据库连接信息缓存key不允许为空");
        Preconditions.checkArgument(isNotBlank(schema), "schema不允许为空");
    }

}
