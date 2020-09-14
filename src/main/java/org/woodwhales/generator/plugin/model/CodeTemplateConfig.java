package org.woodwhales.generator.plugin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.woodwhales.generator.plugin.controller.vo.CodeListPageConfigVO;
import org.woodwhales.generator.plugin.controller.vo.CodeNavigationConfigVO;

import java.util.List;

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
    private CodeNavigationConfigVO codeNavigationConfig;

    /**
     * 列表配置
     */
    private List<CodeListPageConfigVO> codeListPageConfigList;
}
