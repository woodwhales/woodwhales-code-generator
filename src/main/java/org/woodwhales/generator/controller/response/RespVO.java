package org.woodwhales.generator.controller.response;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespVO {

	private String code;
	private String msg;
	private Object data;
	
	public static RespVO success() {
		return new RespVO("0000", "success", StringUtils.EMPTY);
	}
	
	public static RespVO success(Object data) {
		return new RespVO("0000", "success", data);
	}
	
	public static RespVO error() {
		return new RespVO("-1", "error", StringUtils.EMPTY);
	}
	
	public static RespVO error(String code, String msg) {
		return new RespVO(code, msg, StringUtils.EMPTY);
	}
	
	public static RespVO error(String msg) {
		return new RespVO("-1", msg, StringUtils.EMPTY);
	}
	
}
