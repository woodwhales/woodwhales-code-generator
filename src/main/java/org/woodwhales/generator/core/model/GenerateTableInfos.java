package org.woodwhales.generator.core.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.woodwhales.generator.core.entity.DataBaseInfo;
import org.woodwhales.generator.core.entity.TableInfo;
import org.woodwhales.generator.core.exception.GenerateException;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.8.25 22:53
 * @description:
 */
@Slf4j
@Getter
public class GenerateTableInfos {

    /**
     * markdown文件生成路径
     */
    private File markdownFile;

    /**
     * 是否生成代码
     */
    private final Boolean generateCode;

    /**
     * 是否生成数据库表结构设计文档
     */
    private final Boolean generateMarkdown;

    /**
     * java代码生成根路径
     * {项目根路径}/src/main/java/
     */
    private File javaFile;

    /**
     * 数据库连接信息
     */
    private final DataBaseInfo dataBaseInfo;

    /**
     * 数据库表结构信息结果集
     */
    private List<TableInfo> tables;

    /**
     * @param dataBaseInfo 数据库连接信息
     */
    public GenerateTableInfos(Boolean generateCode,
                              Boolean generateMarkdown,
                              DataBaseInfo dataBaseInfo) {
        Objects.requireNonNull(dataBaseInfo, "数据库链接信息对象不允许为空");
        this.dataBaseInfo = dataBaseInfo;
        this.generateCode = generateCode;
        this.generateMarkdown = generateMarkdown;
    }

    public GenerateTableInfos markdownFile(String markdownFile) {
        if(generateMarkdown && StringUtils.isNotBlank(markdownFile)) {
            // 设置 markdown 生成目录
            this.markdownFile = new File(markdownFile);
        }
        return this;
    }

    public GenerateTableInfos javaFile(String baseDirPath, List<TableInfo> tables) {
        if(generateCode && StringUtils.isNotBlank(baseDirPath)) {
            // 设置 java代码 生成目录
            this.javaFile = this.getTargetFile(baseDirPath, this.dataBaseInfo.getJavaCodeConfig().getPackageName());
            this.tables = tables;
        }
        return this;
    }

    /**
     * 创建项目路径一下的包文件目录，如果不存在则创建
     * @param baseDirPath
     * @param packageName
     * @return
     */
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
                log.error("create dir process failed, causeBy={}", e.getMessage(), e);
                throw new GenerateException("生成代码的包目录失败");
            }
        }

        return targetDirFile;
    }

    public String getSuperClassSimpleName() {
        if (hasSuperClass()) {
            return StringUtils.substringAfterLast(this.dataBaseInfo.getJavaCodeConfig().getSuperClass(), ".");
        }
        return null;
    }

    public boolean hasSuperClass() {
        return isNotBlank(this.dataBaseInfo.getJavaCodeConfig().getSuperClass());
    }

    public boolean hasInterfaceList() {
        return CollectionUtils.isNotEmpty(this.dataBaseInfo.getJavaCodeConfig().getInterfaceList());
    }

    public String getInterfaceSimpleNameListString() {
        if (hasInterfaceList()) {
            List<String> interfaceSimpleNameList = this.dataBaseInfo.getJavaCodeConfig().getInterfaceList().stream()
                    .map(interfaceName -> StringUtils.substringAfterLast(interfaceName, "."))
                    .collect(Collectors.toList());
            return StringUtils.join(interfaceSimpleNameList, ", ");
        }

        return StringUtils.EMPTY;
    }
}
