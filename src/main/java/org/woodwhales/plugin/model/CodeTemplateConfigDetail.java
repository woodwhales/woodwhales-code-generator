package org.woodwhales.plugin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.woodwhales.plugin.controller.vo.CodeListPageConfigVO;
import org.woodwhales.plugin.controller.vo.CodeNavigationConfigVO;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.9.13 22:21
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeTemplateConfigDetail {

    /**
     * 菜单配置
     */
    private CodeNavigationConfigVO codeNavigationConfig;

    /**
     * 列表配置
     */
    private CodeListPageConfigVO codeListPageConfig;

}
