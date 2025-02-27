package org.woodwhales.generator.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.woodwhales.generator.core.constant.DbTypeConstant;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author woodwhales
 * @create 2020-10-09 22:40
 */
@Getter
@AllArgsConstructor
public enum DbTypeEnum {

    /**
     * mysql 类型
     */
    MYSQL("MYSQL", DbTypeConstant.MYSQL_SERVICE_NAME),

    /**
     * oracle 类型
     */
    ORACLE("ORACLE", DbTypeConstant.ORACLE_SERVICE_NAME),

    /**
     * PGSQL 类型
     */
    PGSQL("PGSQL", DbTypeConstant.PGSQL_SERVICE_NAME),
    ;

    /**
     * 数据库类型
     */
    private final String dbType;

    /**
     * 数据库连接对象 bean name
     */
    public final String dbConnectionFactoryServiceName;

    /**
     * dbType 为 key 的 DbTypeEnum map 集合
     */
    private static Map<String, DbTypeEnum> dbTypeEnumMap = Stream.of(DbTypeEnum.values())
            .collect(Collectors.toMap(DbTypeEnum::getDbType, Function.identity()));

    /**
     * 通过 dbType 获取 DbTypeEnum 对象
     * @param dbType
     * @return
     */
    public static DbTypeEnum ofDbType(String dbType) {
        return dbTypeEnumMap.get(dbType);
    }

    public static String getServiceName(String dbType) {
        return dbTypeEnumMap.get(dbType).getDbConnectionFactoryServiceName();
    }

}
