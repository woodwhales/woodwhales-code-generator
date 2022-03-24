package org.woodwhales.generator.plugin.service.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.stereotype.Service;
import org.woodwhales.generator.core.service.impl.BaseFeeMarkerService;
import org.woodwhales.generator.plugin.model.CodeTemplateConfigDetail;
import org.woodwhales.generator.plugin.service.CodeTemplateFreeMarkerService;

import java.util.HashMap;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.9.20 16:02
 * @description:
 */
@Service("codeNavigationFreeMarkerService")
public class CodeNavigationFreeMarkerServiceImpl extends BaseFeeMarkerService implements CodeTemplateFreeMarkerService {

    @Override
    public boolean process(CodeTemplateConfigDetail codeTemplateConfigDetail) throws Exception {
        Configuration configuration = this.initConfiguration("/template/custom");
        Template template = configuration.getTemplate("code-navigation.ftl");

        // 要生成的目录
        String targetFilePath = "";

        // 是否覆盖原文件
        final Boolean isCoverOldFile = false;

        // 数据源
        HashMap<String, Object> dataModel = new HashMap<>(16);

        String fileName = "";

        return true;
    }
}
