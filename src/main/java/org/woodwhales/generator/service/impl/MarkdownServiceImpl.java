package org.woodwhales.generator.service.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.woodwhales.generator.entity.DataBaseInfo;
import org.woodwhales.generator.entity.TableInfo;
import org.woodwhales.generator.service.FreeMarkerService;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.7.26 18:12
 * @description:
 */
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
    public void process(File targetFile, String packageName, DataBaseInfo dataBaseInfo, List<TableInfo> tables) throws Exception {

        Template template = configuration.getTemplate("markdown.ftl");

        String targetFilePath = targetFile.getAbsolutePath();

        String schema = dataBaseInfo.getSchema();
        String fileName = schema + "数据库表结构设计.md";
        boolean isCoverOldFile = true;
        FileWriter fw = new FileWriter(new File(targetFilePath + File.separator + fileName), !isCoverOldFile);

        HashMap<String, Object> dataModel = new HashMap<>();
        dataModel.put("tables", tables);
        dataModel.put("schema", dataBaseInfo.getSchema());
        template.process(dataModel, fw);

    }
}
