package org.woodwhales.generator.controller.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataBaseRequestBody {

	@NotBlank(message = "ip字段为空")
	private String ip; 
	
	@NotBlank(message = "port字段为空")
	private Integer port; 
	
	@NotBlank(message = "username字段为空")
	private String username; 
	
	@NotBlank(message = "password字段为空")
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

}
