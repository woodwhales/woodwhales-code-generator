package org.woodwhales.generator.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woodwhales.generator.plugin.controller.vo.CodeNavigationConfigVO;
import org.woodwhales.generator.plugin.service.CodeTemplateConfigService;
import org.woodwhales.generator.view.controller.request.CustomViewRequestParam;

import javax.validation.Valid;

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
    public String index(Model model, @Valid CustomViewRequestParam requestParam) {
        Integer menuId = requestParam.getMenuId();
        CodeNavigationConfigVO codeNavigationConfigVO = codeTemplateConfigService.getCodeNavigationConfigVOByNavigationConfigById(menuId);
        model.addAttribute("navigation", codeNavigationConfigVO);
        return "custom/index";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "custom/welcome";
    }

    @GetMapping("/list")
    public String list() {
        return "custom/list";
    }

}
