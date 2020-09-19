package org.woodwhales.generator.core.service.impl;

import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.woodwhales.generator.core.entity.DataBaseInfo;
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

        DataBaseInfo dataBaseInfo = generateTableInfos.getDataBaseInfo();
        if(!dataBaseInfo.getGenerateMarkdown()) {
            return true;
        }

        Template template = configuration.getTemplate("markdown.ftl");

        String targetFilePath = generateTableInfos.getMarkdownFile().getAbsolutePath();

        String schema = dataBaseInfo.getSchema();
        String fileName = schema + "数据库表结构设计.md";

        final Boolean isCoverOldFile = dataBaseInfo.getOverMarkdown();
        HashMap<String, Object> dataModel = new HashMap<>(16);
        dataModel.put("tables", generateTableInfos.getTables());
        dataModel.put("schema", dataBaseInfo.getSchema());

        return process(template, targetFilePath, fileName, dataModel, isCoverOldFile);
    }

    @Override
    protected String templateFilePath() {
        return "/template/markdown";
    }
}
