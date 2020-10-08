package org.woodwhales.generator.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woodwhales.generator.plugin.controller.vo.CodeListPageConfigVO;
import org.woodwhales.generator.plugin.controller.vo.CodeNavigationConfigVO;
import org.woodwhales.generator.plugin.service.CodeTemplateConfigService;
import org.woodwhales.generator.view.controller.request.CustomViewCodeListRequestParam;
import org.woodwhales.generator.view.controller.request.CustomViewCodeNavigationRequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author woodwhales
 * @create 2020-10-07 13:39
 */
@Controller
@RequestMapping("custom")
public class CustomViewController {

    @Autowired
    private CodeTemplateConfigService codeTemplateConfigService;

    @GetMapping({"/", "/index"})
    public String index(Model model, @Valid CustomViewCodeNavigationRequestParam requestParam) {
        Integer codeNavigationConfigId = requestParam.getMenuId();
        CodeNavigationConfigVO codeNavigationConfigVO = codeTemplateConfigService.getCodeNavigationConfigVOByNavigationConfigById(codeNavigationConfigId);
        model.addAttribute("navigation", codeNavigationConfigVO);
        return "custom/index";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "custom/welcome";
    }

    @GetMapping("/list")
    public String list(Model model, @Valid CustomViewCodeListRequestParam requestParam) {
        Integer codeNavigationConfigId = requestParam.getMenuId();
        Integer codeListPageConfigId = requestParam.getCodeListPageConfigId();

        List<CodeListPageConfigVO> codeListPageConfigVOList = codeTemplateConfigService.getCodeListPageConfigVOListByCodeNavigationConfigId(codeNavigationConfigId);
        CodeListPageConfigVO codeListPageConfig = codeListPageConfigVOList.stream()
                .filter(codeListPageConfigVO -> Objects.equals(codeListPageConfigId, codeListPageConfigVO.getId()))
                .collect(Collectors.toList()).get(0);
        model.addAttribute("codeListPageConfig", codeListPageConfig);
        model.addAttribute("navName", codeListPageConfig.getListPageConfigs().getNavName());
        model.addAttribute("searchInputs", codeListPageConfig.getListPageConfigs().getSearchInputs());
        model.addAttribute("tableConfig", codeListPageConfig.getListPageConfigs().getTableConfig());
        return "custom/list";
    }

}
