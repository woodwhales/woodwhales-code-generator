package org.woodwhales.generator.core.controller.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 生成代码请求体
 * @author woodwhales
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DataBaseRequestBody extends BuildConnectionRequestBody {

	/**
	 * 表格配置
	 */
	private DbTableConfig dbTableConfig;

	/**
	 * 代码生成配置
	 */
	private JavaCodeConfig javaCodeConfig;

	/**
	 * markdown 配置
	 */
	private MarkdownConfig markdownConfig;

}
