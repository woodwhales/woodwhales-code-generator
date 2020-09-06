package org.woodwhales.plugin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 模板配置表
 * 
 */
@TableName(value= "code_list_page_config")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CodeListPageConfig extends CommonField {

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
     * 导航配置表主键
     */
    private Integer codeNavigationConfigId;

    /**
     * 配置内容：json字符串格式
     */
    @TableField(value = "config_content")
    private String configContent;

}