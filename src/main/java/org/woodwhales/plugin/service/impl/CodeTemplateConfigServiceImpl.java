package org.woodwhales.plugin.service.impl;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woodwhales.common.response.PageRespVO;
import org.woodwhales.plugin.controller.vo.CodeListPageConfigVO;
import org.woodwhales.plugin.controller.vo.CodeNavigationConfigVO;
import org.woodwhales.plugin.entity.CodeListPageConfig;
import org.woodwhales.plugin.entity.CodeNavigationConfig;
import org.woodwhales.plugin.model.CodeTemplateConfig;
import org.woodwhales.plugin.service.CodeListPageConfigService;
import org.woodwhales.plugin.service.CodeNavigationConfigService;
import org.woodwhales.plugin.service.CodeTemplateConfigService;

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
    public CodeTemplateConfig getCodeTemplateByCodeNavigationConfigById(Integer codeNavigationConfigById) {
        CodeNavigationConfig codeNavigationConfig = codeNavigationConfigService.getCodeNavigationConfigById(codeNavigationConfigById);

        CodeNavigationConfigVO codeNavigationConfigVO = CodeNavigationConfigVO.newInstance(codeNavigationConfig);

        Preconditions.checkNotNull(codeNavigationConfig, "codeNavigationConfig不允许为空");
        List<CodeListPageConfig> codeListPageConfigList = codeListPageConfigService.getCodeListPageConfigListByCodeNavigationConfigId(codeNavigationConfigById);

        List<CodeListPageConfigVO> codeListPageConfigVOList = codeListPageConfigList.stream()
                                                            .map(CodeListPageConfigVO::build)
                                                            .collect(Collectors.toList());

        return new CodeTemplateConfig(codeNavigationConfigVO, codeListPageConfigVOList);
    }

    @Override
    public PageRespVO<CodeNavigationConfig> pageCodeTemplate() {
        List<CodeNavigationConfig> codeNavigationConfigList = codeNavigationConfigService.listCodeNavigationConfig();
        return PageRespVO.success(codeNavigationConfigList);
    }

}
