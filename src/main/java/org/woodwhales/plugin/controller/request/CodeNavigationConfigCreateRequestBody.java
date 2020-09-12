package org.woodwhales.plugin.controller.request;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.woodwhales.plugin.controller.request.template.NavigationConfig;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.9.6 20:57
 * @description:
 */
@Data
@NoArgsConstructor
public class CodeNavigationConfigCreateRequestBody implements CodeTemplateConfigContent {

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
     * 导航配置信息
     */
    @NotNull(message = "导航配置信息不允许为空")
    private List<NavigationConfig> navigationConfigs;

    @Override
    public String getConfigJson() {
        Preconditions.checkNotNull(navigationConfigs, "导航配置信息不允许为空");
        return new Gson().toJson(navigationConfigs);
    }
}
