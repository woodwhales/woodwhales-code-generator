package org.woodwhales.generator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.NotBlank;

@Controller
public class ViewController {

	@GetMapping({"/", "/index"})
	public String index() {
		return "index";
	}

	@GetMapping("/detail")
	public String detail(@NotBlank(message = "数据表主键不允许为空") String tableKey, Model model) {
		model.addAttribute("tableKeyId", tableKey);
		return "detail";
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
		return "tips";
	}
}
