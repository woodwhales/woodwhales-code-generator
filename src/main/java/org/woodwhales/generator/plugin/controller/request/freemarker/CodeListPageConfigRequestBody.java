package org.woodwhales.generator.plugin.controller.request.freemarker;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.9.13 13:48
 * @description:
 */
@Data
@NoArgsConstructor
public class CodeListPageConfigRequestBody {

    @NotNull(message = "codeListPageConfigId不允许为空")
    private Integer codeListPageConfigId;
}
