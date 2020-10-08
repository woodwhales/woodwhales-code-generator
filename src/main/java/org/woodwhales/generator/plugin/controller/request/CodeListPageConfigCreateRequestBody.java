package org.woodwhales.generator.plugin.controller.request;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.woodwhales.generator.plugin.controller.request.template.ListPageConfig;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.9.5 14:56
 * @description:
 */
@Data
@NoArgsConstructor
public class CodeListPageConfigCreateRequestBody implements CodeTemplateConfigContent {

    @NotNull(message = "菜单配置主键不允许为空")
    private Integer codeNavigationConfigId;

    /**
     * 模板名称
     */
    @NotBlank(message = "模板名称不允许为空")
    private String configName;

    /**
     * 备注
     */
    private String description;

    /**
     * 数据库表名
     */
    @NotBlank(message = "数据库表名不允许为空")
    private String dbTableName;

    /**
     * 模板配置
     */
    @NotBlank(message = "模板配置不允许为空")
    private ListPageConfig listPageConfig;

    @Override
    public String getConfigJson() {
        Preconditions.checkArgument(Objects.nonNull(this.listPageConfig), "模板配置对象不允许为空");
        return new Gson().toJson(this.listPageConfig);
    }
}
