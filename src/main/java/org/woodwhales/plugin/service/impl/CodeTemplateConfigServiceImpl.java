package org.woodwhales.plugin.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woodwhales.plugin.entity.CodeListPageConfig;
import org.woodwhales.plugin.entity.CodeNavigationConfig;
import org.woodwhales.plugin.model.CodeTemplateConfig;
import org.woodwhales.plugin.service.CodeListPageConfigService;
import org.woodwhales.plugin.service.CodeNavigationConfigService;
import org.woodwhales.plugin.service.CodeTemplateConfigService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
    public CodeTemplateConfig getCodeTemplateConfig(Integer codeListConfigId) {
        CodeListPageConfig codeListPageConfig = codeListPageConfigService.getCodeListPageConfigById(codeListConfigId);
        Integer codeNavigationConfigId = codeListPageConfig.getCodeNavigationConfigId();
        CodeNavigationConfig codeNavigationConfig = codeNavigationConfigService.getCodeNavigationConfigById(codeNavigationConfigId);
        return new CodeTemplateConfig(codeNavigationConfig, codeListPageConfig);
    }

    @Override
    public List<CodeTemplateConfig> listCodeTemplate() {

        List<CodeNavigationConfig> codeNavigationConfigList = codeNavigationConfigService.listCodeNavigationConfig();

        List<Integer> codeNavigationConfigIdList = codeNavigationConfigList.stream()
                                                                            .map(CodeNavigationConfig::getId)
                                                                            .collect(Collectors.toList());

        List<CodeListPageConfig> codeListPageConfigList = codeListPageConfigService.getCodeListPageConfigListByCodeNavigationConfigIdList(codeNavigationConfigIdList);
        if(CollectionUtils.isEmpty(codeListPageConfigList)) {
            return Collections.emptyList();
        }

        Map<Integer, CodeNavigationConfig> codeNavigationConfigMap = codeNavigationConfigList.stream()
                .collect(Collectors.toMap(CodeNavigationConfig::getId, Function.identity()));

        return codeListPageConfigList.stream()
                .map(codeListPageConfig -> this.buildCodeTemplateConfig(codeListPageConfig, codeNavigationConfigMap))
                .collect(Collectors.toList());

    }

    private CodeTemplateConfig buildCodeTemplateConfig(CodeListPageConfig codeListPageConfig, Map<Integer, CodeNavigationConfig> codeNavigationConfigMap) {
        CodeTemplateConfig codeTemplateConfig = new CodeTemplateConfig();
        codeTemplateConfig.setCodeListPageConfig(codeListPageConfig);
        codeTemplateConfig.setCodeNavigationConfig(codeNavigationConfigMap.get(codeListPageConfig.getCodeNavigationConfigId()));
        return codeTemplateConfig;
    }

}
