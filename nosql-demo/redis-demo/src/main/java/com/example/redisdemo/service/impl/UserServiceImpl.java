package com.example.redisdemo.service.impl;

import com.example.redisdemo.controller.request.AddUserReq;
import com.example.redisdemo.controller.request.UpdateUserReq;
import com.example.redisdemo.entity.UserInfo;
import com.example.redisdemo.global.constants.SystemDefines;
import com.example.redisdemo.service.IUserService;
import com.google.gson.Gson;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author zhaolei
 * Create: 2020/1/16 15:08
 * Modified By:
 * Description:
 */
@Service
public class UserServiceImpl implements IUserService {

    //private Gson gson = new Gson();
    //private RedisTemplate<String, String> redisTemplate;
    /*@Autowired
    public UserServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }*/

    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    public UserServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public UserInfo saveUserInfo(AddUserReq addUserReq) throws Exception {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(addUserReq, userInfo);
        redisTemplate.opsForHash().put(SystemDefines.USER_INFO_HASH_KEY, userInfo.getUserName(), userInfo);
        return userInfo;
    }

    @Override
    public UserInfo findUserInfoById(Integer id) {
        return null;
    }

    @Override
    public UserInfo findUserInfoByUserName(String userName) {
        return null;
    }

    @Override
    public UserInfo updateUserInfo(UpdateUserReq updateUserReq) throws Exception {
        return null;
    }

    @Override
    public Boolean deleteUserById(Integer id) {
        return null;
    }

}
