package org.woodwhales.generator.core.controller.request.dbconfig;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author woodwhales on 2022-04-07 9:51
 */
@Data
public class CodeDatabaseConfigUpdateRequestBody extends CodeDatabaseConfigRequestBody {

    @NotBlank(message = "configCode不能为空")
    private String configCode;

}
