package org.woodwhales.generator.plugin.service;

import org.woodwhales.generator.plugin.controller.request.CodeListPageConfigCreateRequestBody;
import org.woodwhales.generator.plugin.entity.CodeListPageConfig;

import java.util.List;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.9.6 19:21
 * @description:
 */
public interface CodeListPageConfigService {

    /**
     * 创建 CodeListPageConfig
     * @param requestBody
     * @return
     */
    boolean create(CodeListPageConfigCreateRequestBody requestBody);

    /**
     * 根据主键查询配置信息
     * @param codeListPageConfigId
     * @return
     */
    CodeListPageConfig getCodeListPageConfigById(Integer codeListPageConfigId);

    /**
     * 根据 codeNavigationConfigId 查询 CodeListPageConfig 列表
     * @param codeNavigationConfigId
     * @return
     */
    List<CodeListPageConfig> getCodeListPageConfigListByCodeNavigationConfigId(Integer codeNavigationConfigId);

    /**
     * 查询列表配置
     * @return
     */
    List<CodeListPageConfig> listCodeListPageConfig();
}
