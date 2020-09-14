package org.woodwhales.generator.plugin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.woodwhales.common.response.RespVO;
import org.woodwhales.generator.plugin.controller.request.CodeListPageConfigCreateRequestBody;
import org.woodwhales.generator.plugin.service.CodeListPageConfigService;

@Slf4j
@CrossOrigin
@RestController()
@RequestMapping("/plugin/codeListPageConfig")
public class CodeListPageConfigController {

    @Autowired
    private CodeListPageConfigService codeListPageConfigService;

    @PostMapping("/create/")
    public RespVO<Void> createCodeTemplateConfig(@RequestBody CodeListPageConfigCreateRequestBody requestBody) {
        boolean success = codeListPageConfigService.create(requestBody);
        return RespVO.resp(success);
    }

}