package org.woodwhales.generator.core.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woodwhales.generator.core.controller.request.DataBaseRequestBody;
import org.woodwhales.generator.core.entity.DataBaseInfo;
import org.woodwhales.generator.core.entity.TableInfo;
import org.woodwhales.generator.core.exception.GenerateException;
import org.woodwhales.generator.core.model.GenerateTableInfos;
import org.woodwhales.generator.core.service.GenerateInfoFactory;
import org.woodwhales.generator.core.service.GenerateService;

import java.io.File;
import java.util.List;
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
        // 检查目标文件目录是否为合法文件夹
        String generateDir = requestBody.getJavaCodeConfig().getGenerateDir();
        File baseDir = checkBaseDirPath(generateDir);

        DataBaseInfo dataBaseInfo = DataBaseInfo.convert(requestBody);

        List<String> interfaceList = requestBody.getJavaCodeConfig().getInterfaceList();
        if(CollectionUtils.isNotEmpty(interfaceList)) {
            dataBaseInfo.getJavaCodeConfig().setInterfaceList(interfaceList.stream().distinct().collect(Collectors.toList()));
        }

        // 获取数据库表结构信息
        List<TableInfo> tables = generateService.listTables(dataBaseInfo, true);

        return new GenerateTableInfos(generateDir, baseDir.getAbsolutePath(), dataBaseInfo, tables);
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
