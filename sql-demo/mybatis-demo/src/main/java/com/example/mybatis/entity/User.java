package com.example.mybatis.entity;

import lombok.Data;

/**
 * @author zhaolei
 * Create: 2019/7/17 10:55
 * Modified By:
 * Description: 用户实体
 */
@Data
public class User {
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别 1=男 2=女 其他=保密
     */
    private Integer sex;
}
