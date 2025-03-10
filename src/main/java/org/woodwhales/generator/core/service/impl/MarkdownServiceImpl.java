package org.woodwhales.generator.core.service.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.woodwhales.generator.core.controller.request.MarkdownConfig;
import org.woodwhales.generator.core.model.GenerateTableInfos;
import org.woodwhales.generator.core.service.FreeMarkerService;

import java.util.HashMap;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.7.26 18:12
 * @description:
 */
@Slf4j
@Service("markdownService")
public class MarkdownServiceImpl extends BaseFeeMarkerService implements FreeMarkerService {

    @Override
    public boolean process(GenerateTableInfos generateTableInfos) throws Exception {

        MarkdownConfig markdownConfig = generateTableInfos.getDataBaseInfo().getMarkdownConfig();
        if(!markdownConfig.getGenerateMarkdown()) {
            return true;
        }

        Configuration configuration = this.initConfiguration("/template/markdown");
        Template template = configuration.getTemplate("markdown.ftl", "UTF-8");

        String targetFilePath = generateTableInfos.getMarkdownFile().getAbsolutePath();

        String schema = generateTableInfos.getDataBaseInfo().getSchema();
        String fileName = schema + "数据库表结构设计.md";

        final Boolean isCoverOldFile = markdownConfig.getOverMarkdown();
        HashMap<String, Object> dataModel = new HashMap<>(16);
        dataModel.put("tables", generateTableInfos.getTables());
        dataModel.put("schema", generateTableInfos.getDataBaseInfo().getSchema());

        return process(template, targetFilePath, fileName, dataModel, isCoverOldFile);
    }

}
