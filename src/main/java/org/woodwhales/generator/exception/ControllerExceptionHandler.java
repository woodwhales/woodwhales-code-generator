package org.woodwhales.generator.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.woodwhales.generator.controller.response.RespVO;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public RespVO handle(Exception exception) {
		log.error("cause by : {}", exception.getMessage(), exception);
		return RespVO.error(exception.getMessage());
	}
	
	@ResponseBody
	@ExceptionHandler(value = GenerateException.class)
	public RespVO handle(GenerateException exception) {
		log.error("cause by : {}", exception.getMessage(), exception);
		return RespVO.error(exception.getMessage());
	}
}
