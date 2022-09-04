package org.woodwhales.generator.core.service.impl;

import cn.woodwhales.common.business.DataTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woodwhales.generator.core.controller.request.DataBaseRequestBody;
import org.woodwhales.generator.core.controller.request.JavaCodeConfig;
import org.woodwhales.generator.core.controller.request.MarkdownConfig;
import org.woodwhales.generator.core.entity.DataBaseInfo;
import org.woodwhales.generator.core.entity.TableInfo;
import org.woodwhales.generator.core.exception.GenerateException;
import org.woodwhales.generator.core.model.GenerateTableInfos;
import org.woodwhales.generator.core.service.GenerateInfoFactory;
import org.woodwhales.generator.core.service.GenerateService;

import java.io.File;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.8.25 22:59
 * @description:
 */
@Slf4j
@Component
public class GenerateInfoFactoryImpl implements GenerateInfoFactory {

    @Autowired
    private GenerateService generateService;

    @Override
    public GenerateTableInfos buildGenerateTableInfos(DataBaseRequestBody requestBody) throws Exception {
        DataBaseInfo dataBaseInfo = DataBaseInfo.convert(requestBody);

        JavaCodeConfig javaCodeConfig = requestBody.getJavaCodeConfig();
        MarkdownConfig markdownConfig = requestBody.getMarkdownConfig();

        Boolean generateCode = javaCodeConfig.getGenerateCode();
        Boolean generateMarkdown = markdownConfig.getGenerateMarkdown();
        GenerateTableInfos generateTableInfos = new GenerateTableInfos(generateCode, generateMarkdown, dataBaseInfo);
        // 获取数据库表结构信息
        List<TableInfo> tables = generateService.listTables(dataBaseInfo, true);
        // 生成代码
        if(generateCode) {
            // 检查目标文件目录是否为合法文件夹
            String generateDir = javaCodeConfig.getGenerateDir();
            if(StringUtils.isBlank(generateDir)) {
                throw new GenerateException("项目目录不允许为空");
            }
            File baseDir = checkBaseDirPath(generateDir);
            dataBaseInfo.getJavaCodeConfig().setInterfaceList(DataTool.toList(javaCodeConfig.getInterfaceList(), Function.identity(), true));
            generateTableInfos.javaFile(baseDir.getAbsolutePath(), tables);
        }
        // 生成markdown
        if(generateMarkdown) {
            String markdownDir = markdownConfig.getMarkdownDir();
            if(StringUtils.isBlank(markdownDir)) {
                throw new GenerateException("markdown文档目录不允许为空");
            }
            File markdownFileDir = FileUtils.getFile(markdownDir);
            if(!markdownFileDir.exists()) {
                try {
                    FileUtils.forceMkdir(markdownFileDir);
                } catch (Exception e) {
                    log.error("create dir process failed, {}", e.getMessage(), e);
                    throw new GenerateException("生成markdown的目录失败");
                }
            }
            generateTableInfos.markdownFile(markdownDir, tables);
        }
        return generateTableInfos;
    }

    /**
     * 校验基础包路径是否存在，不存在则创建
     * {baseDirPath}/src/main/java
     * @param baseDirPath
     * @return
     */
    private File checkBaseDirPath(String baseDirPath) {
        File tmpFile = FileUtils.getFile(baseDirPath + File.separator + "src" + File.separator + "main" + File.separator + "java");
        // 文件不存在则创建
        if(!tmpFile.exists()) {
            try {
                FileUtils.forceMkdir(tmpFile);
            } catch (Exception e) {
                log.error("create dir process failed, {}", e.getMessage(), e);
                throw new GenerateException("生成代码的目录失败");
            }
        }

        return tmpFile;
    }


}
