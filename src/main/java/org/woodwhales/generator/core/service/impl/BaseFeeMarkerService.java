package org.woodwhales.generator.core.service.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.7.26 18:51
 * @description:
 */
@Slf4j
public abstract class BaseFeeMarkerService {

    protected Configuration initConfiguration(String templateFilePath) {
        try {
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
            configuration.setDefaultEncoding("UTF-8");
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            configuration.setClassForTemplateLoading(this.getClass(), templateFilePath);
            return configuration;
        } catch (Exception e) {
            log.error("load template process is failed, causeBy={}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 生成文件
     * @param template freemarker生成的template
     * @param targetFileDir 生成文件的文件夹目录
     * @param targetFileName 生成文件的文件名
     * @param dataModel 数据对象
     * @param isCoverOldFile 是否覆盖原来的文件
     * @return
     */
    protected boolean process(Template template, String targetFileDir, String targetFileName, HashMap<String, Object> dataModel, boolean isCoverOldFile) {
        return process(template, targetFileDir + File.separator + targetFileName, dataModel, isCoverOldFile);
    }

    /**
     * 生成文件
     * @param template freemarker生成的template
     * @param targetFileAbsoluteName 生成文件的绝对路径名
     * @param dataModel 数据对象
     * @param isCoverOldFile 是否覆盖原来的文件
     * @return
     */
    protected boolean process(Template template, String targetFileAbsoluteName, HashMap<String, Object> dataModel, boolean isCoverOldFile) {
        try(FileWriter fw = new FileWriter(targetFileAbsoluteName, !isCoverOldFile)) {
            template.process(dataModel, fw);
        } catch (IOException | TemplateException e) {
            log.error("生成文件异常，cause = {}", e.getMessage(), e);
            return false;
        }

        return true;
    }

}
