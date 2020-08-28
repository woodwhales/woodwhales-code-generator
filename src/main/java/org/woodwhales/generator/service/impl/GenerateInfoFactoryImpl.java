package org.woodwhales.generator.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woodwhales.generator.controller.request.DataBaseRequestBody;
import org.woodwhales.generator.entity.DataBaseInfo;
import org.woodwhales.generator.entity.TableInfo;
import org.woodwhales.generator.exception.GenerateException;
import org.woodwhales.generator.model.GenerateInfo;
import org.woodwhales.generator.service.GenerateInfoFactory;
import org.woodwhales.generator.service.GenerateService;

import java.io.File;
import java.util.List;

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
    public GenerateInfo build(DataBaseRequestBody requestBody) throws Exception {
        // 检查目标文件目录是否为合法文件夹
        String generateDir = requestBody.getGenerateDir();
        File baseDir = checkBaseDirPath(generateDir);

        DataBaseInfo dataBaseInfo = DataBaseInfo.convert(requestBody);

        List<TableInfo> tables = generateService.listTables(dataBaseInfo, true);

        String packageName = requestBody.getPackageName();
        final Boolean overCode = requestBody.getOverCode();
        final Boolean overMarkdown = requestBody.getOverMarkdown();
        final Boolean generateCode = requestBody.getGenerateCode();
        final Boolean generateMarkdown = requestBody.getGenerateMarkdown();
        final String superClass = requestBody.getSuperClass();
        final List<String> interfaceList = requestBody.getInterfaceList();

        return new GenerateInfo(baseDir.getAbsolutePath(), packageName,
                dataBaseInfo, tables, overCode, overMarkdown,
                generateCode, generateMarkdown, superClass, interfaceList);
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
                log.error("create dir process failed, {}", e);
                throw new GenerateException("生成代码的目录失败");
            }
        }

        return tmpFile;
    }


}
