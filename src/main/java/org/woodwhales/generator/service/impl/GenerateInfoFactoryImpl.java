package org.woodwhales.generator.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
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
    public GenerateInfo build(DataBaseRequestBody dataBaseRequestBody) throws Exception {
        // 检查目标文件目录是否为合法文件夹
        String baseDirPath = dataBaseRequestBody.getGenerateDir();
        File baseDir = checkBaseDirPath(baseDirPath);

        String packageName = dataBaseRequestBody.getPackageName();
        DataBaseInfo dataBaseInfo = DataBaseInfo.convert(dataBaseRequestBody);

        List<TableInfo> tables = generateService.listTables(dataBaseInfo, true);

        return buildGenerateInfo(packageName, dataBaseInfo, tables, baseDir, baseDirPath);
    }

    private GenerateInfo buildGenerateInfo(String packageName, DataBaseInfo dataBaseInfo, List<TableInfo> tables,
                                           File baseDir, String baseDirPath) {
        GenerateInfo generateInfo = new GenerateInfo(packageName, dataBaseInfo, tables);

        // 设置 markdown 生成目录
        generateInfo.setMarkdownFile(new File(baseDirPath));

        // 设置 java代码 生成目录
        File targetFile = getTargetFile(baseDir.getAbsolutePath(), packageName);
        generateInfo.setJavaFile(targetFile);

        return generateInfo;
    }

    private File checkBaseDirPath(String baseDirPath) {
        File tmpFile = FileUtils.getFile(baseDirPath + File.separator + "src" + File.separator + "main" + File.separator + "java");
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

    private File getTargetFile(String baseDirPath, String packageName) {
        String[] dirNames = StringUtils.split(packageName, ".");
        StringBuffer sb = new StringBuffer();
        for (String dirName : dirNames) {
            sb.append(File.separator + dirName);
        }
        String packageDir = sb.toString();

        String targetDir =  baseDirPath + File.separator + packageDir;
        File targetDirFile = new File(targetDir);
        if(!targetDirFile.exists()) {
            try {
                FileUtils.forceMkdir(targetDirFile);
            } catch (Exception e) {
                log.error("create dir process failed, {}", e);
                throw new GenerateException("生成代码的包目录失败");
            }
        }

        return targetDirFile;
    }
}
