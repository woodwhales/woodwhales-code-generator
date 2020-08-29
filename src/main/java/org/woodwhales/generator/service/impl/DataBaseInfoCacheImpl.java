package org.woodwhales.generator.service.impl;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.woodwhales.generator.entity.TableInfo;
import org.woodwhales.generator.service.DataBaseInfoCache;

import java.util.*;

import static org.apache.commons.lang3.StringUtils.startsWith;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.8.29 17:11
 * @description:
 */
@Service
public class DataBaseInfoCacheImpl implements DataBaseInfoCache {

    private Map<String, List<TableInfo>> tableInfosCache = new HashMap<>(16);

    @Override
    public void clearCache(String dataBaseInfoKey) {
        // 数据库连接成功之后，清空缓存
        Iterator<Map.Entry<String, List<TableInfo>>> tableInfosCacheIterator = tableInfosCache.entrySet().iterator();
        while (tableInfosCacheIterator.hasNext()) {
            Map.Entry<String, List<TableInfo>> entry = tableInfosCacheIterator.next();
            if (startsWith(dataBaseInfoKey, entry.getKey())) {
                tableInfosCacheIterator.remove();
            }
        }
    }

    @Override
    public void cacheTableInfoList(String dataBaseInfoKey, List<TableInfo> tableInfos) {
        Preconditions.checkArgument(StringUtils.isNotBlank(dataBaseInfoKey), "要缓存的key不允许为空");
        Objects.requireNonNull(tableInfos, "要缓存的数据库表信息不允许为空");
        tableInfosCache.put(dataBaseInfoKey, tableInfos);
    }

    @Override
    public List<TableInfo> getTableInfoList(String dataBaseInfoKey) {
        Iterator<Map.Entry<String, List<TableInfo>>> tableInfosCacheIterator = tableInfosCache.entrySet().iterator();
        while (tableInfosCacheIterator.hasNext()) {
            Map.Entry<String, List<TableInfo>> entry = tableInfosCacheIterator.next();
            if (StringUtils.equals(dataBaseInfoKey, entry.getKey())) {
                return entry.getValue();
            }
        }

        return null;
    }
}
