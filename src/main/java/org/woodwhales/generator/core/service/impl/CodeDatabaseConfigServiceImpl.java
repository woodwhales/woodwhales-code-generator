package org.woodwhales.generator.core.service.impl;

import cn.woodwhales.common.model.result.OpResult;
import cn.woodwhales.common.model.vo.PageRespVO;
import cn.woodwhales.common.model.vo.RespVO;
import cn.woodwhales.common.mybatisplus.MybatisPlusExecutor;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woodwhales.generator.core.controller.request.CodeDatabaseConfigQueryParam;
import org.woodwhales.generator.core.controller.request.dbconfig.CodeDatabaseConfigDeleteRequestBody;
import org.woodwhales.generator.core.controller.request.dbconfig.CodeDatabaseConfigGetRequestBody;
import org.woodwhales.generator.core.controller.request.dbconfig.CodeDatabaseConfigRequestBody;
import org.woodwhales.generator.core.controller.request.dbconfig.CodeDatabaseConfigUpdateRequestBody;
import org.woodwhales.generator.core.controller.vo.CodeDatabaseConfigVO;
import org.woodwhales.generator.core.service.CodeDatabaseConfigService;
import org.woodwhales.generator.plugin.entity.CodeDatabaseConfig;
import org.woodwhales.generator.plugin.mapper.CodeDatabaseConfigMapper;

import java.util.Date;
import java.util.Objects;

/**
 * @author woodwhales on 2021-05-20 23:26
 * @description
 */
@Service
public class CodeDatabaseConfigServiceImpl implements CodeDatabaseConfigService {

    @Autowired
    private CodeDatabaseConfigMapper codeDatabaseConfigMapper;

    @Override
    public RespVO<PageRespVO<CodeDatabaseConfigVO>> page(CodeDatabaseConfigQueryParam param) {
        return MybatisPlusExecutor.executeQueryPage(codeDatabaseConfigMapper, param, wrapper -> {
            wrapper.like(StringUtils.isNotBlank(param.getConfigName()), CodeDatabaseConfig::getConfigName, param.getConfigName())
            .eq(CodeDatabaseConfig::getStatus, 0);
        }, CodeDatabaseConfigVO::build);
    }

    @Override
    public OpResult<Void> create(CodeDatabaseConfigRequestBody requestBody) {
        CodeDatabaseConfig newCodeDatabaseConfig = requestBody.buildConfigCodeDatabase();

        CodeDatabaseConfig oldCodeDatabaseConfig = getCodeDatabaseConfigByCode(requestBody.getConfigCode());
        if(Objects.nonNull(oldCodeDatabaseConfig)) {
            newCodeDatabaseConfig.setId(oldCodeDatabaseConfig.getId());
            newCodeDatabaseConfig.setGmtCreated(oldCodeDatabaseConfig.getGmtCreated());
            codeDatabaseConfigMapper.updateById(newCodeDatabaseConfig);
        } else {
            codeDatabaseConfigMapper.insert(newCodeDatabaseConfig);
        }

        return OpResult.success();
    }

    private CodeDatabaseConfig getCodeDatabaseConfigByCode(String configCode) {
        Wrapper<CodeDatabaseConfig> wrapper = Wrappers.<CodeDatabaseConfig>lambdaQuery()
                .eq(CodeDatabaseConfig::getStatus, 0)
                .eq(CodeDatabaseConfig::getConfigCode, configCode);
        return MybatisPlusExecutor.executeQueryOne(codeDatabaseConfigMapper, wrapper);
    }

    @Override
    public OpResult<CodeDatabaseConfigVO> get(CodeDatabaseConfigGetRequestBody requestBody) {
        return OpResult.success(CodeDatabaseConfigVO.build(getCodeDatabaseConfigByCode(requestBody.getConfigCode())));
    }

    @Override
    public OpResult<Void> delete(CodeDatabaseConfigDeleteRequestBody requestBody) {
        CodeDatabaseConfig codeDatabaseConfig = getCodeDatabaseConfigByCode(requestBody.getConfigCode());
        if(Objects.nonNull(codeDatabaseConfig)) {
            codeDatabaseConfigMapper.deleteById(codeDatabaseConfig.getId());
        }
        return OpResult.success();
    }

    @Override
    public OpResult<Void> update(CodeDatabaseConfigUpdateRequestBody requestBody) {
        CodeDatabaseConfig codeDatabaseConfig = getCodeDatabaseConfigByCode(requestBody.getConfigCode());
        if(Objects.nonNull(codeDatabaseConfig)) {
            codeDatabaseConfig.setConfigName(requestBody.getConfigName());
            codeDatabaseConfig.setConfigMemo(requestBody.getConfigMemo());
            codeDatabaseConfig.setGmtModified(new Date());
            codeDatabaseConfigMapper.updateById(codeDatabaseConfig);
        }
        return OpResult.success();
    }
}
