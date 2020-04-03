package com.example.ehcachecache.controller;

import com.example.ehcachecache.controller.request.AddUserReq;
import com.example.ehcachecache.controller.request.UpdateUserReq;
import com.example.ehcachecache.entity.one.UserInfo;
import com.example.ehcachecache.global.constants.SwaggerUIConstants;
import com.example.ehcachecache.global.constants.SystemDefines;
import com.example.ehcachecache.model.baesResult.Result;
import com.example.ehcachecache.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhaolei
 * Create: 2020/1/16 15:09
 * Modified By:
 * Description:
 */
@Api(tags = SwaggerUIConstants.USER_PC_API)
@RequestMapping(value = "user")
@RestController()
public class UserController {

    private IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }


    @ApiOperation(value = "添加用户")
    @ApiImplicitParam(name = "addUserReq", value = "用户参数", dataType = "AddUserReq", required = true, paramType = "body")
    @RequestMapping(value = "addUserReq", method = RequestMethod.POST)
    public Result<UserInfo> addUser(@RequestBody AddUserReq addUserReq) throws Exception {
        return new Result<>(userService.saveUserInfo(addUserReq));
    }

    @ApiOperation(value = "根据ID查询用户")
    @ApiImplicitParam(name = "id", value = "用户id", dataType = "int", required = true, paramType = "path")
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Result<UserInfo> findUserById(@PathVariable("id") Integer id){
        return new Result<>(userService.findUserInfoById(id));
    }

    @ApiOperation(value = "根据用户名查询用户")
    @ApiImplicitParam(name = "username", value = "用户username", dataType = "string", required = true, paramType = "query")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result<UserInfo> findUserByUserName(@RequestParam("username")String username){
        return new Result<>(userService.findUserInfoByUserName(username));
    }

    @ApiOperation(value = "修改用户")
    @ApiImplicitParam(name = "updateUserReq", value = "用户参数", dataType = "UpdateUserReq", required = true, paramType = "body")
    @RequestMapping(value = "updateUserReq", method = RequestMethod.PUT)
    public Result<UserInfo> UpdateUser(@RequestBody UpdateUserReq updateUserReq) throws Exception {
        return new Result<>(userService.updateUserInfo(updateUserReq));
    }


    @ApiOperation(value = "根据ID删除用户")
    @ApiImplicitParam(name = "id", value = "用户id", dataType = "int", required = true, paramType = "path")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Result<Boolean> deleteUser(@PathVariable("id") Integer id){
        return new Result<>(userService.deleteUserById(id));
    }
}
