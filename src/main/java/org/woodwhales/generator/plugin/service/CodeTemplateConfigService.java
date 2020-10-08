package org.woodwhales.generator.plugin.service;

import org.woodwhales.common.model.vo.PageRespVO;
import org.woodwhales.generator.plugin.controller.vo.CodeListPageConfigVO;
import org.woodwhales.generator.plugin.controller.vo.CodeNavigationConfigVO;
import org.woodwhales.generator.plugin.entity.CodeNavigationConfig;
import org.woodwhales.generator.plugin.model.CodeTemplateConfig;
import org.woodwhales.generator.plugin.model.CodeTemplateConfigDetail;

import java.util.List;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.9.6 22:40
 * @description:
 */
public interface CodeTemplateConfigService {

    CodeTemplateConfig getCodeTemplateByCodeNavigationConfigById(Integer codeNavigationConfigId);

    List<CodeListPageConfigVO> getCodeListPageConfigVOListByCodeNavigationConfigId(Integer codeNavigationConfigId);

    CodeTemplateConfigDetail getCodeTemplateConfigDetailByCodeListPageConfigId(Integer codeListPageConfigId);

    CodeNavigationConfigVO getCodeNavigationConfigVOByNavigationConfigById(Integer codeNavigationConfigId);

    CodeListPageConfigVO getCodeListPageConfigVOByCodeListPageConfigId(Integer codeListPageConfigId);

    PageRespVO<CodeNavigationConfig> pageCodeTemplate();

    CodeNavigationConfigVO getCodeNavigationConfigVOByCodeListPageConfigId(Integer codeListPageConfigId);
}
