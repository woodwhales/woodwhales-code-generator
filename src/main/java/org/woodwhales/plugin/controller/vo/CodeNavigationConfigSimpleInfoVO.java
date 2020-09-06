package org.woodwhales.plugin.controller.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.woodwhales.plugin.entity.CodeNavigationConfig;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.9.6 21:32
 * @description:
 */
@Data
@NoArgsConstructor
public class CodeNavigationConfigSimpleInfoVO {

    /**
     * 模板配置表主键
     */
    private Integer id;

    /**
     * 模板名称
     */
    private String configName;

    public static CodeNavigationConfigSimpleInfoVO build(CodeNavigationConfig codeNavigationConfig) {
        CodeNavigationConfigSimpleInfoVO codeNavigationConfigSimpleInfoVO = new CodeNavigationConfigSimpleInfoVO();
        BeanUtils.copyProperties(codeNavigationConfig, codeNavigationConfigSimpleInfoVO);
        return codeNavigationConfigSimpleInfoVO;
    }
}
