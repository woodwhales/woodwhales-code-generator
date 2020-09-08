package org.woodwhales.plugin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.woodwhales.common.response.PageRespVO;
import org.woodwhales.plugin.entity.CodeNavigationConfig;
import org.woodwhales.plugin.service.CodeTemplateConfigService;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.9.6 22:53
 * @description:
 */
@RestController
@RequestMapping("/codeTemplate")
public class CodeTemplateController {

    @Autowired
    private CodeTemplateConfigService codeTemplateConfigService;

    @GetMapping("/page/")
    public PageRespVO<CodeNavigationConfig> page() {
        return codeTemplateConfigService.pageCodeTemplate();
    }

}
