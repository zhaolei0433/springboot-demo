package com.example.rediscache.controller.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhaolei
 * Create: 2019/11/14 11:30
 * Modified By:
 * Description:
 */
@Data
public class AddUserReq implements Serializable {
    private String userName;
    private String password;
    private String phone;
    private String mailBox;
}
