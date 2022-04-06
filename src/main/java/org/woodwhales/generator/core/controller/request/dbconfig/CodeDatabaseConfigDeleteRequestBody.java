package org.woodwhales.generator.core.controller.request.dbconfig;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author woodwhales on 2022-04-06 19:34
 */
@Data
public class CodeDatabaseConfigDeleteRequestBody {

    @NotBlank(message = "要删除的配置configCode不能为空")
    private String configCode;

}
