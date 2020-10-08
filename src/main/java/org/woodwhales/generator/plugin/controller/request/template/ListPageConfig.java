package org.woodwhales.generator.plugin.controller.request.template;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author woodwhales
 */
@Data
@NoArgsConstructor
public class ListPageConfig {

    @NotBlank(message = "导航栏名称不允许为空")
    private String navName;

    /**
     * 数据库表名
     */
    @NotBlank(message = "数据库表名不允许为空")
    private String dbTableName;

    private List<SearchInput> searchInputs;

    private TableConfig tableConfig;
}