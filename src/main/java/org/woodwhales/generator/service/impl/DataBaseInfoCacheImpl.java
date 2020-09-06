package org.woodwhales.generator.service.impl;

import com.google.common.base.Preconditions;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.woodwhales.generator.constant.MyConstant;
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

    private Map<String, TableInfo> tableInfoCache = new HashMap<>(32);

    private Map<String, String> dataBaseKeyCache = new HashMap<>(16);

    @Override
    public String getDataBaseKey(String encryptedDataBaseInfoKey) {
        return dataBaseKeyCache.get(encryptedDataBaseInfoKey);
    }

    @Override
    public String getEncryptedDataBaseKey(String dataBaseInfoKey) {
        Preconditions.checkArgument(StringUtils.isNotBlank(dataBaseInfoKey), "数据库连接key不允许为空");
        return DigestUtils.sha256Hex(dataBaseInfoKey + MyConstant.hexTxt);
    }

    @Override
    public void clearCache(String dataBaseInfoKey) {
        // 数据库连接成功之后，清空缓存
        Iterator<Map.Entry<String, List<TableInfo>>> tableInfosCacheIterator = tableInfosCache.entrySet().iterator();
        while (tableInfosCacheIterator.hasNext()) {
            Map.Entry<String, List<TableInfo>> entry = tableInfosCacheIterator.next();
            final String cacheKey = entry.getKey();
            if (startsWith(cacheKey, dataBaseInfoKey)) {

                // 清空table缓存
                List<TableInfo> tables = entry.getValue();
                tables.stream().forEach(table -> {
                    tableInfoCache.remove(table.getTableKey());
                });

                tableInfosCacheIterator.remove();
                // 删除数据库key映射缓存
                dataBaseKeyCache.remove(getEncryptedDataBaseKey(dataBaseInfoKey));
            }
        }
    }

    @Override
    public void cacheTableInfoList(final String dataBaseInfoKey, List<TableInfo> tableInfos) {
        Preconditions.checkArgument(StringUtils.isNotBlank(dataBaseInfoKey), "要缓存的key不允许为空");
        Objects.requireNonNull(tableInfos, "要缓存的数据库表信息不允许为空");
        tableInfosCache.put(dataBaseInfoKey, tableInfos);
        // 缓存数据库key映射
        dataBaseKeyCache.put(getEncryptedDataBaseKey(dataBaseInfoKey), dataBaseInfoKey);
        tableInfos.stream().forEach(tableInfo -> {
            tableInfoCache.put(tableInfo.getTableKey(), tableInfo);
        });
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

    @Override
    public TableInfo getTableInfo(String tableKey) {
        return tableInfoCache.get(tableKey);
    }
}
