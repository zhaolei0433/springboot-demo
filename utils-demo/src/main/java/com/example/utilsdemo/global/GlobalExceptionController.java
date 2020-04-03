package com.example.utilsdemo.global;

import com.example.utilsdemo.global.constants.ResponseConstants;
import com.example.utilsdemo.global.exception.MethodParamErrorException;
import com.example.utilsdemo.global.exception.OperationErrorException;
import com.example.utilsdemo.global.response.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;



/**
 * 终端请求接口，全局异常处理
 */
@ControllerAdvice
@Log4j2
public class GlobalExceptionController {

    @ExceptionHandler({NullPointerException.class, ArrayIndexOutOfBoundsException.class, ArithmeticException.class})
    @ResponseBody
    public Result handleException500(Exception e){
        log.error("handleException500", e);
        return new Result(ResponseConstants.ERROR_CODE_00500, ResponseConstants.ERROR_CODE_00500_MSG);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleException(Exception e){
        log.error("handleException", e);
        return new Result(ResponseConstants.ERROR_CODE_00001, ResponseConstants.ERROR_CODE_00001_MSG);
    }

    /**
     * 检验请求参数格式
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        log.error("handleException", e);
        return new Result(ResponseConstants.ERROR_CODE_00003, ResponseConstants.ERROR_CODE_00003_MSG+":格式异常!");
    }

    /**
     * 检验请求参数数据格式
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result handleModelArgumentsException(MethodArgumentNotValidException e){
        log.error(e.getMessage());
        if (e.getBindingResult() != null && !CollectionUtils.isEmpty(e.getBindingResult().getAllErrors())) {
            return new Result(ResponseConstants.ERROR_CODE_00003, ResponseConstants.ERROR_CODE_00003_MSG+e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        } else {
            return new Result(ResponseConstants.ERROR_CODE_00003, ResponseConstants.ERROR_CODE_00003_MSG+e.getMessage());
        }
    }

    /**
     * 检验请求参数系统合法性
     * @param e
     * @return
     */
    @ExceptionHandler(RequestParamErrorException.class)
    @ResponseBody
    public Result handleRequestParamErrorException(RequestParamErrorException e){
        log.error(e.getMessage());
        return new Result(ResponseConstants.ERROR_CODE_00003, ResponseConstants.ERROR_CODE_00003_MSG+":"+e.getMessage());

    }

    /**
     * 操作异常
     * @param e
     * @return
     */
    @ExceptionHandler(OperationErrorException.class)
    @ResponseBody
    public Result handleOperationErrorException(OperationErrorException e){
        log.error(e.getMessage());
        return new Result(ResponseConstants.ERROR_CODE_00001, ResponseConstants.ERROR_CODE_00001_MSG+":"+e.getMessage());

    }

    /**
     * 检验方法参数系统合法性
     * @param e
     * @return
     */
    @ExceptionHandler(MethodParamErrorException.class)
    @ResponseBody
    public Result handleMethodParamErrorException(MethodParamErrorException e){
        log.error(e.getMessage());
        return new Result(ResponseConstants.ERROR_CODE_00003, ResponseConstants.ERROR_CODE_00003_MSG+":"+e.getMessage());

    }

    //url参数异常
    @ExceptionHandler({MissingServletRequestParameterException.class, BindException.class})
    @ResponseBody
    public Result handlePathArgumentException2(Exception e){
        log.error("传参为空:"+e.getMessage());
        if(e instanceof BindException){
            return new Result(ResponseConstants.ERROR_CODE_00003, ((BindException) e).getBindingResult().getAllErrors().get(0).getDefaultMessage());
        } else {
            return new Result(ResponseConstants.ERROR_CODE_00011, e.getMessage());
        }
    }
    
    //方法参数异常
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public Result handleMedhodArgumentException(Exception e){
        log.error("传参为空:"+e.getMessage());
        return new Result(ResponseConstants.ERROR_CODE_00011, e.getMessage());
    }

}
