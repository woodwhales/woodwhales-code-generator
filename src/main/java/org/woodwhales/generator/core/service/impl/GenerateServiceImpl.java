package org.woodwhales.generator.core.service.impl;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import cn.woodwhales.common.model.result.OpResult;
import org.woodwhales.generator.core.controller.request.GenerateTemplateRequestBody;
import org.woodwhales.generator.core.controller.vo.DataBaseSimpleInfoVO;
import org.woodwhales.generator.core.entity.Column;
import org.woodwhales.generator.core.entity.DataBaseInfo;
import org.woodwhales.generator.core.entity.TableInfo;
import org.woodwhales.generator.core.enums.DbTypeEnum;
import org.woodwhales.generator.core.service.ConnectionFactory;
import org.woodwhales.generator.core.service.DataBaseInfoCache;
import org.woodwhales.generator.core.service.GenerateService;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author woodwhales
 */
@Slf4j
@Service
public class GenerateServiceImpl implements GenerateService {

	@Autowired
	private DataBaseInfoCache dataBaseInfoCache;

	@Autowired
	private ApplicationContext applicationContext;

	private ConnectionFactory getConnectionFactory(final String dbType) {
		Map<String, ConnectionFactory> connectionFactoryMap = applicationContext.getBeansOfType(ConnectionFactory.class);
		DbTypeEnum dbTypeEnum = DbTypeEnum.ofDbType(dbType);
		String serviceName = dbTypeEnum.getDbConnectionFactoryServiceName();
		ConnectionFactory connectionFactory = connectionFactoryMap.get(serviceName);
		return connectionFactory;
	}

	@Override
	public DataBaseSimpleInfoVO getDataBaseVersion(DataBaseInfo dataBaseInfo) throws Exception {
		String dbType = dataBaseInfo.getDbType();
		ConnectionFactory connectionFactory = getConnectionFactory(dbType);

		// 获取数据库链接
		Connection connection = getConnection(dataBaseInfo).getData();

		// 数据库链接成功之后清除缓存
		dataBaseInfoCache.clearCache(dataBaseInfo.getDataBaseInfoKey());

		// 查询所有数据库表名
		return connectionFactory.getDataBaseVersion(connection);
	}

	@Override
	public OpResult<Connection> getConnection(DataBaseInfo dataBaseInfo) throws Exception {
		String dbType = dataBaseInfo.getDbType();
		ConnectionFactory connectionFactory = getConnectionFactory(dbType);

		// 获取数据库链接
		try {
			Connection connection = connectionFactory.buildConnection(dataBaseInfo);
			return OpResult.success(connection);
		} catch (Exception e) {
			log.error("获取数据库链接失败", e);
			throw e;
		}
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

		List<TableInfo> cacheTableInfoList = dataBaseInfoCache.getTableInfoList(dataBaseInfoKey);

		// 生成代码
		if(isProcess) {
			final List<String> dbNameList = dataBaseInfo.getDbTableConfig().getDbNameList();
			final Boolean selectAll = dataBaseInfo.getDbTableConfig().getSelectAll();
			// 不是全选，dbNameList不允许为空
			if(!selectAll) {
				Preconditions.checkArgument(CollectionUtils.isNotEmpty(dbNameList), "未选择要生成的数据库表");
			}

			if (Objects.nonNull(cacheTableInfoList)) {
				if(selectAll) {
					return cacheTableInfoList;
				} else {
					return getTableInfoListByDbNameList(cacheTableInfoList, dbNameList);
				}
			} else {
				List<TableInfo> tableInfos = getTableInfoList(dataBaseInfo);
				dataBaseInfoCache.cacheTableInfoList(dataBaseInfoKey, tableInfos);
				return getTableInfoListByDbNameList(tableInfos, dbNameList);
			}

		} else {
			if (Objects.nonNull(cacheTableInfoList)) {
				return cacheTableInfoList;
			} else {
				List<TableInfo> tableInfos = getTableInfoList(dataBaseInfo);
				dataBaseInfoCache.cacheTableInfoList(dataBaseInfoKey, tableInfos);
				return tableInfos;
			}
		}
	}

	private List<TableInfo> getTableInfoList(DataBaseInfo dataBaseInfo) throws Exception {
		final String dbType = dataBaseInfo.getDbType();
		final String schema = dataBaseInfo.getSchema();
		final String dataBaseInfoKey = dataBaseInfo.getDataBaseInfoKey();

		ConnectionFactory connectionFactory = getConnectionFactory(dbType);
		Connection connection = connectionFactory.buildConnection(dataBaseInfo);

		List<TableInfo> tableInfos = connectionFactory.listTables(connection, schema, dataBaseInfoKey);
		return tableInfos;
	}

	@Override
	public TableInfo getTableInfo(String tableKey) {
		TableInfo tableInfoFromCache = dataBaseInfoCache.getTableInfo(tableKey);
		if(Objects.isNull(tableInfoFromCache)) {
			return null;
		}

		TableInfo tableInfo = new TableInfo(tableInfoFromCache.getDbName(), tableInfoFromCache.getTableKey());
		BeanUtils.copyProperties(tableInfoFromCache, tableInfo);
		return tableInfo;
	}

	@Override
	public TableInfo getTableInfo(GenerateTemplateRequestBody requestBody) {
		TableInfo tableInfo = getTableInfo(requestBody.getTableKey());
		if(Objects.isNull(tableInfo)) {
			return null;
		}

		Preconditions.checkArgument(CollectionUtils.isNotEmpty(requestBody.getColumnNameList()), "字段列表不允许为空");

		Map<String, Column> columnMap = tableInfo.getColumns()
												.stream()
												.collect(Collectors.toMap(Column::getDbName, Function.identity()));

		List<Column> columns = requestBody.getColumnNameList().stream().map(columnMap::get).collect(Collectors.toList());
		tableInfo.setColumns(columns);
		return tableInfo;
	}

	@Override
	public List<TableInfo> listTables(String encryptedDataBaseKey) {
		String dataBaseKey = dataBaseInfoCache.getDataBaseKey(encryptedDataBaseKey);
		return dataBaseInfoCache.getTableInfoList(dataBaseKey);
	}

	private List<TableInfo> getTableInfoListByDbNameList(List<TableInfo> tableInfos, List<String> dbNameList) {
		return tableInfos.stream()
				.filter(tableInfo -> dbNameList.contains(tableInfo.getDbName()))
				.collect(Collectors.toList());
	}

}
