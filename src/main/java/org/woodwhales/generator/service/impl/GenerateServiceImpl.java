package org.woodwhales.generator.service.impl;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woodwhales.generator.entity.DataBaseInfo;
import org.woodwhales.generator.entity.TableInfo;
import org.woodwhales.generator.service.ConnectionFactory;
import org.woodwhales.generator.service.DataBaseInfoCache;
import org.woodwhales.generator.service.GenerateService;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GenerateServiceImpl implements GenerateService {

	@Autowired
	private DataBaseInfoCache dataBaseInfoCache;

	@Autowired
	private ConnectionFactory connectionFactory;

	@Override
	public List<String> listSchema(DataBaseInfo dataBaseInfo) throws Exception {
		// 获取数据库链接
		Connection connection = connectionFactory.buildConnection(dataBaseInfo);

		// 数据库链接成功之后清除缓存
		dataBaseInfoCache.clearCache(dataBaseInfo.getDataBaseInfoKey());

		// 查询所有数据库表名
		return connectionFactory.listSchemas(connection);
	}

	/**
	 *
	 * @param dataBaseInfo
	 * @param isProcess 是否要生成数据库表
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<TableInfo> listTables(DataBaseInfo dataBaseInfo, boolean isProcess) throws Exception {

		// 先从缓存中获取
		final String dataBaseInfoKey = dataBaseInfo.getDataBaseInfoKey();
		final String schema = dataBaseInfo.getSchema();

		List<TableInfo> cacheTableInfoList = dataBaseInfoCache.getTableInfoList(dataBaseInfoKey);

		// 生成代码
		if(isProcess) {
			final List<String> dbNameList = dataBaseInfo.getDbNameList();
			final Boolean selectAll = dataBaseInfo.getSelectAll();
			// 不是全选，dbNameList不允许为空
			Preconditions.checkArgument(!selectAll && CollectionUtils.isNotEmpty(dbNameList), "未选择要生成的数据库表");
			if (Objects.nonNull(cacheTableInfoList)) {
				if(selectAll) {
					return cacheTableInfoList;
				} else {
					return getTableInfoListByDbNameList(cacheTableInfoList, dbNameList);
				}
			} else {
				Connection connection = connectionFactory.buildConnection(dataBaseInfo);
				List<TableInfo> tableInfos = connectionFactory.listTables(connection, schema);
				dataBaseInfoCache.cacheTableInfoList(dataBaseInfoKey, tableInfos);
				return getTableInfoListByDbNameList(tableInfos, dbNameList);
			}

		} else {
			if (Objects.nonNull(cacheTableInfoList)) {
				return cacheTableInfoList;
			} else {
				Connection connection = connectionFactory.buildConnection(dataBaseInfo);
				List<TableInfo> tableInfos = connectionFactory.listTables(connection, schema);
				dataBaseInfoCache.cacheTableInfoList(dataBaseInfoKey, tableInfos);
				return tableInfos;
			}
		}
	}

	private List<TableInfo> getTableInfoListByDbNameList(List<TableInfo> tableInfos, List<String> dbNameList) {
		return tableInfos.stream()
						.filter(tableInfo -> dbNameList.contains(tableInfo.getDbName()))
						.collect(Collectors.toList());
	}

}
