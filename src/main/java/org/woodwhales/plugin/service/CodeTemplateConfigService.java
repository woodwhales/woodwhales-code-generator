package org.woodwhales.plugin.service;

import org.woodwhales.plugin.model.CodeTemplateConfig;

import java.util.List;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.9.6 22:40
 * @description:
 */
public interface CodeTemplateConfigService {
    CodeTemplateConfig getCodeTemplateConfig(Integer codeListConfigId);

    List<CodeTemplateConfig> listCodeTemplate();
}
