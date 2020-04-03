package com.example.ehcachecache.global.myException;

/**
 * @author zhaolei
 * Create: 2019/11/14 10:42
 * Modified By:
 * Description: 用户异常操作异常类
 */
public class OperationErrorException extends Exception {
    public OperationErrorException(String message) {
        super(message);
    }
}
