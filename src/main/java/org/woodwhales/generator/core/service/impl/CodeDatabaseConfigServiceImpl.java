package org.woodwhales.generator.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woodwhales.common.model.result.OpResult;
import org.woodwhales.common.model.vo.PageRespVO;
import org.woodwhales.generator.core.controller.request.CodeDatabaseConfigQueryParam;
import org.woodwhales.generator.core.controller.request.dbconfig.CodeDatabaseConfigGetRequestBody;
import org.woodwhales.generator.core.controller.request.dbconfig.CodeDatabaseConfigRequestBody;
import org.woodwhales.generator.core.controller.vo.CodeDatabaseConfigVO;
import org.woodwhales.generator.core.service.CodeDatabaseConfigService;
import org.woodwhales.generator.plugin.entity.CodeDatabaseConfig;
import org.woodwhales.generator.plugin.mapper.CodeDatabaseConfigMapper;
import org.woodwhales.mybatisplus.MybatisPlusExecutor;

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
    public PageRespVO<CodeDatabaseConfigVO> page(CodeDatabaseConfigQueryParam param) {
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
        return OpResult.success(CodeDatabaseConfigVO.build(getCodeDatabaseConfigByCode(requestBody.getDbConfigCode())));
    }
}
