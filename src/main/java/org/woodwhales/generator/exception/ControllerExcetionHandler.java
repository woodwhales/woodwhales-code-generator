package org.woodwhales.generator.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.woodwhales.generator.controller.response.RespVO;

@RestControllerAdvice
public class ControllerExcetionHandler {

	@ResponseBody
	@ExceptionHandler(value = {Exception.class, Error.class})
	public RespVO handle(Exception exception) {
		return RespVO.error(exception.getMessage());
	}
	
	@ResponseBody
	@ExceptionHandler(value = {GenerateException.class, Error.class})
	public RespVO handle(GenerateException exception) {
		return RespVO.error(exception.getMessage());
	}
}
