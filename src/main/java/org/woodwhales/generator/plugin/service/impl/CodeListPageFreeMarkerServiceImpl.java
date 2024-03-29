package org.woodwhales.generator.plugin.service.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.woodwhales.generator.core.service.impl.BaseFeeMarkerService;
import org.woodwhales.generator.plugin.model.CodeTemplateConfigDetail;
import org.woodwhales.generator.plugin.service.CodeTemplateFreeMarkerService;

import java.util.HashMap;

import static org.apache.commons.lang3.StringUtils.lowerCase;

/**
 * @author woodwhales on 2020-09-14
 * @description
 */
@Slf4j
@Service("codeListPageFreeMarkerService")
public class CodeListPageFreeMarkerServiceImpl extends BaseFeeMarkerService implements CodeTemplateFreeMarkerService {

    @Override
    public boolean process(CodeTemplateConfigDetail codeTemplateConfigDetail) throws Exception {
        Configuration configuration = this.initConfiguration("/template/custom");
        Template template = configuration.getTemplate("code-list-page.ftl");

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
