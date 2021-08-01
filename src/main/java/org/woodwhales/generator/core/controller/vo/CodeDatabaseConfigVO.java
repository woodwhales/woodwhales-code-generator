package org.woodwhales.generator.core.controller.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.woodwhales.generator.core.controller.request.dbconfig.ExtraCodeDbConfig;
import org.woodwhales.generator.core.controller.request.dbconfig.ExtraFileDbConfig;
import org.woodwhales.generator.plugin.entity.CodeDatabaseConfig;

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
     * schema
     */
    private String configSchema;

    /**
     * 代码配置
     */
    private ExtraCodeDbConfig extraCodeDbConfig;

    /**
     * 文件配置
     */
    private ExtraFileDbConfig extraFileDbConfig;

    /**
     * 创建时间
     */
    private java.util.Date gmtCreated;

    /**
     * 更新时间
     */
    private java.util.Date gmtModified;

    public static CodeDatabaseConfigVO build(CodeDatabaseConfig codeDatabaseConfig) {
        CodeDatabaseConfigVO codeDatabaseConfigVO = new CodeDatabaseConfigVO();
        BeanUtils.copyProperties(codeDatabaseConfig, codeDatabaseConfigVO);
        codeDatabaseConfigVO.setExtraCodeDbConfig(new Gson().fromJson(codeDatabaseConfig.getGenerateCodeContent(),
                ExtraCodeDbConfig.class));
        codeDatabaseConfigVO.setExtraFileDbConfig(new Gson().fromJson(codeDatabaseConfig.getGenerateFileContent(),
                ExtraFileDbConfig.class));
        return codeDatabaseConfigVO;
    }

}
