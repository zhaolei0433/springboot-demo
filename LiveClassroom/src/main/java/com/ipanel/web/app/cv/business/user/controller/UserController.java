package com.ipanel.web.app.cv.business.user.controller;

import com.ipanel.web.app.cv.annotation.SysLog;
import com.ipanel.web.app.cv.business.place.controller.request.AddPlaceReq;
import com.ipanel.web.app.cv.business.user.controller.request.AddUserReq;
import com.ipanel.web.app.cv.business.user.service.IUserService;
import com.ipanel.web.app.cv.entity.User;
import com.ipanel.web.app.cv.global.SystemDefines;
import com.ipanel.web.app.cv.global.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhaolei
 * Create: 2019/3/4 16:35
 * Modified By:
 * Description:
 */
@Api(tags = SystemDefines.USER_PC_API)
@RestController
@RequestMapping(value = "user")
@CrossOrigin
public class UserController {

    private IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @SysLog(operationType = SystemDefines.SYSLOG_ADD, operationName = "添加用户")
    @ApiOperation(value = "添加会场")
    @ApiImplicitParam(name = "req", value = "用户参数", dataType = "AddUserReq", required = true, paramType = "body")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<User> addUser(@RequestBody AddUserReq req) throws Exception {
        return new Result<>(userService.addUser(req));
    }

    @SysLog(operationType = SystemDefines.SYSLOG_ADD, operationName = "excel添加会场")
    @ApiOperation(value = "excel添加会场")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public Result<Boolean> addUser(@RequestParam("file") MultipartFile file) {
        boolean a = false;
        String fileName = file.getOriginalFilename();
        try {
            a = userService.batchImport(fileName, file);
            System.out.println(fileName );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new Result<>(a);
    }
}
