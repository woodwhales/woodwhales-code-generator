package org.woodwhales.generator.core.service;

import org.woodwhales.generator.core.entity.TableInfo;

import java.util.Map;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.8.30 10:34
 * @description:
 */
public interface DynamicFreeMarkerService {

    /**
     * 动态生成模板
     * @param freemarker
     * @param tableInfo
     * @param customKeyValueMap
     * @return
     * @throws Exception
     */
    String dynamicProcess(String freemarker, TableInfo tableInfo, Map<String, Object> customKeyValueMap) throws Exception;

}
