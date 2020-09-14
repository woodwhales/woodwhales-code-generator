package org.woodwhales.generator.core.controller.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author woodwhales on 2020-08-28
 * @description
 */
@Data
@NoArgsConstructor
public class BuildConnectionRequestBody {

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
    private String driveClassName;

    /**
     * oracel 数据库的 sid
     */
    private String sid;

    /**
     * 数据库名称
     */
    private String schema;

}
