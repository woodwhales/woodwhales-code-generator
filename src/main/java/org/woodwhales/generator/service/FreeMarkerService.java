package org.woodwhales.generator.service;

import org.woodwhales.generator.entity.DataBaseInfo;
import org.woodwhales.generator.entity.TableInfo;

import java.io.File;
import java.util.List;

/**
 * 模板接口
 * @author woodwhales
 */
public interface FreeMarkerService {

	/**
	 * 生成目标文件
	 * @param targetFile
	 * @param packageName
	 * @param dataBaseInfo
	 * @param tables
	 * @throws Exception
	 */
	void process(File targetFile, String packageName, DataBaseInfo dataBaseInfo, List<TableInfo> tables) throws Exception;
	
}
