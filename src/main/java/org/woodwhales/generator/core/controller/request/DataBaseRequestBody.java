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
	 * 包名称
	 */
	private String packageName;
	
	/**
	 * 生成代码的目录
	 */
	private String generateDir;

	/**
	 * 是否生成数据库表结构设计文档
	 */
	private Boolean generateMarkdown;

	/**
	 * 是否覆盖markdown
	 */
	private Boolean overMarkdown;

	/**
	 * 是否生成代码
	 */
	private Boolean generateCode;

	/**
	 * 是否生成controller
	 */
	private Boolean generateController;

	/**
	 * 是否生成service
	 */
	private Boolean generateService;

	/**
	 * 是否生成BatchMapper
	 */
	private Boolean generateBatchMapper;

	/**
	 * 是否覆盖markdown
	 */
	private Boolean overCode;

	/**
	 * 要生成的数据库表名列表
	 */
	private List<String> dbNameList;

	/**
	 * 是否生成全部数据库表
	 */
	private boolean selectAll = false;

	/**
	 * 父类
	 */
	private String superClass;

	/**
	 * 接口
	 */
	private List<String> interfaceList;

	/**
	 * 作者名称
	 */
	private String author;

	/**
	 * orm框架
	 */
	private String orm;

}
