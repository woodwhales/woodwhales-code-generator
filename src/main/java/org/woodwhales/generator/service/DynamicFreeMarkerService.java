package org.woodwhales.generator.service;

import org.woodwhales.generator.entity.TableInfo;

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
     * @return
     * @throws Exception
     */
    String dynamicProcess(String freemarker, TableInfo tableInfo) throws Exception;

}
