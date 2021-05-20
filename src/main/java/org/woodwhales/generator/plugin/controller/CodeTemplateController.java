package org.woodwhales.generator.plugin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.woodwhales.common.model.vo.PageRespVO;
import org.woodwhales.common.model.vo.RespVO;
import org.woodwhales.generator.core.util.GsonUtil;
import org.woodwhales.generator.plugin.controller.request.freemarker.CodeListPageConfigRequestBody;
import org.woodwhales.generator.plugin.controller.request.freemarker.CodeNavigationConfigRequestBody;
import org.woodwhales.generator.plugin.controller.vo.CodeNavigationConfigVO;
import org.woodwhales.generator.plugin.entity.CodeNavigationConfig;
import org.woodwhales.generator.plugin.model.CodeTemplateConfigDetail;
import org.woodwhales.generator.plugin.service.CodeTemplateConfigService;
import org.woodwhales.generator.plugin.service.CodeTemplateFreeMarkerService;

/**
 * 项目名称：woodwhales-code-generator
 * @author woodwhales
 * @date 2020-09-06 22:53:00
 */
@Slf4j
@RestController
@RequestMapping("/codeTemplate")
public class CodeTemplateController {

    @Autowired
    private CodeTemplateConfigService codeTemplateConfigService;

    @Autowired
    @Qualifier("codeListPageFreeMarkerService")
    private CodeTemplateFreeMarkerService codeListPageFreeMarkerService;

    @Autowired
    @Qualifier("codeNavigationFreeMarkerService")
    private CodeTemplateFreeMarkerService codeNavigationFreeMarkerService;

    @GetMapping("/page/")
    public PageRespVO<CodeNavigationConfig> page() {
        return codeTemplateConfigService.listCodeNavigationConfig();
    }

    /**
     * 生成模板
     * @param requestBody 菜单请求体
     * @return 响应体
     * @throws Exception 异常
     */
    @PostMapping("/generateMenu")
    public RespVO generateMenu(@Validated @RequestBody CodeNavigationConfigRequestBody requestBody) throws Exception {
        log.info("requestBody = {}", GsonUtil.toJson(requestBody));
        final Integer codeNavigationConfigId = requestBody.getCodeNavigationConfigId();
        CodeNavigationConfigVO codeNavigationConfigVO = codeTemplateConfigService.getCodeNavigationConfigVOByNavigationConfigById(codeNavigationConfigId);
        CodeTemplateConfigDetail codeTemplateConfigDetail = new CodeTemplateConfigDetail(codeNavigationConfigVO, null);
        log.info("codeTemplateConfigDetail => {}", GsonUtil.toJson(codeTemplateConfigDetail));
        boolean success = codeNavigationFreeMarkerService.process(codeTemplateConfigDetail);
        if (success) {
            return RespVO.success();
        } else {
            return RespVO.error();
        }
    }

    /**
     * 生成模板
     * @param requestBody 列表请求体
     * @return 响应体
     * @throws Exception 异常
     */
    @PostMapping("/generateTable")
    public RespVO generateTable(@Validated @RequestBody CodeListPageConfigRequestBody requestBody) throws Exception {
        log.info("requestBody = {}", GsonUtil.toJson(requestBody));
        final Integer codeListPageConfigId = requestBody.getCodeListPageConfigId();
        CodeTemplateConfigDetail codeTemplateConfigDetail = codeTemplateConfigService.getCodeTemplateConfigDetailByCodeListPageConfigId(codeListPageConfigId);
        log.info("codeTemplateConfigDetail => {}", GsonUtil.toJson(codeTemplateConfigDetail));
        boolean success = codeListPageFreeMarkerService.process(codeTemplateConfigDetail);
        if (success) {
            return RespVO.success();
        } else {
            return RespVO.error();
        }
    }
}
