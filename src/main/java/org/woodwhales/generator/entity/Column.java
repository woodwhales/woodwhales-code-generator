package org.woodwhales.generator.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Column {
	
	// 原字段名
	private String dbName;
	
	// 格式化之后的字段名
	private String name;
	
	// 原字段类型
	private String dbType;

	// 格式化的字段类型
	private String type;
	
	// 字段注释
	private String comment;
	
	// 是否为主键
	private boolean primaryKey;
}
