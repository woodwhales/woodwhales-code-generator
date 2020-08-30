package org.woodwhales.generator.service;

import org.woodwhales.generator.entity.TableInfo;

import java.util.List;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.8.29 17:10
 * @description:
 */
public interface DataBaseInfoCache {

    /**
     * 链接成功之后，需要清除缓存
     * @param dataBaseInfoKey
     */
    void clearCache(String dataBaseInfoKey);

    /**
     * 缓存 List<TableInfo>
     * @param dataBaseInfoKey
     * @param tableInfos
     */
    void cacheTableInfoList(String dataBaseInfoKey, List<TableInfo> tableInfos);

    /**
     * 通过 dataBaseInfoKey 获取 List<TableInfo>
     * 获取不到则返回 null
     *
     * @see TableInfo
     *
     * @param dataBaseInfoKey
     * @return
     */
    List<TableInfo> getTableInfoList(String dataBaseInfoKey);

    /**
     * 根据 tableKey 查询对应的 TableInfo
     * @param tableKey
     * @return
     */
    TableInfo getTableInfo(String tableKey);
}
