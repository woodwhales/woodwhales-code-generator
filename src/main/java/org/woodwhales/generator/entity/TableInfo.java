package org.woodwhales.generator.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableInfo {

	/**
	 * 原表名
	 */
	private String dbName;

	/**
	 * 处理后的表名
	 */
	private String name;

	/**
	 * 注释
	 */
	private String comment;

	/**
	 * 主键字段名
	 */
	private List<String> keys;

	/**
	 * 主键字段类型
	 */
	private List<String> keyTypes;

	/**
	 * 字段集合
	 */
	private List<Column> columns;
	
}
