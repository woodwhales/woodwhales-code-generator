package org.woodwhales.plugin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woodwhales.plugin.controller.request.CodeNavigationConfigCreateRequestBody;
import org.woodwhales.plugin.controller.vo.CodeNavigationConfigSimpleInfoVO;
import org.woodwhales.plugin.entity.CodeNavigationConfig;
import org.woodwhales.plugin.mapper.CodeNavigationConfigMapper;
import org.woodwhales.plugin.service.CodeNavigationConfigService;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.9.6 19:21
 * @description:
 */
@Service
public class CodeNavigationConfigServiceImpl implements CodeNavigationConfigService {

    @Autowired
    private CodeNavigationConfigMapper codeNavigationConfigMapper;

    @Override
    public boolean create(CodeNavigationConfigCreateRequestBody requestBody) {
        CodeNavigationConfig codeNavigationConfig = new CodeNavigationConfig();
        BeanUtils.copyProperties(requestBody, codeNavigationConfig);
        codeNavigationConfig.setConfigContent(requestBody.getConfigJson());
        codeNavigationConfig.setStatus(0);
        Date now = Date.from(Instant.now());
        codeNavigationConfig.setGmtCreated(now);
        codeNavigationConfig.setGmtModified(now);
        int insert = codeNavigationConfigMapper.insert(codeNavigationConfig);
        return insert == 1;
    }

    @Override
    public List<CodeNavigationConfigSimpleInfoVO> listSimpleInfo() {
        List<CodeNavigationConfig> codeNavigationConfigList = listCodeNavigationConfig();
        if(CollectionUtils.isEmpty(codeNavigationConfigList)) {
            return Collections.emptyList();
        }
        return codeNavigationConfigList.stream()
                                        .map(CodeNavigationConfigSimpleInfoVO::build)
                                        .collect(Collectors.toList());
    }

    @Override
    public List<CodeNavigationConfig> listCodeNavigationConfig() {
        LambdaQueryWrapper<CodeNavigationConfig> wrapper = Wrappers.<CodeNavigationConfig>lambdaQuery().eq(CodeNavigationConfig::getStatus, 0);
        return codeNavigationConfigMapper.selectList(wrapper);
    }

    @Override
    public CodeNavigationConfig getCodeNavigationConfigById(Integer odeNavigationConfigById) {
        LambdaQueryWrapper<CodeNavigationConfig> wrapper = Wrappers.<CodeNavigationConfig>lambdaQuery()
                .eq(CodeNavigationConfig::getId, 0)
                .eq(CodeNavigationConfig::getStatus, 0);
        return codeNavigationConfigMapper.selectOne(wrapper);
    }
}
