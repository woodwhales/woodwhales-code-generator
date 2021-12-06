package org.woodwhales.generator.core.entity;

import com.google.common.base.Preconditions;
import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.woodwhales.generator.core.constant.MyConstant;

import java.util.List;

/**
 * @author woodwhales
 */
@Data
public class TableInfo {

	/**
	 * 表主键
	 */
	private String tableKey;

	/**
	 * 原表名
	 */
	private String dbName;

	/**
	 * 处理后的表名
	 */
	private String name;

	/**
	 * 处理后的表名首字母小写
	 * 用于javaBean的属性名
	 */
	private String propertyName;

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

	/**
	 * 建表语句
	 */
	private String createTableSql;

	public TableInfo(String dbName, String dataBaseInfoKey) {
		Preconditions.checkArgument(StringUtils.isNotBlank(dbName), "数据库表名不允许为空");
		Preconditions.checkArgument(StringUtils.isNotBlank(dataBaseInfoKey), "数据库连接信息缓存key不允许为空");
		this.dbName = dbName;
		this.tableKey = DigestUtils.sha256Hex(dataBaseInfoKey + MyConstant.hexTxt + this.dbName);
	}

}
