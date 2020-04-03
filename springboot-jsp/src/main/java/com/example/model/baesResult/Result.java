package com.example.model.baesResult;

import com.example.global.constants.ResponseConstants;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhaolei
 * Create: 2019/11/14 10:47
 * Modified By:
 * Description: 基本而定返回pojo类
 */
@Data
public class Result<T> implements Serializable {

    private String msg = ResponseConstants.SUCCESS_CODE_00000_MSG;
    private int code = ResponseConstants.SUCCESS_CODE_00000;
    private T data;

    public Result() {
        super();
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
                this.code = ResponseConstants.ERROR_CODE_00001;
                this.msg = ResponseConstants.ERROR_CODE_00001_MSG;
            }
        }
    }

    public Boolean isSuccess(){
        return this.code == ResponseConstants.SUCCESS_CODE_00000;
    }
}
