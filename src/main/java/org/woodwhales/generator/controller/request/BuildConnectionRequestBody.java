package org.woodwhales.generator.controller.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author woodwhales on 2020-08-28
 * @description
 */
@Data
@NoArgsConstructor
public class BuildConnectionRequestBody {

    @NotBlank(message = "ip字段为空")
    private String ip;

    @NotBlank(message = "port字段为空")
    private Integer port;

    @NotBlank(message = "username字段为空")
    private String username;

    @NotBlank(message = "password字段为空")
    private String password;

}
