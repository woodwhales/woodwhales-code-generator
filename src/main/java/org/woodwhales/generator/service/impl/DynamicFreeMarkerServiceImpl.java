package org.woodwhales.generator.service.impl;

import com.google.common.base.Preconditions;
import freemarker.cache.StringTemplateLoader;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.woodwhales.generator.entity.TableInfo;
import org.woodwhales.generator.exception.GenerateException;
import org.woodwhales.generator.service.DynamicFreeMarkerService;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.8.30 10:36
 * @description:
 */
@Service
public class DynamicFreeMarkerServiceImpl implements DynamicFreeMarkerService {

    private Configuration initConfiguration() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        configuration.setTemplateLoader(templateLoader);
        return configuration;
    }

    @Override
    public String dynamicProcess(String freemarkerTemplate, TableInfo tableInfo, Map<String, Object> customKeyValueMap) throws Exception {
        Preconditions.checkArgument(StringUtils.isNotBlank(freemarkerTemplate), "freemarker模板不能为空");
        Preconditions.checkArgument(Objects.nonNull(tableInfo), "数据库表结构信息不能为空");
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(tableInfo.getColumns()), "数据库表结构字段信息不能为空");

        Configuration configuration = initConfiguration();
        StringWriter stringWriter = new StringWriter();

        Template template;
        try {
            template = new Template(null, freemarkerTemplate, configuration);
        } catch (ParseException e) {
            throw new GenerateException("模板语法不正确");
        }

        Map<String, Object> dataModel = getDataModel(tableInfo, customKeyValueMap);

        template.process(dataModel, stringWriter);
        return stringWriter.toString();
    }

    private Map<String, Object> getDataModel(TableInfo tableInfo, Map<String, Object> customKeyValueMap) {
        HashMap<String, Object> dataModel = new HashMap<>(16);
        dataModel.put("columns", tableInfo.getColumns());
        dataModel.put("dbName", tableInfo.getDbName());
        dataModel.put("name", tableInfo.getName());
        if(MapUtils.isNotEmpty(customKeyValueMap)) {
            customKeyValueMap.entrySet().forEach(entry -> dataModel.put(entry.getKey(), entry.getValue()));
        }
        return dataModel;
    }
}
