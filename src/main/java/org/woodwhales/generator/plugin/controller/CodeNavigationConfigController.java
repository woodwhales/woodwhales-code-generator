package org.woodwhales.generator.plugin.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.woodwhales.common.response.RespVO;
import org.woodwhales.generator.plugin.controller.request.CodeNavigationConfigCreateRequestBody;
import org.woodwhales.generator.plugin.controller.vo.CodeNavigationConfigSimpleInfoVO;
import org.woodwhales.generator.plugin.service.CodeNavigationConfigService;

import java.util.Collections;
import java.util.List;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.9.6 20:55
 * @description:
 */
@Slf4j
@CrossOrigin
@RestController()
@RequestMapping("/plugin/codeNavigationConfig")
public class CodeNavigationConfigController {

    @Autowired
    private CodeNavigationConfigService codeNavigationConfigService;

    @PostMapping("/create/")
    public RespVO<Void> createCodeTemplateConfig(@RequestBody CodeNavigationConfigCreateRequestBody requestBody) {
        boolean success = codeNavigationConfigService.create(requestBody);
        return RespVO.resp(success);
    }

    @GetMapping("/listSimpleInfo/")
    public RespVO<List<CodeNavigationConfigSimpleInfoVO>> listSimpleInfo() {
        List<CodeNavigationConfigSimpleInfoVO> codeNavigationConfigSimpleInfoVOList = codeNavigationConfigService.listSimpleInfo();
        if(CollectionUtils.isEmpty(codeNavigationConfigSimpleInfoVOList)) {
            codeNavigationConfigSimpleInfoVOList = Collections.emptyList();
        }
        return RespVO.success(codeNavigationConfigSimpleInfoVOList);
    }

}
