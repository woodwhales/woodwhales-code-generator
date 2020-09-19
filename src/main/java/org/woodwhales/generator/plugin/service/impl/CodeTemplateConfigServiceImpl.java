package org.woodwhales.generator.plugin.service.impl;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woodwhales.common.model.vo.PageRespVO;
import org.woodwhales.generator.plugin.controller.vo.CodeListPageConfigVO;
import org.woodwhales.generator.plugin.controller.vo.CodeNavigationConfigVO;
import org.woodwhales.generator.plugin.entity.CodeListPageConfig;
import org.woodwhales.generator.plugin.entity.CodeNavigationConfig;
import org.woodwhales.generator.plugin.model.CodeTemplateConfig;
import org.woodwhales.generator.plugin.model.CodeTemplateConfigDetail;
import org.woodwhales.generator.plugin.service.CodeListPageConfigService;
import org.woodwhales.generator.plugin.service.CodeNavigationConfigService;
import org.woodwhales.generator.plugin.service.CodeTemplateConfigService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.9.6 22:41
 * @description:
 */
@Service
public class CodeTemplateConfigServiceImpl implements CodeTemplateConfigService {

    @Autowired
    private CodeListPageConfigService codeListPageConfigService;

    @Autowired
    private CodeNavigationConfigService codeNavigationConfigService;

    @Override
    public CodeTemplateConfig getCodeTemplateByCodeNavigationConfigById(Integer codeNavigationConfigId) {

        CodeNavigationConfigVO codeNavigationConfigVO = getCodeNavigationConfigVOByNavigationConfigById(codeNavigationConfigId);

        List<CodeListPageConfig> codeListPageConfigList = codeListPageConfigService.getCodeListPageConfigListByCodeNavigationConfigId(codeNavigationConfigId);

        List<CodeListPageConfigVO> codeListPageConfigVOList = codeListPageConfigList.stream()
                                                            .map(CodeListPageConfigVO::build)
                                                            .collect(Collectors.toList());

        return new CodeTemplateConfig(codeNavigationConfigVO, codeListPageConfigVOList);
    }

    @Override
    public CodeTemplateConfigDetail getCodeTemplateConfigDetailByCodeListPageConfigId(Integer codeListPageConfigId) {
        CodeListPageConfig codeListPageConfig = getCodeListPageConfigByCodeListPageConfigId(codeListPageConfigId);
        Integer codeNavigationConfigId = codeListPageConfig.getCodeNavigationConfigId();
        CodeNavigationConfigVO codeNavigationConfigVO = getCodeNavigationConfigVOByNavigationConfigById(codeNavigationConfigId);
        CodeListPageConfigVO codeListPageConfigVO = CodeListPageConfigVO.build(codeListPageConfig);
        return new CodeTemplateConfigDetail(codeNavigationConfigVO, codeListPageConfigVO);
    }

    @Override
    public CodeNavigationConfigVO getCodeNavigationConfigVOByNavigationConfigById(Integer codeNavigationConfigId) {
        CodeNavigationConfig codeNavigationConfig = getCodeNavigationConfigByCodeNavigationConfigId(codeNavigationConfigId);
        CodeNavigationConfigVO codeNavigationConfigVO = CodeNavigationConfigVO.newInstance(codeNavigationConfig);
        return codeNavigationConfigVO;
    }

    private CodeNavigationConfig getCodeNavigationConfigByCodeNavigationConfigId(Integer codeNavigationConfigId) {
        CodeNavigationConfig codeNavigationConfig = codeNavigationConfigService.getCodeNavigationConfigById(codeNavigationConfigId);
        Preconditions.checkNotNull(codeNavigationConfig, "codeNavigationConfig不允许为空");
        return  codeNavigationConfig;
    }

    @Override
    public CodeListPageConfigVO getCodeListPageConfigVOByCodeListPageConfigId(Integer codeListPageConfigId) {
        CodeListPageConfig codeListPageConfig = getCodeListPageConfigByCodeListPageConfigId(codeListPageConfigId);
        CodeListPageConfigVO codeListPageConfigVO = CodeListPageConfigVO.build(codeListPageConfig);
        return codeListPageConfigVO;
    }

    @Override
    public PageRespVO<CodeNavigationConfig> pageCodeTemplate() {
        List<CodeNavigationConfig> codeNavigationConfigList = codeNavigationConfigService.listCodeNavigationConfig();
        return PageRespVO.success(codeNavigationConfigList);
    }

    private CodeListPageConfig getCodeListPageConfigByCodeListPageConfigId(Integer codeListPageConfigId) {
        CodeListPageConfig codeListPageConfig = codeListPageConfigService.getCodeListPageConfigById(codeListPageConfigId);
        Preconditions.checkNotNull(codeListPageConfig, "codeNavigationConfig不允许为空");
        return codeListPageConfig;
    }

    @Override
    public CodeNavigationConfigVO getCodeNavigationConfigVOByCodeListPageConfigId(Integer codeListPageConfigId) {
        CodeListPageConfig codeListPageConfig = getCodeListPageConfigByCodeListPageConfigId(codeListPageConfigId);
        return getCodeNavigationConfigVOByNavigationConfigById(codeListPageConfig.getCodeNavigationConfigId());
    }

}
