package com.example.redisdemo.controller.request;

import lombok.Data;

/**
 * @author zhaolei
 * Create: 2020/1/20 15:28
 * Modified By:
 * Description:
 */
@Data
public class UpdateUserReq {
    private Integer id;
    private String userName;
    private String password;
    private String phone;
    private String mailBox;
}
