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
     * java代码生成根路径
     * {项目根路径}/src/main/java/
     */
    private File javaFile;

    /**
     * 数据库连接信息
     */
    private DataBaseInfo dataBaseInfo;

    /**
     * 数据库表结构信息结果集
     */
    private List<TableInfo> tables;

    /**
     *
     *
     * @param generateDir 项目根目录
     * @param baseDirPath 项目的java源码目录：xxx项目/src/main/java/
     * @param dataBaseInfo 数据库连接信息
     * @param tables 数据库表信息集合
     */
    public GenerateTableInfos(String generateDir, String baseDirPath,
                              DataBaseInfo dataBaseInfo, List<TableInfo> tables) {
        Objects.requireNonNull(dataBaseInfo, "数据库链接信息对象不允许为空");
        this.dataBaseInfo = dataBaseInfo;
        this.tables = tables;
        // 设置 markdown 生成目录
        this.markdownFile = new File(generateDir);
        // 设置 java代码 生成目录
        this.javaFile = getTargetFile(baseDirPath, this.dataBaseInfo.getPackageName());
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
                log.error("create dir process failed, {}", e);
                throw new GenerateException("生成代码的包目录失败");
            }
        }

        return targetDirFile;
    }

    public String getSuperClassSimpleName() {
        if (hasSuperClass()) {
            return StringUtils.substringAfterLast(this.dataBaseInfo.getSuperClass(), ".");
        }
        return null;
    }

    public boolean hasSuperClass() {
        return StringUtils.isNotBlank(this.dataBaseInfo.getSuperClass());
    }

    public boolean hasInterfaceList() {
        return CollectionUtils.isNotEmpty(this.dataBaseInfo.getInterfaceList());
    }

    public String getInterfaceSimpleNameListString() {
        if (hasInterfaceList()) {
            List<String> interfaceSimpleNameList = this.dataBaseInfo.getInterfaceList().stream()
                    .map(interfaceName -> StringUtils.substringAfterLast(interfaceName, "."))
                    .collect(Collectors.toList());
            return StringUtils.join(interfaceSimpleNameList, ", ");
        }

        return StringUtils.EMPTY;
    }
}
