package org.woodwhales.generator.service;

import org.woodwhales.generator.entity.DataBaseInfo;
import org.woodwhales.generator.entity.TableInfo;

import java.sql.Connection;
import java.util.List;

/**
 * 生成数据库信息接口
 * @author woodwhales
 */
public interface GenerateService {

	/**
	 * 根据 dataBaseInfo 生成 Connection
	 * @param dataBaseInfo
	 * @return
	 * @throws Exception
	 */
	Connection getConnection(DataBaseInfo dataBaseInfo) throws Exception;

	/**
	 * 根据 dataBaseInfo 生成 schema 列表
	 * @param dataBaseInfo
	 * @return
	 * @throws Exception
	 */
	List<String> listSchema(DataBaseInfo dataBaseInfo) throws Exception;

	/**
	 * 根据 dataBaseInfo 生成数据库表信息
	 * @param dataBaseInfo
	 * @param isProcess 是否生成
	 * @return
	 * @throws Exception
	 */
	List<TableInfo> listTables(DataBaseInfo dataBaseInfo, boolean isProcess) throws Exception;
	
}
