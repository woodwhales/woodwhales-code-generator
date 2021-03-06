package org.woodwhales.generator.core.exception;

import freemarker.core.InvalidReferenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.woodwhales.common.model.vo.RespVO;

import javax.validation.UnexpectedTypeException;

/**
 * @author woodwhales
 */
@Slf4j
@RestControllerAdvice(basePackages = "org.woodwhales.generator.core.controller")
public class ControllerExceptionHandler {

	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public RespVO handle(Exception exception) {
		log.error("cause by : {}", exception.getMessage(), exception);
		return RespVO.errorWithErrorMsg(exception.getMessage());
	}
	
	@ResponseBody
	@ExceptionHandler(value = GenerateException.class)
	public RespVO handle(GenerateException exception) {
		log.error("cause by : {}", exception.getMessage(), exception);
		return RespVO.errorWithErrorMsg(exception.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(value = UnexpectedTypeException.class)
	public RespVO handle(UnexpectedTypeException exception) {
		log.error("cause by : {}", exception.getMessage(), exception);
		return RespVO.errorWithErrorMsg(exception.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(value = { MethodArgumentNotValidException.class, BindException.class })
	public RespVO handle(MethodArgumentNotValidException exception) {
		log.error("cause by : {}", exception.getMessage(), exception);
		return RespVO.errorWithErrorMsg(exception.getBindingResult().getAllErrors().get(0).getDefaultMessage());
	}

	@ResponseBody
	@ExceptionHandler(value = InvalidReferenceException.class)
	public RespVO handle(InvalidReferenceException exception) {
		log.error("cause by : {}", exception.getMessage(), exception);
		return RespVO.errorWithErrorMsg("模板解析失败");
	}
}
