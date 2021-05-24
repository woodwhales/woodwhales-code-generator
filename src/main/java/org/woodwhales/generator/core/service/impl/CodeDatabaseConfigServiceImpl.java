package org.woodwhales.generator.core.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woodwhales.common.model.result.OpResult;
import org.woodwhales.common.model.vo.PageRespVO;
import org.woodwhales.generator.core.controller.request.CodeDatabaseConfigQueryParam;
import org.woodwhales.generator.core.controller.request.CodeDatabaseConfigRequestBody;
import org.woodwhales.generator.core.controller.vo.CodeDatabaseConfigVO;
import org.woodwhales.generator.core.service.CodeDatabaseConfigService;
import org.woodwhales.generator.plugin.entity.CodeDatabaseConfig;
import org.woodwhales.generator.plugin.mapper.CodeDatabaseConfigMapper;
import org.woodwhales.mybatisplus.MybatisPlusExecutor;

import java.util.Date;

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
        }, codeDatabaseConfig -> {
            CodeDatabaseConfigVO codeDatabaseConfigVO = new CodeDatabaseConfigVO();
            BeanUtils.copyProperties(codeDatabaseConfig, codeDatabaseConfigVO);
            return codeDatabaseConfigVO;
        });
    }

    @Override
    public OpResult<Void> create(CodeDatabaseConfigRequestBody requestBody) {
        Date now = new Date();
        CodeDatabaseConfig codeDatabaseConfig = new CodeDatabaseConfig();
        BeanUtils.copyProperties(requestBody, codeDatabaseConfig);
        codeDatabaseConfig.setConfigCode(IdWorker.getIdStr());
        codeDatabaseConfig.setStatus(0);
        codeDatabaseConfig.setGmtCreated(now);
        codeDatabaseConfig.setGmtModified(now);
        codeDatabaseConfigMapper.insert(codeDatabaseConfig);
        return OpResult.success();
    }
}
