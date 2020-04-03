package com.ipanel.web.app.cv.global;

import com.ipanel.web.app.cv.global.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
/*

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public Result<?> exceptionHandler(MyException e) {
        log.error("by zhaolei: {}", e.getMessage(), e);
        Result<?> result = new Result<>();
        result.setCode(Result.EXCEPTION);
        result.setMsg(e.toString());
        return result;
    }
}
*/
