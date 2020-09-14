package org.woodwhales.generator.plugin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.woodwhales.common.response.PageRespVO;
import org.woodwhales.common.response.RespVO;
import org.woodwhales.generator.core.util.GsonUtil;
import org.woodwhales.generator.plugin.controller.request.freemarker.CodeListPageConfigRequestBody;
import org.woodwhales.generator.plugin.controller.request.freemarker.CodeNavigationConfigRequestBody;
import org.woodwhales.generator.plugin.controller.vo.CodeNavigationConfigVO;
import org.woodwhales.generator.plugin.model.CodeTemplateConfigDetail;
import org.woodwhales.generator.plugin.entity.CodeNavigationConfig;
import org.woodwhales.generator.plugin.service.CodeTemplateConfigService;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.9.6 22:53
 * @description:
 */
@Slf4j
@RestController
@RequestMapping("/codeTemplate")
public class CodeTemplateController {

    @Autowired
    private CodeTemplateConfigService codeTemplateConfigService;

    @GetMapping("/page/")
    public PageRespVO<CodeNavigationConfig> page() {
        return codeTemplateConfigService.pageCodeTemplate();
    }

    @PostMapping("/generateMenu")
    public RespVO generateMenu(@Validated @RequestBody CodeNavigationConfigRequestBody requestBody) {
        log.info("requestBody = {}", GsonUtil.toJson(requestBody));
        final Integer codeNavigationConfigId = requestBody.getCodeNavigationConfigId();
        CodeNavigationConfigVO codeNavigationConfigVO = codeTemplateConfigService.getCodeNavigationConfigVOByNavigationConfigById(codeNavigationConfigId);

        boolean success = false;
        return RespVO.resp(success);
    }

    @PostMapping("/generateTable")
    public RespVO generateTable(@Validated @RequestBody CodeListPageConfigRequestBody requestBody) {
        log.info("requestBody = {}", GsonUtil.toJson(requestBody));
        final Integer codeListPageConfigId = requestBody.getCodeListPageConfigId();
        CodeTemplateConfigDetail codeTemplateConfigDetail = codeTemplateConfigService.getCodeTemplateConfigDetailByCodeListPageConfigId(codeListPageConfigId);

        boolean success = false;
        return RespVO.resp(success);
    }
}
