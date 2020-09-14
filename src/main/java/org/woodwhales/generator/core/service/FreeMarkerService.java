package org.woodwhales.generator.core.service;

import org.woodwhales.generator.core.model.GenerateTableInfos;

/**
 * 模板接口
 * @author woodwhales
 */
public interface FreeMarkerService {

	/**
	 * 生成目标文件
	 * @param generateTableInfos
	 * @return
	 * @throws Exception
	 */
	boolean process(GenerateTableInfos generateTableInfos) throws Exception;
	
}
