package org.woodwhales.generator.service;

import org.woodwhales.generator.model.GenerateInfo;

/**
 * 模板接口
 * @author woodwhales
 */
public interface FreeMarkerService {

	/**
	 * 生成目标文件
	 * @param generateInfo
	 * @throws Exception
	 */
	boolean process(GenerateInfo generateInfo) throws Exception;
	
}
