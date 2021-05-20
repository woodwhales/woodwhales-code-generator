package org.woodwhales.generator.core.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woodwhales.common.model.vo.PageRespVO;
import org.woodwhales.generator.core.controller.request.CodeDatabaseConfigQueryParam;
import org.woodwhales.generator.core.controller.vo.CodeDatabaseConfigVO;
import org.woodwhales.generator.core.service.CodeDatabaseConfigService;
import org.woodwhales.generator.plugin.entity.CodeDatabaseConfig;
import org.woodwhales.generator.plugin.mapper.CodeDatabaseConfigMapper;
import org.woodwhales.mybatisplus.MybatisPlusExecutor;

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
}
