package com.example.redisdemo.entity;

import lombok.Data;
import java.io.Serializable;

/**
 * @author zhaolei
 * Create: 2019/4/19 14:50
 * Modified By:
 * Description:
 */
@Data
public class UserInfo implements Serializable{
    private Integer id;

    private String userName;

    private String password;

    private String phone;

    private String mailBox;
}
