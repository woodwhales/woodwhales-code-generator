package org.woodwhales.generator.core.controller.request.dbconfig;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author woodwhales on 2021-08-01 21:42
 * @Description
 */
@Data
public class BaseDbConfig {
    @NotBlank(message = "ip字段为空")
    private String ip;

    @NotNull(message = "port字段为空")
    private Integer port;

    @NotBlank(message = "username字段为空")
    private String username;

    @NotBlank(message = "password字段为空")
    private String password;

    @NotBlank(message = "数据库类型为空")
    private String dbType;

    @NotBlank(message = "驱动类名为空")
    private String driverClassName;

    private String sid;
}
