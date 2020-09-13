package org.woodwhales.plugin.service;

import org.woodwhales.common.response.PageRespVO;
import org.woodwhales.plugin.controller.vo.CodeListPageConfigVO;
import org.woodwhales.plugin.controller.vo.CodeNavigationConfigVO;
import org.woodwhales.plugin.entity.CodeNavigationConfig;
import org.woodwhales.plugin.model.CodeTemplateConfig;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.9.6 22:40
 * @description:
 */
public interface CodeTemplateConfigService {

    CodeTemplateConfig getCodeTemplateByCodeNavigationConfigById(Integer codeNavigationConfigId);

    CodeNavigationConfigVO getCodeNavigationConfigVOByNavigationConfigById(Integer codeNavigationConfigId);

    CodeListPageConfigVO getCodeListPageConfigVOByCodeListPageConfigId(Integer codeListPageConfigId);

    PageRespVO<CodeNavigationConfig> pageCodeTemplate();
}
