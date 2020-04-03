package com.ipanel.web.app.cv.annotation;

import java.lang.annotation.*;

/**
 * @author zhaolei
 * Create: 2018/12/28 16:13
 * Modified By:
 * Description:
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
    /**
     * 要执行的操作类型比如：add操作
     **/
    public String operationType() default "";

    /**
     * 要执行的具体操作内容比如：添加用户
     **/
    public String operationName() default "";
}
