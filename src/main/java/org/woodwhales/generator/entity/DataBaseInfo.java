package org.woodwhales.generator.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.woodwhales.generator.constant.MyConstant;
import org.woodwhales.generator.controller.request.BuildConnectionRequestBody;
import org.woodwhales.generator.controller.request.DataBaseRequestBody;

import java.util.List;
import java.util.Properties;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataBaseInfo {
	
	private String ip; 
	
	private Integer port; 
	
	private String username; 
	
	private String password;
	
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
	 * 要生成的数据库表名列表
	 */
	private List<String> dbNameList;

	/**
	 * 是否生成全部数据库表
	 */
	private Boolean selectAll;

	public static DataBaseInfo convert(BuildConnectionRequestBody requestBody) {
		return DataBaseInfo.builder()
				.ip(requestBody.getIp())
				.port(requestBody.getPort())
				.username(requestBody.getUsername())
				.password(requestBody.getPassword())
				.build();
	}
	
	public static DataBaseInfo convert(DataBaseRequestBody requestBody) {
		return DataBaseInfo.builder()
							.ip(requestBody.getIp())
							.port(requestBody.getPort())
							.username(requestBody.getUsername())
							.password(requestBody.getPassword())
							.schema(requestBody.getSchema())
							.packageName(requestBody.getPackageName())
							.generateDir(requestBody.getGenerateDir())
							.dbNameList(requestBody.getDbNameList())
							.selectAll(requestBody.getSelectAll())
							.build();
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
		if(StringUtils.isNotBlank(this.schema)) {
			url = MyConstant.templateUrl
							.replace("[ip]", this.ip)
							.replace("[port]", this.port+"")
							.replace("[schema]", this.schema);
		} else {
			url = MyConstant.templateUrlWithoutSchema
							.replace("[ip]", this.ip)
							.replace("[port]", this.port+"");
		}
		return url;
	}
	
}
