package org.woodwhales.plugin.controller.vo;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.9.8 22:36
 * @description:
 */

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.woodwhales.generator.util.GsonUtil;
import org.woodwhales.plugin.controller.request.template.NavigationConfig;
import org.woodwhales.plugin.entity.CodeNavigationConfig;

import java.util.List;

@Data
@NoArgsConstructor
public class CodeNavigationConfigVO {

    private Integer id;

    private String configName;

    private String description;

    private String configContent;

    List<NavigationConfig> navigationConfigs;

    public static CodeNavigationConfigVO newInstance(CodeNavigationConfig codeNavigationConfig) {
        CodeNavigationConfigVO codeNavigationConfigVO = new CodeNavigationConfigVO();
        BeanUtils.copyProperties(codeNavigationConfig, codeNavigationConfigVO);
        String configContent = codeNavigationConfig.getConfigContent();
        codeNavigationConfigVO.setNavigationConfigs(GsonUtil.fromJson(configContent));
        return codeNavigationConfigVO;
    }
}
