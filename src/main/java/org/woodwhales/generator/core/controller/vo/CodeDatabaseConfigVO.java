package org.woodwhales.generator.core.controller.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author woodwhales on 2021-05-20 23:23
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeDatabaseConfigVO {

    /**
     * 配置编号
     */
    private String configCode;

    /**
     * 配置名称
     */
    private String configName;

    /**
     * 配置备注
     */
    private String configMemo;

    /**
     * 数据库类型
     */
    private String databaseType;

    /**
     * 驱动类名
     */
    private String databaseDriverClassName;

    /**
     * ip地址
     */
    private String configIp;

    /**
     * 端口号
     */
    private Integer configPort;

    /**
     * sid
     */
    private String configSid;

    /**
     * 用户名称
     */
    private String configUsername;

    /**
     * 用户密码
     */
    private String configPassword;

    /**
     * schema
     */
    private String configSchema;

    /**
     * 代码配置
     */
    private String generateCodeContent;

    /**
     * 文件配置
     */
    private String generateFileContent;

    /**
     * 创建时间
     */
    private java.util.Date gmtCreated;

    /**
     * 更新时间
     */
    private java.util.Date gmtModified;

}
