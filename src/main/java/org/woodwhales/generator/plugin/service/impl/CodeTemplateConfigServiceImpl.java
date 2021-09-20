package org.woodwhales.generator.plugin.service.impl;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.woodwhales.common.model.vo.PageRespVO;
import org.woodwhales.generator.plugin.controller.vo.CodeListPageConfigVO;
import org.woodwhales.generator.plugin.controller.vo.CodeNavigationConfigVO;
import org.woodwhales.generator.plugin.entity.CodeListPageConfig;
import org.woodwhales.generator.plugin.entity.CodeNavigationConfig;
import org.woodwhales.generator.plugin.model.CodeTemplateConfig;
import org.woodwhales.generator.plugin.model.CodeTemplateConfigDetail;
import org.woodwhales.generator.plugin.model.CodeTemplateConfigStatistics;
import org.woodwhales.generator.plugin.service.CodeListPageConfigService;
import org.woodwhales.generator.plugin.service.CodeNavigationConfigService;
import org.woodwhales.generator.plugin.service.CodeTemplateConfigService;

import java.util.List;
import java.util.Objects;
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
    public CodeTemplateConfig getCodeTemplateByCodeNavigationConfigId(Integer codeNavigationConfigId) {

        CodeNavigationConfigVO codeNavigationConfigVO = getCodeNavigationConfigVOByNavigationConfigById(codeNavigationConfigId);

        List<CodeListPageConfigVO> codeListPageConfigVOList = getCodeListPageConfigVOListByCodeNavigationConfigId(codeNavigationConfigId);

        return new CodeTemplateConfig(codeNavigationConfigVO, codeListPageConfigVOList);
    }

    @Override
    public CodeTemplateConfig getCodeTemplateByCodeListPageConfigId(Integer codeListPageConfigId) {
        CodeListPageConfig codeListPageConfig = getCodeListPageConfigByCodeListPageConfigId(codeListPageConfigId);
        Integer codeNavigationConfigId = codeListPageConfig.getCodeNavigationConfigId();
        return getCodeTemplateByCodeNavigationConfigId(codeNavigationConfigId);
    }

    @Override
    public CodeTemplateConfigStatistics statistics() {
        Integer codeListPageConfigTotal = codeListPageConfigService.statistics();
        Integer codeNavigationConfigTotal = codeNavigationConfigService.statistics();
        return new CodeTemplateConfigStatistics(codeNavigationConfigTotal, codeListPageConfigTotal);
    }

    @Override
    public List<CodeListPageConfigVO> getCodeListPageConfigVOListByCodeNavigationConfigId(Integer codeNavigationConfigId) {
        Objects.requireNonNull(codeNavigationConfigId, "菜单配置id不允许为空");
        List<CodeListPageConfig> codeListPageConfigList = codeListPageConfigService.getCodeListPageConfigListByCodeNavigationConfigId(codeNavigationConfigId);

        List<CodeListPageConfigVO> codeListPageConfigVOList = codeListPageConfigList.stream()
                .map(CodeListPageConfigVO::build)
                .collect(Collectors.toList());
        return codeListPageConfigVOList;
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
    public PageRespVO<CodeNavigationConfig> listCodeNavigationConfig() {
        List<CodeNavigationConfig> codeNavigationConfigList = codeNavigationConfigService.listCodeNavigationConfig();
        return PageRespVO.success(codeNavigationConfigList);
    }

    public PageRespVO<CodeListPageConfig> listCodeListPageConfig() {
        List<CodeListPageConfig> codeListPageConfigList = codeListPageConfigService.listCodeListPageConfig();
        return PageRespVO.success(codeListPageConfigList);
    }

    private CodeListPageConfig getCodeListPageConfigByCodeListPageConfigId(Integer codeListPageConfigId) {
        CodeListPageConfig codeListPageConfig = codeListPageConfigService.getCodeListPageConfigById(codeListPageConfigId);
        Preconditions.checkNotNull(codeListPageConfig, "codeNavigationConfig不允许为空");
        return codeListPageConfig;
    }

    @Override
    public CodeNavigationConfigVO getCodeNavigationConfigVOByCodeListPageConfigId(Integer codeListPageConfigId) {
        CodeListPageConfig codeListPageConfig = getCodeListPageConfigByCodeListPageConfigId(codeListPageConfigId);
        CodeNavigationConfigVO codeNavigationConfigVO = getCodeNavigationConfigVOByNavigationConfigById(codeListPageConfig.getCodeNavigationConfigId());
        return codeNavigationConfigVO;
    }

}
