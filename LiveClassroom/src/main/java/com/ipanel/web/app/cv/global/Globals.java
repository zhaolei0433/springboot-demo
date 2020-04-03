package com.ipanel.web.app.cv.global;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author zhaolei
 * createTime 2018年9月17日 下午4:16:44
 */
public class Globals {

    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }

    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static Throwable getOriginException(Throwable e) {
        while (e.getCause() != null) {
            e = e.getCause();
        }
        return e;
    }

    /**
     * 检查接口输入
     *
     * @param object req类
     * @throws RequestParamErrorException e
     * @throws IllegalAccessException     e
     */
    public static void checkParams(Object object) throws RequestParamErrorException, IllegalAccessException {
      /*  MyInterfaceRequestParams myInterfaceRequestParams = object.getClass().getAnnotation(MyInterfaceRequestParams.class);
        if (null == myInterfaceRequestParams) {
            return;
        }*/
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            ApiModelProperty modelProperty = field.getAnnotation(ApiModelProperty.class);
            if (null == modelProperty) {
                continue;
            }
            if (modelProperty.required()) {
                if (isEmpty(field.get(object))
                        || (field.get(object) instanceof String && isBlank(field.get(object).toString()))
                        || (field.getType().isAssignableFrom(List.class) && ((List) field.get(object)).isEmpty())) {
                    throw new RequestParamErrorException(modelProperty.value() + "不能为空");
                }
            }
            /*// 如果字段不为空，且该字段类型为class并且有RequestParams注解，对该字段进行判断
            if (null != field.get(object)) {
                Class<?> clazz = field.get(object).getClass();
                MyInterfaceRequestParams myInterfaceRequestParams1 = clazz.getAnnotation(MyInterfaceRequestParams.class);
                if (null != myInterfaceRequestParams1) {
                    checkParams(field.get(object));
                }
            }*/
            Type genericType = field.getGenericType();
            // 如果是泛型参数的类型
            if (genericType instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) genericType;
                // 先取泛型参数的类型，判断是否有ApiModel注解
                Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
                ApiModel apiModel1 = genericClazz.getAnnotation(ApiModel.class);
                if (null != apiModel1) {
                    // 如果是list，遍历判断每个对象的值是否满足要求
                    if (field.getType().isAssignableFrom(List.class)) {
                        List list = (List) field.get(object);
                        for (Object obj : list) {
                            checkParams(obj);
                        }
                    }
                }
            }
        }
    }
}