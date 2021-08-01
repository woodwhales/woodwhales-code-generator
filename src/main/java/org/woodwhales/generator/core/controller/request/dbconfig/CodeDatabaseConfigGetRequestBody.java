package org.woodwhales.generator.core.controller.request.dbconfig;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author woodwhales on 2021-08-01 23:29
 * @Description
 */
@Data
public class CodeDatabaseConfigGetRequestBody {

    @NotBlank(message = "配置编号不允许为空")
    private String dbConfigCode;

}
