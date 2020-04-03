package com.example.ehcachecache.service.impl;

import com.example.ehcachecache.controller.request.AddUserReq;
import com.example.ehcachecache.controller.request.UpdateUserReq;
import com.example.ehcachecache.dao.IUserRepository;
import com.example.ehcachecache.entity.one.UserInfo;
import com.example.ehcachecache.service.IUserService;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author zhaolei
 * Create: 2020/1/16 15:08
 * Modified By:
 * Description:
 */
@Service
public class UserServiceImpl implements IUserService {
    private IUserRepository userRepository;

    @Autowired
    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }


   // @Transactional(rollbackFor = Exception.class)
    @CachePut(value = "userInfo", key = "#result.id")
    @Override
    public UserInfo saveUserInfo(AddUserReq addUserReq) throws Exception {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(addUserReq, userInfo);
        userRepository.save(userInfo);
        throw new Exception();
        //return userRepository.save(userInfo);
    }


    @Override
    public UserInfo findUserInfoById(Integer id) {
        Optional<UserInfo> userInfoOptional = userRepository.findById(id);
        return userInfoOptional.orElse(null);
    }

    @Override
    public UserInfo findUserInfoByUserName(String userName) {
        return userRepository.findByUserName(userName).get(0);
    }

    @Override
    public UserInfo updateUserInfo(UpdateUserReq updateUserReq) throws Exception {
        UserInfo userInfo = userRepository.findById(updateUserReq.getId()).get();
        //BeanUtils.copyProperties(updateUserReq, userInfo);
        userInfo.setUserName(updateUserReq.getUserName());
        return userRepository.save(userInfo);
    }

    @Override
    public Boolean deleteUserById(Integer id) {
        userRepository.deleteById(id);
        return true;
    }
}
