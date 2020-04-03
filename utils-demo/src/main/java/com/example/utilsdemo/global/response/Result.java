package com.example.utilsdemo.global.response;

import java.io.Serializable;

/**
 * @author zhaolei
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final int SUCCESS = 0;
    public static final int FAIL = 1;
    public static final int EXCEPTION = 500;
    private String msg = "success";
    private int code = SUCCESS;
    private T data;

    public Result() {
        super();
    }

    public Result(T data, String msg, int code) {
        super();
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public Result(T data) {
        super();
        this.data = data;
        if (data instanceof Boolean) {
            if (!(Boolean) data) {
                this.code = FAIL;
                this.msg = "failed";
            }
        }
    }

    public Result(Throwable e) {
        super();
        this.msg = e.toString();
        this.code = EXCEPTION;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
