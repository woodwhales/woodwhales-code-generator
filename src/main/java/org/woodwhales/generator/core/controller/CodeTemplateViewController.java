package org.woodwhales.generator.core.controller;

import com.google.common.base.Preconditions;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.woodwhales.generator.core.entity.TableInfo;
import org.woodwhales.generator.core.service.GenerateService;
import org.woodwhales.generator.plugin.controller.vo.CodeNavigationConfigSimpleInfoVO;
import org.woodwhales.generator.plugin.model.CodeTemplateConfig;
import org.woodwhales.generator.plugin.service.CodeNavigationConfigService;
import org.woodwhales.generator.plugin.service.CodeTemplateConfigService;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

/**
 * @author woodwhales
 */
@Controller
public class CodeTemplateViewController {

	@Autowired
	private GenerateService generateService;

	@Autowired
	private CodeNavigationConfigService codeNavigationConfigService;

	@Autowired
	private CodeTemplateConfigService codeTemplateConfigService;

	@GetMapping({"/", "/index"})
	public String index() {
		return "code-generator/index";
	}

	@GetMapping("/detail")
	public String detail(@NotBlank(message = "数据表主键不允许为空") String tableKey, Model model) {
		model.addAttribute("tableKeyId", tableKey);
		return "code-generator/detail";
	}

	@GetMapping("/tips")
	public String tips(Model model) {
		model.addAttribute("tips", "columns \t字段列表\n" +
				"dbName\t\t\t数据库字段名\n" +
				"name\t\t\t格式化之后的字段名\n" +
				"dbType\t\t\t格式化之后的字段名\n" +
				"type \t\t\t格式化的字段类型\n" +
				"comment\t\t\t字段注释\n" +
				"columnSize\t\t字段大小\n" +
				"nullAble\t\t是否允许为NULL\n" +
				"nullableString\tnullAble 的字符串形式\n" +
				"defaultValue\t默认值\n" +
				"primaryKey\t\t是否为主键");
		return "code-generator/tips";
	}

	@GetMapping("/addListPageConfig")
	public String addConfig(@RequestParam("tableKey") String tableKey, Model model) {
		Preconditions.checkArgument(StringUtils.isNotBlank(tableKey), "表主键不允许为空");
		TableInfo tableInfo = generateService.getTableInfo(tableKey);
		if(Objects.isNull(tableInfo)) {
			return "redirect:/";
		}

		model.addAttribute("tableKey", tableKey);
		model.addAttribute("tableDbName", tableInfo.getDbName());
		model.addAttribute("tablePrimaryKeys", tableInfo.getKeys());
		model.addAttribute("columns", tableInfo.getColumns());
		model.addAttribute("tableName", tableInfo.getPropertyName());
		model.addAttribute("tableComment", tableInfo.getComment());
		model.addAttribute("tableUrl", "/" + tableInfo.getDbName() + "/" + tableInfo.getPropertyName() + "/");

		List<CodeNavigationConfigSimpleInfoVO> codeNavigationConfigSimpleInfoVOList = codeNavigationConfigService.listSimpleInfo();
		model.addAttribute("codeNavigationConfigs", codeNavigationConfigSimpleInfoVOList);
		return "code-generator/add-list-page-config";
	}

	@GetMapping("/addNavigationConfig")
	public String addNavigationConfig(@RequestParam("dataBaseInfoKey") String encryptedDataBaseInfoKey, Model model) {
		List<TableInfo> tableInfos = generateService.listTables(encryptedDataBaseInfoKey);
		if(CollectionUtils.isEmpty(tableInfos)) {
			return "redirect:/";
		}
		model.addAttribute("dataBaseInfoKey", encryptedDataBaseInfoKey);
		return "code-generator/add-navigation-config";
	}

	@GetMapping("/codeTemplate/")
	public String codeTemplate() {
		return "code-generator/code-template";
	}

	@GetMapping("/codeTemplate/detail/")
	public String codeTemplateDetail(@RequestParam("id") Integer codeListPageConfigId, Model model) {
		CodeTemplateConfig codeTemplateConfig = codeTemplateConfigService.getCodeTemplateByCodeListPageConfigId(codeListPageConfigId);
		model.addAttribute("codeTemplateConfig", codeTemplateConfig);
		return "code-generator/code-template-detail";
	}
}
