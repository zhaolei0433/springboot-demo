package com.example.business.sysUser.service;

import com.example.business.sysUser.controller.request.AddSysUserReq;
import com.example.entity.PermissionInfo;
import com.example.entity.SysUserInfo;

import java.util.List;

/**
 * @author zhaolei
 * Create: 2019/11/14 11:23
 * Modified By:
 * Description:
 */
public interface ISysUserService {

    SysUserInfo addSysUser(AddSysUserReq req) throws Exception;

    /**
     * 获取所有的资源数据
     * @return
     */
    public List<PermissionInfo> queryAllData();

    /**
     * 根据资源id获取权限名称
     * @param id
     * @return
     */
    public List<String> getRoleNameByPermissionInfoId(Integer id);
}
