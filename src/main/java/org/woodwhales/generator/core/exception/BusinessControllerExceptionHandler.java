package org.woodwhales.generator.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.woodwhales.common.model.vo.RespVO;
import org.woodwhales.generator.view.controller.BusinessViewController;

import javax.validation.UnexpectedTypeException;

/**
 * @author woodwhales
 * @create 2020-10-07 20:18
 */
@Slf4j
@ControllerAdvice(assignableTypes = {
        BusinessViewController.class
})
public class BusinessControllerExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public ModelAndView handle(Exception exception) {
        log.error("cause by : {}", exception.getMessage(), exception);
        RespVO errorResp = RespVO.error(exception.getMessage());
        return returnModelAndView(errorResp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ModelAndView returnModelAndView(RespVO errorResp, HttpStatus httpStatus) {
        ModelAndView modelAndView = new ModelAndView("my-error");
        modelAndView.addObject("error", errorResp);
        modelAndView.addObject("httpStatus", httpStatus.value());
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = GenerateException.class)
    public ModelAndView handle(GenerateException exception) {
        log.error("cause by : {}", exception.getMessage(), exception);
        RespVO errorResp = RespVO.error(exception.getMessage());
        return returnModelAndView(errorResp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = UnexpectedTypeException.class)
    public ModelAndView handle(UnexpectedTypeException exception) {
        log.error("cause by : {}", exception.getMessage(), exception);
        RespVO errorResp = RespVO.error(exception.getMessage());
        return returnModelAndView(errorResp, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = UserRequestException.class)
    public ModelAndView handle(UserRequestException exception) {
        log.error("cause by : {}", exception.getMessage(), exception);
        RespVO errorResp = RespVO.error(exception.getMessage());
        return returnModelAndView(errorResp, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = { MethodArgumentNotValidException.class})
    public ModelAndView handle(MethodArgumentNotValidException exception) {
        log.error("cause by : {}", exception.getMessage(), exception);
        RespVO errorResp = RespVO.error(exception.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return returnModelAndView(errorResp, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BindException.class)
    public ModelAndView handle(BindException exception) {
        log.error("cause by : {}", exception.getMessage(), exception);
        RespVO errorResp = RespVO.error(exception.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return returnModelAndView(errorResp, HttpStatus.BAD_REQUEST);
    }
}
