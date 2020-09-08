package org.woodwhales.common.response;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author woodwhales
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespVO<T> {

	private Integer code;
	private String msg;
	private T data;

	public static RespVO resp(boolean result) {
		if(result) {
			return success();
		}

		return error();
	}

	public static RespVO success() {
		return new RespVO(0, "success", StringUtils.EMPTY);
	}
	
	public static <T> RespVO<T> success(T data) {
		return new RespVO(0, "success", data);
	}
	
	public static RespVO error() {
		return new RespVO(-1, "error", StringUtils.EMPTY);
	}
	
	public static RespVO error(Integer code, String msg) {
		return new RespVO(code, msg, StringUtils.EMPTY);
	}
	
	public static RespVO error(String msg) {
		return new RespVO(-1, msg, StringUtils.EMPTY);
	}
	
}
