package org.woodwhales.generator.model;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.woodwhales.generator.entity.DataBaseInfo;
import org.woodwhales.generator.entity.TableInfo;
import org.woodwhales.generator.exception.GenerateException;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.8.25 22:53
 * @description:
 */
@Slf4j
public class GenerateInfo {

    private File markdownFile;

    private File javaFile;

    /**
     * 是否覆盖markdown
     */
    private Boolean overMarkdown;

    /**
     * 是否覆盖markdown
     */
    private Boolean overCode;

    /**
     * 是否生成代码
     */
    private Boolean generateCode;

    /**
     * 是否生成数据库表结构设计文档
     */
    private Boolean generateMarkdown;

    private String packageName;

    private DataBaseInfo dataBaseInfo;

    private List<TableInfo> tables;

    /**
     * 父类
     */
    private String superClass;

    /**
     * 接口
     */
    private List<String> interfaceList;

    public GenerateInfo(String baseDirPath, String packageName, DataBaseInfo dataBaseInfo,
                        List<TableInfo> tables, Boolean overCode, Boolean overMarkdown,
                        Boolean generateCode, Boolean generateMarkdown,
                        String superClass, List<String> interfaceList) {
        this.packageName = packageName;
        this.dataBaseInfo = dataBaseInfo;
        this.tables = tables;
        this.overCode = overCode;
        this.overMarkdown = overMarkdown;
        this.generateCode = generateCode;
        this.generateMarkdown = generateMarkdown;
        this.superClass = superClass;
        this.interfaceList = interfaceList;
        // 设置 markdown 生成目录
        this.markdownFile = new File(baseDirPath);
        // 设置 java代码 生成目录
        this.javaFile = getTargetFile(baseDirPath, packageName);

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

    public File getMarkdownFile() {
        return markdownFile;
    }

    public File getJavaFile() {
        return javaFile;
    }

    public Boolean getOverMarkdown() {
        return overMarkdown;
    }

    public Boolean getOverCode() {
        return overCode;
    }

    public Boolean getGenerateCode() {
        return generateCode;
    }

    public Boolean getGenerateMarkdown() {
        return generateMarkdown;
    }

    public String getPackageName() {
        return packageName;
    }

    public DataBaseInfo getDataBaseInfo() {
        return dataBaseInfo;
    }

    public List<TableInfo> getTables() {
        return tables;
    }

    public String getSuperClass() {
        return superClass;
    }

    public String getSuperClassSimpleName() {
        if (hasSuperClass()) {
            return StringUtils.substringAfterLast(this.superClass, ".");
        }
        return null;
    }

    public boolean hasSuperClass() {
        return StringUtils.isNotBlank(this.superClass);
    }

    public boolean hasInterfaceList() {
        return CollectionUtils.isNotEmpty(this.interfaceList);
    }

    public String getInterfaceSimpleNameListString() {
        if (hasInterfaceList()) {
            List<String> interfaceSimpleNameList = this.interfaceList.stream()
                    .map(interfaceName -> StringUtils.substringAfterLast(interfaceName, "."))
                    .collect(Collectors.toList());
            return StringUtils.join(interfaceSimpleNameList, ", ");
        }

        return StringUtils.EMPTY;
    }

    public List<String> getInterfaceList() {
        return interfaceList;
    }
}
