package com.example.business.sysUser.controller;

import com.example.business.sysUser.controller.request.AddSysUserReq;
import com.example.business.sysUser.service.ISysUserService;
import com.example.entity.SysUserInfo;
import com.example.model.baesResult.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaolei
 * Create: 2019/11/14 11:24
 * Modified By:
 * Description:
 */
@RestController
@RequestMapping("sysUser")
public class SysUserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserController.class);

    private ISysUserService sysUserService;

    @Autowired
    public SysUserController(ISysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }


    @RequestMapping(value = "addSysUser", method = RequestMethod.POST)
    public Result<SysUserInfo> addSysUser(@RequestBody AddSysUserReq req) throws Exception {
        return new Result<>(sysUserService.addSysUser(req));
    }




}
