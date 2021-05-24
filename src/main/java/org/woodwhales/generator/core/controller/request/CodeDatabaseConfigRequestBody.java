package org.woodwhales.generator.core.controller.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author woodwhales on 2021-05-24 13:40
 * @description
 */
@Data
public class CodeDatabaseConfigRequestBody {

    /**
     * 配置名称
     */
    @NotBlank(message = "配置名称不允许为空")
    private String configName;

    /**
     * 配置备注
     */
    private String configMemo;

    /**
     * 数据库类型
     */
    @NotBlank(message = "数据库类型不允许为空")
    private String databaseType;

    /**
     * 驱动类名
     */
    @NotBlank(message = "驱动类名不允许为空")
    private String databaseDriverClassName;

    /**
     * ip地址
     */
    @NotBlank(message = "ip地址不允许为空")
    private String configIp;

    /**
     * 端口号
     */
    @NotNull(message = "端口号不允许为空")
    private Integer configPort;

    /**
     * sid
     */
    private String configSid;

    /**
     * 用户名称
     */
    @NotBlank(message = "用户名称不允许为空")
    private String configUsername;

    /**
     * 用户密码
     */
    @NotBlank(message = "用户密码不允许为空")
    private String configPassword;

    /**
     * schema
     */
    private String configSchema;

    /**
     * 代码配置
     */
    @NotBlank(message = "代码配置不允许为空")
    private String generateCodeContent;

    /**
     * 文件配置
     */
    @NotBlank(message = "文件配置不允许为空")
    private String generateFileContent;
}
