package org.woodwhales.plugin.service;

import org.woodwhales.plugin.controller.request.CodeNavigationConfigCreateRequestBody;
import org.woodwhales.plugin.controller.vo.CodeNavigationConfigSimpleInfoVO;
import org.woodwhales.plugin.entity.CodeNavigationConfig;

import java.util.List;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.9.6 19:20
 * @description:
 */
public interface CodeNavigationConfigService {

    /**
     * 创建 CodeNavigationConfig
     * @param requestBody
     * @return
     */
    boolean create(CodeNavigationConfigCreateRequestBody requestBody);

    /**
     * 查询所有配置
     * @return
     */
    List<CodeNavigationConfigSimpleInfoVO> listSimpleInfo();

    /**
     * 查询所有菜单配置
     * @return
     */
    List<CodeNavigationConfig> listCodeNavigationConfig();

    /**
     * 根据主键查询配置信息
     * @param odeNavigationConfigById
     * @return
     */
    CodeNavigationConfig getCodeNavigationConfigById(Integer odeNavigationConfigById);
}
