package com.ipanel.web.app.cv.global;/**
 * @ClassName
 * @Author lz
 * @Date
 * @Description
 **/

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * @ClassName
 * @Author lz
 * @Date
 * @Description
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {

    private Integer code;

    private String msg;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    private static Response RESULT_SUCCESS = new Response(Constants.ERROR_CODE_00000, Constants.ERROR_CODE_00000_MSG);

    private static Response RESULT_FAILED = new Response(Constants.ERROR_CODE_00001, Constants.ERROR_CODE_00001_MSG);;

    public Response(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public static Response SUCCESS(){
        return RESULT_SUCCESS;
    }

    public static <T> Response<T> SUCCESS(T data){
        return new Response<T>(Constants.ERROR_CODE_00000, Constants.ERROR_CODE_00000_MSG, data);
    }

    public static Response FAILED(){
        return RESULT_FAILED;
    }

    public static Response FAILED(String msg){
        return new Response(Constants.ERROR_CODE_00001, (msg == null || msg.replaceAll(" ", "").equals("")) ? Constants.ERROR_CODE_00001_MSG : msg);
    }

    public static Response FAILED(int code, String msg){
        return new Response(code, msg);
    }

}
