package org.woodwhales.generator.core.controller;

import cn.woodwhales.common.model.result.OpResult;
import cn.woodwhales.common.model.vo.PageRespVO;
import cn.woodwhales.common.model.vo.RespVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.woodwhales.generator.core.controller.request.BuildConnectionRequestBody;
import org.woodwhales.generator.core.controller.request.CodeDatabaseConfigQueryParam;
import org.woodwhales.generator.core.controller.request.dbconfig.CodeDatabaseConfigGetRequestBody;
import org.woodwhales.generator.core.controller.request.dbconfig.CodeDatabaseConfigRequestBody;
import org.woodwhales.generator.core.controller.vo.CodeDatabaseConfigVO;
import org.woodwhales.generator.core.entity.DataBaseInfo;
import org.woodwhales.generator.core.service.CodeDatabaseConfigService;
import org.woodwhales.generator.core.service.GenerateService;

import javax.validation.Valid;
import java.sql.Connection;

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

    @Autowired
    private GenerateService generateService;

    @GetMapping("/page")
    public PageRespVO<CodeDatabaseConfigVO> page(@Validated CodeDatabaseConfigQueryParam param) {
        return codeDatabaseConfigService.page(param);
    }

    @CrossOrigin
    @PostMapping("/create")
    public RespVO<Void> create(@Validated @RequestBody CodeDatabaseConfigRequestBody requestBody) {
        return RespVO.resp(codeDatabaseConfigService.create(requestBody));
    }

    @CrossOrigin
    @PostMapping("/testConnection")
    public RespVO testConnection(@Valid @RequestBody BuildConnectionRequestBody requestBody) throws Exception {
        log.info("[testConnection].buildConnectionRequestBody = {}", requestBody);
        DataBaseInfo dataBaseInfo = DataBaseInfo.convert(requestBody);
        OpResult<Connection> opResult = generateService.getConnection(dataBaseInfo);
        if (opResult.isSuccessful()) {
            return RespVO.success();
        } else {
            return RespVO.error();
        }
    }

    @CrossOrigin
    @PostMapping("/get")
    public RespVO<CodeDatabaseConfigVO> get(@Validated @RequestBody CodeDatabaseConfigGetRequestBody requestBody) {
        return RespVO.resp(codeDatabaseConfigService.get(requestBody));
    }

}
