package org.woodwhales.generator.controller.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.8.29 20:34
 * @description:
 */
@Data
@NoArgsConstructor
public class DataBaseTableRequestBody {

    @NotBlank(message = "数据库表主键不允许为空")
    private String tableKey;

}
