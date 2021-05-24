package org.woodwhales.generator.core.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.woodwhales.common.model.vo.PageRespVO;
import org.woodwhales.common.model.vo.RespVO;
import org.woodwhales.generator.core.controller.request.CodeDatabaseConfigQueryParam;
import org.woodwhales.generator.core.controller.request.CodeDatabaseConfigRequestBody;
import org.woodwhales.generator.core.controller.vo.CodeDatabaseConfigVO;
import org.woodwhales.generator.core.service.CodeDatabaseConfigService;

/**
 * @author woodwhales on 2021-05-20 23:19
 * @description
 */
@Slf4j
@RestController()
@RequestMapping("/databaseConfig")
public class CodeDatabaseConfigController {

    @Autowired
    private CodeDatabaseConfigService codeDatabaseConfigService;

    @GetMapping("/page")
    public PageRespVO<CodeDatabaseConfigVO> page(@Validated CodeDatabaseConfigQueryParam param) {
        return codeDatabaseConfigService.page(param);
    }

    @CrossOrigin
    @PostMapping("/create")
    public RespVO<Void> create(@Validated @RequestBody CodeDatabaseConfigRequestBody requestBody) {
        return RespVO.resp(codeDatabaseConfigService.create(requestBody));
    }

}
