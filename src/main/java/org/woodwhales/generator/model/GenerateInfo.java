package org.woodwhales.generator.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.woodwhales.generator.entity.DataBaseInfo;
import org.woodwhales.generator.entity.TableInfo;

import java.io.File;
import java.util.List;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.8.25 22:53
 * @description:
 */
@Data
@NoArgsConstructor
public class GenerateInfo {

    private File markdownFile;

    private File javaFile;

    public GenerateInfo(String packageName, DataBaseInfo dataBaseInfo, List<TableInfo> tables) {
        this.packageName = packageName;
        this.dataBaseInfo = dataBaseInfo;
        this.tables = tables;
    }

    private String packageName;

    private DataBaseInfo dataBaseInfo;

    private List<TableInfo> tables;


}
