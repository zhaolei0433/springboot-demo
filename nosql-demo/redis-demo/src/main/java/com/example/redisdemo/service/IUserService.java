package com.example.redisdemo.service;

import com.example.redisdemo.controller.request.AddUserReq;
import com.example.redisdemo.controller.request.UpdateUserReq;
import com.example.redisdemo.entity.UserInfo;

/**
 * @author zhaolei
 * Create: 2020/1/16 15:07
 * Modified By:
 * Description:
 */
public interface IUserService {
    /**
     * 保存用户信息
     * @return
     */
    UserInfo saveUserInfo(AddUserReq addUserReq) throws Exception;

    UserInfo findUserInfoById(Integer id);

    UserInfo findUserInfoByUserName(String userName);

    /**
     * 更新用户信息
     * @return
     */
    UserInfo updateUserInfo(UpdateUserReq updateUserReq) throws Exception;

    Boolean deleteUserById(Integer id);
}
