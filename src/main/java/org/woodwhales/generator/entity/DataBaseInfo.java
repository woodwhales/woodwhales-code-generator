package org.woodwhales.generator.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.woodwhales.generator.constant.MyConstant;
import org.woodwhales.generator.controller.request.BuildConnectionRequestBody;

import java.util.List;
import java.util.Properties;

/**
 * 数据库链接配置数据对象
 * @author woodwhales
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataBaseInfo {

	/**
	 * ip地址
	 */
	private String ip;

	/**
	 * 端口号
	 */
	private Integer port;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 数据库类型
	 */
	private String dbType;

	/**
	 * 数据库驱动全类名
	 */
	private String driveClassName;

	/**
	 * oracle 数据库的sid
	 */
	private String sid;

	/**
	 * 数据库名称
	 */
	private String schema;

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
	private Boolean selectAll;

	/**
	 * 父类
	 */
	private String superClass;

	/**
	 * 接口
	 */
	private List<String> interfaceList;

	/**
	 * 数据库表名
	 */
	private String dbName;

	public static DataBaseInfo convert(BuildConnectionRequestBody requestBody) {
		DataBaseInfo dataBaseInfo = new DataBaseInfo();
		BeanUtils.copyProperties(requestBody, dataBaseInfo);
		return dataBaseInfo;
	}
	
	public Properties getProperties () {
		Properties properties = new Properties();
		properties.put("remarksReporting", "true");
		properties.put("useInformationSchema", "true");
		properties.put("user", this.username);
		properties.put("password", this.password);
		return properties;
	}
	
	public String getUrl() {
		String url = null;

		if(StringUtils.equals(dbType, "MYSQL")) {
			if(StringUtils.isNotBlank(this.schema)) {
				url = MyConstant.mysql_Template_Url
						.replace("[ip]", this.ip)
						.replace("[port]", this.port+"")
						.replace("[schema]", this.schema);
			} else {
				url = MyConstant.mysql_Template_Url_Without_Schema
						.replace("[ip]", this.ip)
						.replace("[port]", this.port+"");
			}
		}

		if(StringUtils.equals(dbType, "ORACLE")) {
			if(StringUtils.isNotBlank(this.sid)) {
				url = MyConstant.oracle_Template_Url
						.replace("[ip]", this.ip)
						.replace("[port]", this.port+"")
						.replace("[sid]", this.sid);
			}
		}

		return url;
	}

	/**
	 * 获取数据库唯一key
	 * @return
	 */
	public String getDataBaseInfoKey() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(this.driveClassName)
				.append(this.ip)
				.append(this.port)
				.append(this.username)
				.append(this.password);
				if(StringUtils.isNotBlank(this.schema)) {
					stringBuilder.append(this.schema);
				}
		return stringBuilder.toString();
	}
	
}
