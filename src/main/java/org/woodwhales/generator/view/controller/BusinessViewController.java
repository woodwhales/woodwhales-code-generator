package org.woodwhales.generator.view.controller;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;
import org.woodwhales.generator.core.exception.UserRequestException;
import org.woodwhales.generator.plugin.controller.vo.CodeListPageConfigVO;
import org.woodwhales.generator.plugin.controller.vo.CodeNavigationConfigVO;
import org.woodwhales.generator.plugin.model.CodeTemplateConfig;
import org.woodwhales.generator.plugin.model.CodeTemplateConfigStatistics;
import org.woodwhales.generator.plugin.service.CodeTemplateConfigService;
import org.woodwhales.generator.view.controller.request.CustomViewCodeListRequestParam;
import org.woodwhales.generator.view.controller.request.CustomViewCodeNavigationRequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author woodwhales
 * @create 2020-10-07 13:39
 */
@Controller
@RequestMapping("custom")
public class BusinessViewController {

    @Autowired
    private CodeTemplateConfigService codeTemplateConfigService;

    @GetMapping({"/", "/index"})
    public String index(Model model, @Valid CustomViewCodeNavigationRequestParam requestParam) {
        Integer codeNavigationConfigId = requestParam.getMenuId();
        CodeNavigationConfigVO codeNavigationConfigVO = codeTemplateConfigService.getCodeNavigationConfigVOByNavigationConfigById(codeNavigationConfigId);
        model.addAttribute("navigation", codeNavigationConfigVO);
        model.addAttribute("codeNavigationConfigId", codeNavigationConfigId);
        return "custom/index";
    }

    @GetMapping("/welcome")
    public String welcome(Model model) {
        CodeTemplateConfigStatistics codeTemplateConfigStatistics = codeTemplateConfigService.statistics();
        model.addAttribute("codeNavigationConfigTotal", codeTemplateConfigStatistics.getCodeNavigationConfigTotal());
        model.addAttribute("codeListPageConfigTotal", codeTemplateConfigStatistics.getCodeListPageConfigTotal());
        return "custom/welcome";
    }

    @GetMapping("/list")
    public String list(Model model, @Valid CustomViewCodeListRequestParam requestParam) {
        String dbTableName = requestParam.getDbTableName();
        Integer codeNavigationConfigId = requestParam.getCodeNavigationConfigId();

        CodeTemplateConfig codeTemplateConfig = codeTemplateConfigService.getCodeTemplateByCodeNavigationConfigId(codeNavigationConfigId);
        List<CodeListPageConfigVO> codeListPageConfigVOList = codeTemplateConfig.getCodeListPageConfigList();
        List<CodeListPageConfigVO> resultList = codeListPageConfigVOList
                .stream()
                .filter(codeListPageConfigVOItem -> StringUtils.equals(dbTableName, codeListPageConfigVOItem.getListPageConfigs().getDbTableName()))
                .collect(Collectors.toList());

        if(CollectionUtils.isEmpty(resultList)) {
            throw new UserRequestException("未关联列表配置");
        }

        CodeListPageConfigVO codeListPageConfigVO = resultList.get(0);

        model.addAttribute("navName", codeListPageConfigVO.getListPageConfigs().getNavName());
        model.addAttribute("searchInputs", codeListPageConfigVO.getListPageConfigs().getSearchInputs());
        model.addAttribute("tableConfig", codeListPageConfigVO.getListPageConfigs().getTableConfig());
        return "custom/list";
    }

}
