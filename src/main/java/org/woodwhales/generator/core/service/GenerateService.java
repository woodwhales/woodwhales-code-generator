package org.woodwhales.generator.core.service;

import cn.woodwhales.common.model.result.OpResult;
import org.woodwhales.generator.core.controller.request.GenerateTemplateRequestBody;
import org.woodwhales.generator.core.entity.DataBaseInfo;
import org.woodwhales.generator.core.entity.TableInfo;

import java.sql.Connection;
import java.util.List;

/**
 * 生成数据库信息接口
 * @author woodwhales
 */
public interface GenerateService {

	/**
	 * 根据 dataBaseInfo 生成 schema 列表
	 * @param dataBaseInfo
	 * @return
	 * @throws Exception
	 */
	List<String> listSchema(DataBaseInfo dataBaseInfo) throws Exception;

	/**
	 * 获取数据库链接
	 * @param dataBaseInfo
	 * @return
	 * @throws Exception
	 */
	OpResult<Connection> getConnection(DataBaseInfo dataBaseInfo) throws Exception;

	/**
	 * 根据 dataBaseInfo 生成数据库表信息
	 * @param dataBaseInfo
	 * @param isProcess 是否生成
	 * @return
	 * @throws Exception
	 */
	List<TableInfo> listTables(DataBaseInfo dataBaseInfo, boolean isProcess) throws Exception;

	/**
	 * 根据tableKey获取TableInfo
	 * @param tableKey
	 * @return
	 */
	TableInfo getTableInfo(String tableKey);


	/**
	 * 根据模板生成文件
	 * @param requestBody
	 * @return
	 */
	TableInfo getTableInfo(GenerateTemplateRequestBody requestBody);

	/**
	 * 根据加密的数据库key查询数据库表信息
	 * @param encryptedDataBaseKey
	 * @return
	 */
	List<TableInfo> listTables(String encryptedDataBaseKey);
}
