package com.example.global;

import com.example.global.constants.ResponseConstants;
import com.example.global.myException.OperationErrorException;
import com.example.model.baesResult.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class GlobalExceptionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionController.class);

    /**
     * 服务器内部错误异常返回捕获并返回
     *
     * @param e
     * @return
     */
    @ExceptionHandler({NullPointerException.class, ArrayIndexOutOfBoundsException.class, ArithmeticException.class})
    @ResponseBody
    public Result handleException500(Exception e) {
        LOGGER.error("handleException500", e);
        return new Result(ResponseConstants.ERROR_CODE_00500, ResponseConstants.ERROR_CODE_00500_MSG);
    }

    /**
     * 检验请求参数格式
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        LOGGER.error("handleException", e);
        return new Result(ResponseConstants.ERROR_CODE_00003, ResponseConstants.ERROR_CODE_00003_MSG + ":格式异常!");
    }

    /**
     * 检验请求参数数据格式
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result handleModelArgumentsException(MethodArgumentNotValidException e) {
        LOGGER.error(e.getMessage());
        if (e.getBindingResult() != null && !CollectionUtils.isEmpty(e.getBindingResult().getAllErrors())) {
            return new Result(ResponseConstants.ERROR_CODE_00003, ResponseConstants.ERROR_CODE_00003_MSG + e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        } else {
            return new Result(ResponseConstants.ERROR_CODE_00003, ResponseConstants.ERROR_CODE_00003_MSG + e.getMessage());
        }
    }

    /**
     * 操作异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(OperationErrorException.class)
    @ResponseBody
    public Result handleOperationErrorException(OperationErrorException e) {
        LOGGER.error(e.getMessage());
        return new Result(ResponseConstants.ERROR_CODE_00001, ResponseConstants.ERROR_CODE_00001_MSG + ":" + e.getMessage());

    }


    //url参数异常
    @ExceptionHandler({MissingServletRequestParameterException.class, BindException.class})
    @ResponseBody
    public Result handlePathArgumentException2(Exception e) {
        LOGGER.error("传参为空:" + e.getMessage());
        if (e instanceof BindException) {
            return new Result(ResponseConstants.ERROR_CODE_00003, ((BindException) e).getBindingResult().getAllErrors().get(0).getDefaultMessage());
        } else {
            return new Result(ResponseConstants.ERROR_CODE_00011, e.getMessage());
        }
    }

    //方法参数异常
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public Result handleMedhodArgumentException(Exception e) {
        LOGGER.error("传参为空:" + e.getMessage());
        return new Result(ResponseConstants.ERROR_CODE_00011, e.getMessage());
    }

}
