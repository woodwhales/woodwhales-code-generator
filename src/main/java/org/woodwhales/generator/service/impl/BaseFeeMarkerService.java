package org.woodwhales.generator.service.impl;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.7.26 18:51
 * @description:
 */
@Slf4j
public abstract class BaseFeeMarkerService {

    protected abstract String filePath();

    protected Configuration getConfiguration(ResourceLoader resourceLoader) {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        String locationFilePath = filePath();
        Resource resource = resourceLoader.getResource(locationFilePath);

        try {
            File file = resource.getFile();
            FileTemplateLoader templateLoader = new FileTemplateLoader(file);
            configuration.setTemplateLoader(templateLoader);
        } catch (IOException e) {
            log.error("load template process is failed, {}", e);
        }
        return configuration;
    }
}
