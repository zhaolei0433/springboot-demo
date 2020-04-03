package com.example.rediscache.service;

import com.example.rediscache.controller.request.AddUserReq;
import com.example.rediscache.controller.request.UpdateUserReq;
import com.example.rediscache.entity.one.UserInfo;

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
