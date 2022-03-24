package org.woodwhales.generator.core.service.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.woodwhales.generator.core.entity.DataBaseInfo;
import org.woodwhales.generator.core.entity.TableInfo;
import org.woodwhales.generator.core.model.GenerateTableInfos;
import org.woodwhales.generator.core.service.FreeMarkerService;

import java.io.File;
import java.util.HashMap;

import static org.apache.commons.lang3.StringUtils.defaultIfBlank;
import static org.apache.commons.lang3.StringUtils.lowerCase;

/**
 * 模板接口实现
 *
 * @author woodwhales
 */
@Slf4j
@Service("javaFileService")
public class JavaFileServiceImpl extends BaseFeeMarkerService implements FreeMarkerService {

    @Override
    public boolean process(GenerateTableInfos generateTableInfos) throws Exception {
        DataBaseInfo dataBaseInfo = generateTableInfos.getDataBaseInfo();
        if (!dataBaseInfo.getGenerateCode()) {
            return true;
        }

        Configuration configuration = this.initConfiguration("/template/java/" + lowerCase(dataBaseInfo.getOrm()));
        HashMap<String, Object> dataModel = new HashMap<>(16);
        dataModel.put("settings", dataBaseInfo);
        dataModel.put("packageName", dataBaseInfo.getPackageName());
        String targetFilePath = generateTableInfos.getJavaFile().getAbsolutePath();

        final Boolean isCoverOldFile = dataBaseInfo.getOverCode();

        for (TableInfo tableInfo : generateTableInfos.getTables()) {
            dataModel.put("table", tableInfo);
            // 生成entity
            this.entityProcess(dataModel, targetFilePath, tableInfo.getName(), generateTableInfos, configuration);
            // 生成mapper
            this.process(dataModel, targetFilePath, "mapper", tableInfo.getName() + "Mapper", isCoverOldFile, configuration);

            if (dataBaseInfo.getGenerateBatchMapper()) {
                this.process(dataModel, targetFilePath, "batchmapper", "Batch" + tableInfo.getName() + "Mapper", isCoverOldFile, configuration);
            }

            if (tableInfo.getKeys().size() > 0) {
                dataModel.put("primaryKey", tableInfo.getKeys().get(0));
            } else {
                dataModel.put("primaryKey", null);
            }

            if (tableInfo.getKeyTypes().size() > 0) {
                dataModel.put("primaryKeyType", tableInfo.getKeyTypes().get(0));
            } else {
                dataModel.put("primaryKeyType", null);
            }

            if (dataBaseInfo.getGenerateController()) {
                // 生成controller
                this.process(dataModel, targetFilePath, "controller", tableInfo.getName() + "Controller", isCoverOldFile, configuration);
            }

            if (dataBaseInfo.getGenerateService()) {
                // 生成service
                this.process(dataModel, targetFilePath, "service", tableInfo.getName() + "Service", isCoverOldFile, configuration);
                // 生成service.impl
                this.process(dataModel, targetFilePath, "service.impl", tableInfo.getName() + "ServiceImpl", isCoverOldFile, configuration);
            }
        }
        return true;
    }

    private boolean entityProcess(HashMap<String, Object> dataModel, String targetFilePath,
                                  String fileName, GenerateTableInfos generateTableInfos, Configuration configuration) throws Exception {
        if (generateTableInfos.hasSuperClass()) {
            dataModel.put("hasSuperClass", generateTableInfos.hasSuperClass());
            dataModel.put("superClass", generateTableInfos.getDataBaseInfo().getSuperClass());
            dataModel.put("superClassSimpleName", generateTableInfos.getSuperClassSimpleName());
        }

        if (generateTableInfos.hasInterfaceList()) {
            dataModel.put("hasInterfaceList", generateTableInfos.hasInterfaceList());
            dataModel.put("interfaceSimpleNameListString", generateTableInfos.getInterfaceSimpleNameListString());
            dataModel.put("interfaceList", generateTableInfos.getDataBaseInfo().getInterfaceList());
        }

        Boolean overCode = generateTableInfos.getDataBaseInfo().getOverCode();
        return this.process(dataModel, targetFilePath, "entity", fileName, overCode, configuration);
    }

    private boolean process(HashMap<String, Object> dataModel, String targetFilePath,
                            String templateName, String fileName, Boolean isCoverOldFile,
                            Configuration configuration) throws Exception {
        Template template = configuration.getTemplate(templateName + ".ftl");

        String[] split = StringUtils.split(templateName, ".");
        StringBuffer sb = new StringBuffer(targetFilePath);
        for (String str : split) {
            sb.append(File.separator);
            sb.append(str);
        }
        String markdownFileDir = sb.toString();
        FileUtils.forceMkdir(new File(markdownFileDir));

        return process(template, markdownFileDir, fileName + ".java", dataModel, isCoverOldFile);
    }

}
