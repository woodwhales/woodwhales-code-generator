package org.woodwhales.generator.core.controller.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

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

    /**
     * 自定义kv键值对
     */
    private Map<String, Object> customKeyValueMap;
}
