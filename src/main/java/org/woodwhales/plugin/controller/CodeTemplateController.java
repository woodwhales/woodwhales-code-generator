package org.woodwhales.plugin.controller;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.woodwhales.common.response.RespVO;
import org.woodwhales.plugin.model.CodeTemplateConfig;
import org.woodwhales.plugin.service.CodeTemplateConfigService;

import java.util.Collections;
import java.util.List;

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

    @GetMapping("/list/")
    public RespVO<List<CodeTemplateConfig>> list() {
        List<CodeTemplateConfig> codeTemplateConfigList = codeTemplateConfigService.listCodeTemplate();
        if(CollectionUtils.isEmpty(codeTemplateConfigList)) {
            codeTemplateConfigList = Collections.emptyList();
        }
        return RespVO.success(codeTemplateConfigList);
    }

    @GetMapping("/get/")
    public String getCodeTemplate(@RequestParam("codeListPageConfigId") Integer codeListPageConfigId, Model model) {
        CodeTemplateConfig codeTemplateConfig = codeTemplateConfigService.getCodeTemplateConfig(codeListPageConfigId);
        model.addAttribute("codeTemplateConfig", codeTemplateConfig);
        return "code-template";
    }

}
