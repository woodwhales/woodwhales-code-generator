package org.woodwhales.plugin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.woodwhales.plugin.entity.CodeListPageConfig;
import org.woodwhales.plugin.entity.CodeNavigationConfig;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.9.6 22:42
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeTemplateConfig {

    /**
     * 菜单配置
     */
    private CodeNavigationConfig codeNavigationConfig;

    /**
     * 列表配置
     */
    private CodeListPageConfig codeListPageConfig;
}
