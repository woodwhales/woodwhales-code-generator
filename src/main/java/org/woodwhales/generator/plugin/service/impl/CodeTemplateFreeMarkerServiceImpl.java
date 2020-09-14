package org.woodwhales.generator.plugin.service.impl;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.woodwhales.generator.core.service.impl.BaseFeeMarkerService;
import org.woodwhales.generator.plugin.service.CodeTemplateFreeMarkerService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author woodwhales on 2020-09-14
 * @description
 */
@Slf4j
@Service("codeTemplateFreeMarkerService")
public class CodeTemplateFreeMarkerServiceImpl extends BaseFeeMarkerService implements CodeTemplateFreeMarkerService {

    @Override
    public boolean process() throws Exception {

        Template template = configuration.getTemplate("code-list-page.ftl");

        // 要生成的目录
        String targetFilePath = "";

        // 是否覆盖原文件
        final Boolean isCoverOldFile = false;

        // 数据源
        HashMap<String, Object> dataModel = new HashMap<>(16);

        String fileName = "";

        try(FileWriter fw = new FileWriter(new File(targetFilePath + File.separator + fileName), !isCoverOldFile)) {
            template.process(dataModel, fw);
        } catch (IOException | TemplateException e) {
            log.error("生成文件异常，cause = {}", e.getCause().getMessage());
            return false;
        }
        return true;
    }

    @Override
    protected String filePath() {
        return "classpath:template/custom";
    }
}
