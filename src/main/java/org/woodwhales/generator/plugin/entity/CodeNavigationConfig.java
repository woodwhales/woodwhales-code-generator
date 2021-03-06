package org.woodwhales.generator.plugin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 导航配置表
 * 
 */
@TableName(value= "code_navigation_config")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CodeNavigationConfig extends CommonField {

    /**
     * 模板配置表主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 模板名称
     */
    @TableField(value = "config_name")
    private String configName;

    /**
     * 备注
     */
    @TableField(value = "description")
    private String description;

    /**
     * 配置内容：json字符串格式
     */
    @TableField(value = "config_content")
    private String configContent;

}