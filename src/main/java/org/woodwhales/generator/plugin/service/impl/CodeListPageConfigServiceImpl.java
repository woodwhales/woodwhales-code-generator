package org.woodwhales.generator.plugin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woodwhales.generator.plugin.controller.request.CodeListPageConfigCreateRequestBody;
import org.woodwhales.generator.plugin.entity.CodeListPageConfig;
import org.woodwhales.generator.plugin.mapper.CodeListPageConfigMapper;
import org.woodwhales.generator.plugin.service.CodeListPageConfigService;

import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.9.6 19:21
 * @description:
 */
@Service
public class CodeListPageConfigServiceImpl implements CodeListPageConfigService {

    @Autowired
    private CodeListPageConfigMapper codeListPageConfigMapper;

    @Override
    public boolean create(CodeListPageConfigCreateRequestBody requestBody) {
        CodeListPageConfig codeListPageConfig = new CodeListPageConfig();
        BeanUtils.copyProperties(requestBody, codeListPageConfig);
        codeListPageConfig.setConfigContent(requestBody.getConfigJson());
        codeListPageConfig.setStatus(0);
        Date now = Date.from(Instant.now());
        codeListPageConfig.setGmtCreated(now);
        codeListPageConfig.setGmtModified(now);
        int insert = codeListPageConfigMapper.insert(codeListPageConfig);
        return insert == 1;
    }

    @Override
    public CodeListPageConfig getCodeListPageConfigById(Integer codeListPageConfigId) {
        LambdaQueryWrapper<CodeListPageConfig> wrapper = Wrappers.<CodeListPageConfig>lambdaQuery()
                .eq(CodeListPageConfig::getId, codeListPageConfigId)
                .eq(CodeListPageConfig::getStatus, 0);
        return codeListPageConfigMapper.selectOne(wrapper);
    }

    @Override
    public List<CodeListPageConfig> getCodeListPageConfigListByCodeNavigationConfigId(Integer codeNavigationConfigId) {
        LambdaQueryWrapper<CodeListPageConfig> wrapper = Wrappers.<CodeListPageConfig>lambdaQuery()
                .eq(CodeListPageConfig::getCodeNavigationConfigId, codeNavigationConfigId)
                .eq(CodeListPageConfig::getStatus, 0);
        return codeListPageConfigMapper.selectList(wrapper);
    }
}
