package org.woodwhales.generator.service.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.woodwhales.generator.entity.DataBaseInfo;
import org.woodwhales.generator.model.GenerateTableInfos;
import org.woodwhales.generator.service.FreeMarkerService;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    private Configuration configuration;

    @Autowired
    ResourceLoader resourceLoader;

    @PostConstruct
    public void init() {
        configuration = getConfiguration(resourceLoader);
    }

    @Override
    protected String filePath() {
        return "classpath:template/markdown";
    }

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

        try(FileWriter fw = new FileWriter(new File(targetFilePath + File.separator + fileName), !isCoverOldFile)) {
            template.process(dataModel, fw);
        } catch (IOException | TemplateException e) {
            log.error("生成文件异常，cause = {}", e.getCause().getMessage());
            return false;
        }
        return true;
    }
}
