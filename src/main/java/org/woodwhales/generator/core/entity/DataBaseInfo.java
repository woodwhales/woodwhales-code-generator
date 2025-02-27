package org.woodwhales.generator.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.woodwhales.generator.core.constant.MyConstant;
import org.woodwhales.generator.core.controller.request.BuildConnectionRequestBody;
import org.woodwhales.generator.core.controller.request.DbTableConfig;
import org.woodwhales.generator.core.controller.request.JavaCodeConfig;
import org.woodwhales.generator.core.controller.request.MarkdownConfig;

import java.util.Date;
import java.util.Properties;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;

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
	private String driverClassName;

	/**
	 * oracle 数据库的sid
	 */
	private String sid;

	/**
	 * 数据库名称
	 */
	private String schema;

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

	/**
	 * 当前时间
	 */
	private String now;

	public static DataBaseInfo convert(BuildConnectionRequestBody requestBody) {
		DataBaseInfo dataBaseInfo = new DataBaseInfo();
		BeanUtils.copyProperties(requestBody, dataBaseInfo);
		dataBaseInfo.setUsername(trimToEmpty(requestBody.getUsername()));
		dataBaseInfo.setPassword(trimToEmpty(dataBaseInfo.getPassword()));
		dataBaseInfo.setNow(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
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
			if(isNotBlank(this.schema)) {
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
			if(isNotBlank(this.sid)) {
				url = MyConstant.oracle_Template_Url
						.replace("[ip]", this.ip)
						.replace("[port]", this.port+"")
						.replace("[sid]", this.sid);
			}
		}

		if(StringUtils.equals(dbType, "PGSQL")) {
			if(isNotBlank(this.schema)) {
				url = MyConstant.pgsql_Template_Url
						.replace("[ip]", this.ip)
						.replace("[port]", this.port+"")
						.replace("[schema]", this.schema);
			} else {
				url = MyConstant.pgsql_Template_Url_Without_Schema
						.replace("[ip]", this.ip)
						.replace("[port]", this.port+"");
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
		stringBuilder.append(this.driverClassName)
				.append(this.ip)
				.append(this.port)
				.append(this.username)
				.append(this.password);
				if(isNotBlank(this.schema)) {
					stringBuilder.append(this.schema);
				}
		return stringBuilder.toString();
	}

	public String getAuthor() {
		return StringUtils.trimToNull(this.javaCodeConfig.getAuthor());
	}
}
