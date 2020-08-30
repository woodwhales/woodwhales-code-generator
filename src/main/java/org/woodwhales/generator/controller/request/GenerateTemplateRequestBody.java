package org.woodwhales.generator.controller.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.8.30 00:40
 * @description:
 */
@Data
public class GenerateTemplateRequestBody {

    @NotBlank(message = "数据库表主键不允许为空")
    private String tableKey;

    @NotBlank(message = "freemarker模板不允许为空")
    private String freemarkerTemplate;

    @NotNull(message = "字段列表不允许为空")
    private List<String> columnNameList;
}
