package com.example.business.sysUser.controller.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhaolei
 * Create: 2019/11/14 11:30
 * Modified By:
 * Description:
 */
@Data
public class AddSysUserReq implements Serializable {
    private String userName;
    private String password;
    private String phone;
    private String email;
    private String userType;
}
