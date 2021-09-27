package org.woodwhales.generator.core.controller.request.dbconfig;

import cn.hutool.crypto.SecureUtil;
import com.google.gson.Gson;
import lombok.Data;
import org.woodwhales.generator.plugin.entity.CodeDatabaseConfig;

import javax.validation.constraints.NotNull;

/**
 * @author woodwhales on 2021-05-24 13:40
 * @description
 */
@Data
public class CodeDatabaseConfigRequestBody {

    /**
     * 扩展配置
     */
    private ExtraFileDbConfig extraFileDbConfig;

    /**
     * 扩展配置
     */
    private ExtraCodeDbConfig extraCodeDbConfig;

    /**
     * 基础配置
     */
    @NotNull(message = "基础配置不允许为空")
    private BaseDbConfig baseDbConfig;

    /**
     * 配置名称
     */
    @NotNull(message = "配置名称不允许为空")
    private String dbConfigName;

    /**
     * 备注
     */
    private String dbConfigMemo;

    public String getConfigCode() {
        String ip = this.baseDbConfig.getIp();
        Integer port = this.baseDbConfig.getPort();
        String driverClassName = this.baseDbConfig.getDriverClassName();
        String sid = this.baseDbConfig.getSid();
        String configCode = ip + port + driverClassName + sid;
        return SecureUtil.sha256(configCode);
    }

    public CodeDatabaseConfig buildConfigCodeDatabase() {
        CodeDatabaseConfig codeDatabaseConfig = new CodeDatabaseConfig();
        codeDatabaseConfig.setConfigCode(this.getConfigCode());
        codeDatabaseConfig.setConfigName(this.dbConfigName);
        codeDatabaseConfig.setConfigMemo(this.dbConfigMemo);

        codeDatabaseConfig.setDatabaseType(this.baseDbConfig.getDbType());
        codeDatabaseConfig.setDatabaseDriverClassName(this.baseDbConfig.getDriverClassName());
        codeDatabaseConfig.setConfigIp(this.baseDbConfig.getIp());
        codeDatabaseConfig.setConfigPort(this.baseDbConfig.getPort());
        codeDatabaseConfig.setConfigSid(this.baseDbConfig.getSid());
        codeDatabaseConfig.setConfigUsername(this.baseDbConfig.getUsername());
        codeDatabaseConfig.setConfigPassword(this.baseDbConfig.getPassword());
        codeDatabaseConfig.setConfigSchema(null);

        codeDatabaseConfig.setGenerateCodeContent(new Gson().toJson(this.extraCodeDbConfig));
        codeDatabaseConfig.setGenerateFileContent(new Gson().toJson(this.extraFileDbConfig));

        return codeDatabaseConfig;
    }
}
