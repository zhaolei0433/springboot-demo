package com.ipanel.web.app.cv.global;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 终端请求接口，全局异常处理*/


@ControllerAdvice
@Log4j2
public class GlobalExceptionController {

    @ExceptionHandler({NullPointerException.class, ArrayIndexOutOfBoundsException.class, ArithmeticException.class})
    @ResponseBody
    public Response handleException500(Exception e){
        log.error("handleException500", e);
        return Response.FAILED(Constants.RETURN_CODE_00500, Constants.RETURN_CODE_00500_MSG);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response handleException(Exception e){
        log.error("handleException", e);
        return Response.FAILED(Constants.ERROR_CODE_00001, Constants.ERROR_CODE_00001_MSG);
    }

    /**
     * 检验请求参数格式
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public Response handleHttpMessageNotReadableException(Exception e){
        log.error("handleException", e);
        return Response.FAILED(Constants.ERROR_CODE_00001, Constants.ERROR_CODE_00001_MSG+"：请求参格式异常!");
    }

    /**
     * 检验请求参数数据格式
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Response handleModelArgumentsException(MethodArgumentNotValidException e){
        log.error("Bean 校验异常"+e.getMessage());
        if (e.getBindingResult() != null && !CollectionUtils.isEmpty(e.getBindingResult().getAllErrors())) {
            return Response.FAILED(Constants.RETURN_CODE_00003, Constants.RETURN_CODE_00003_MSG+e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        } else {
            return Response.FAILED(Constants.RETURN_CODE_00003, Constants.RETURN_CODE_00003_MSG+Constants.RETURN_CODE_00003_MSG+e.getMessage());
        }
    }

    //url参数异常
    @ExceptionHandler({MissingServletRequestParameterException.class, BindException.class})
    @ResponseBody
    public Response handlePathArgumentException2(Exception e){
        log.error("传参为空:"+e.getMessage());
        if(e instanceof  BindException){
            return Response.FAILED(Constants.RETURN_CODE_00003, ((BindException) e).getBindingResult().getAllErrors().get(0).getDefaultMessage());
        } else {
            return Response.FAILED(Constants.RETURN_CODE_00011, e.getMessage());
        }
    }

}
