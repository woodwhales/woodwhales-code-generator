package org.woodwhales.generator.plugin.controller.request.freemarker;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.9.13 13:47
 * @description:
 */
@Data
@NoArgsConstructor
public class CodeNavigationConfigRequestBody {

    @NotNull(message = "codeNavigationConfigId不允许为空")
    private Integer codeNavigationConfigId;
}
