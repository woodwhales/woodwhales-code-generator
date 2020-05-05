package org.woodwhales.generator.entity;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.woodwhales.generator.constant.MyConstant;
import org.woodwhales.generator.controller.request.DataBaseRequestBody;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
	
	public static DataBaseInfo convert(DataBaseRequestBody dataBaseRequestBody) {
		return DataBaseInfo.builder()
							.ip(dataBaseRequestBody.getIp())
							.port(dataBaseRequestBody.getPort())
							.username(dataBaseRequestBody.getUsername())
							.password(dataBaseRequestBody.getPassword())
							.schema(dataBaseRequestBody.getSchema())
							.packageName(dataBaseRequestBody.getPackageName())
							.generateDir(dataBaseRequestBody.getGenerateDir())
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
