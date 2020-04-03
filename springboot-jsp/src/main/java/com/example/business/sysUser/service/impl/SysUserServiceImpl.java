package com.example.business.sysUser.service.impl;

import com.example.business.sysUser.controller.SysUserController;
import com.example.business.sysUser.controller.request.AddSysUserReq;
import com.example.business.sysUser.dao.IPermissionRepository;
import com.example.business.sysUser.dao.ISysUserRepository;
import com.example.business.sysUser.service.ISysUserService;
import com.example.entity.PermissionInfo;
import com.example.entity.RoleInfo;
import com.example.entity.SysUserInfo;
import com.example.global.constants.SystemDefines;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author zhaolei
 * Create: 2019/11/14 11:23
 * Modified By:
 * Description:
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserServiceImpl.class);

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();


    private ISysUserRepository sysUserRepository;

    private IPermissionRepository permissionRepository;

    @Autowired
    public SysUserServiceImpl(ISysUserRepository sysUserRepository, IPermissionRepository permissionRepository) {
        this.sysUserRepository = sysUserRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public SysUserInfo addSysUser(AddSysUserReq req) throws Exception {
        SysUserInfo sysUser = new SysUserInfo();
        BeanUtils.copyProperties(req, sysUser);
        sysUser.setCreateTime(new SimpleDateFormat(SystemDefines.SIMPLE_DATE_FORMAT).format(new Date()));
        sysUser.setUserType(SystemDefines.USER_TYPE_NORMAL_ADMIN);
        sysUser.setPassword(PASSWORD_ENCODER.encode(req.getPassword()));
        return sysUserRepository.save(sysUser);
    }

    @Override
    public List<PermissionInfo> queryAllData() {
        return (List<PermissionInfo>) permissionRepository.findAll();
    }

    @Override
    public List<String> getRoleNameByPermissionInfoId(Integer id) {
        Optional<PermissionInfo> permissionInfoOptional  = permissionRepository.findById(id);
        if (permissionInfoOptional.isPresent()){
            PermissionInfo permissionInfo = permissionInfoOptional.get();
            return permissionInfo.getRoleInfos().stream().map(RoleInfo::getName).collect(Collectors.toList());
        }
        return null;
    }
}
