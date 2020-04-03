package com.example.global.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaolei
 * Create: 2019/11/14 11:57
 * Modified By:
 * Description:
 */
public class SystemDefines {
    /**
     * 日志注解静态变量
     */
    public static final String SYSLOG_ADD = "添加";
    public static final String SYSLOG_DELETE = "删除";
    public static final String SYSLOG_UPDATE = "修改";
    public static final String SYSLOG_QUERY = "查询";

    public static final String SESSION_USER_ID = "session_user_id";
    public static final String SESSION_USER_NAME = "session_user_name";
    public static final String SESSION_USER_TYPE = "session_user_type";
    public static final String SESSION_USER = "session_user";
    public static final String USER_TYPE_SUPER_ADMIN = "superAdmin";
    public static final String USER_TYPE_SUPER_ADMIN_TIP = "超级管理员";
    public static final String USER_TYPE_NORMAL_ADMIN = "normal";

    public static Map<String, String> USER_TYPE_MAP = null;

    public static Map<String, String> SYSTEM_MSG_MAP = null;

    //一些异常code定义
    public static final String NO_PERMISSION_CODE  = "no_permission";//无权限

    static {
        USER_TYPE_MAP = new HashMap<String, String>();
        USER_TYPE_MAP.put(USER_TYPE_SUPER_ADMIN, USER_TYPE_SUPER_ADMIN_TIP);
        USER_TYPE_MAP.put(USER_TYPE_NORMAL_ADMIN, "普通管理员");

        SYSTEM_MSG_MAP = new HashMap<String,String>();
        SYSTEM_MSG_MAP.put(NO_PERMISSION_CODE, "无权限");

    }

    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String SIMPLE_DATE_FORMAT_SFM = "yyyyMMddHHmmss";
}
