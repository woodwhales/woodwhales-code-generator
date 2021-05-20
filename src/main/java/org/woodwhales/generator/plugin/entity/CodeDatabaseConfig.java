package org.woodwhales.generator.plugin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 数据库连接配置表
 *
 * @author woodwhales on 2021-05-20 23:45:37
 *
 */
@TableName(value= "code_database_config")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeDatabaseConfig implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 数据库连接配置表主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 配置编号
     */
    @TableField(value = "config_code")
    private String configCode;

    /**
     * 配置名称
     */
    @TableField(value = "config_name")
    private String configName;

    /**
     * 配置备注
     */
    @TableField(value = "config_memo")
    private String configMemo;

    /**
     * 数据库类型
     */
    @TableField(value = "database_type")
    private String databaseType;

    /**
     * 驱动类名
     */
    @TableField(value = "database_driver_class_name")
    private String databaseDriverClassName;

    /**
     * ip地址
     */
    @TableField(value = "config_ip")
    private String configIp;

    /**
     * 端口号
     */
    @TableField(value = "config_port")
    private Integer configPort;

    /**
     * sid
     */
    @TableField(value = "config_sid")
    private String configSid;

    /**
     * 用户名称
     */
    @TableField(value = "config_username")
    private String configUsername;

    /**
     * 用户密码
     */
    @TableField(value = "config_password")
    private String configPassword;

    /**
     * schema
     */
    @TableField(value = "config_schema")
    private String configSchema;

    /**
     * 代码配置
     */
    @TableField(value = "generate_code_content")
    private String generateCodeContent;

    /**
     * 文件配置
     */
    @TableField(value = "generate_file_content")
    private String generateFileContent;

    /**
     * 状态码：-1 删除，0 启用，1 禁用
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(value = "gmt_created")
    private java.util.Date gmtCreated;

    /**
     * 更新时间
     */
    @TableField(value = "gmt_modified")
    private java.util.Date gmtModified;

}