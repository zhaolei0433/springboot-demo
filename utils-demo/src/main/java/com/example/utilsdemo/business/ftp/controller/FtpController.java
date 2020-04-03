package com.example.utilsdemo.business.ftp.controller;

import com.example.utilsdemo.business.ftp.service.IFtpService;
import com.example.utilsdemo.global.constants.SwaggerUIConstants;
import com.example.utilsdemo.global.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhaolei
 * Create: 2018/9/19 15:46
 * Modified By:
 * Description:
 */
@Api(tags = SwaggerUIConstants.FTP_PC_API)
@RestController
@RequestMapping(value = "ftp")
@CrossOrigin
public class FtpController {
    private static Logger logger = LoggerFactory.getLogger(FtpController.class);
    private IFtpService ftpService;

    @Autowired
    public FtpController(IFtpService ftpService) {
        this.ftpService = ftpService;
    }

    @ApiOperation(value = "连接ftp")
    @RequestMapping(value = "connectFTP", method = RequestMethod.GET)
    public Result<Boolean> connectFTP() throws Exception{
        return new Result<>(ftpService.connectToServer());
    }

    @ApiOperation(value = "断开ftp")
    @RequestMapping(value = "closeConnectFTP", method = RequestMethod.GET)
    public Result<Boolean> closeConnectFTP() throws Exception{
        return new Result<>(ftpService.closeConnect());
    }

    @ApiOperation(value = "查看ftp当前工作路径")
    @RequestMapping(value = "getFTPWorkingDir", method = RequestMethod.GET)
    public Result<String> getFTPWorkingDir() throws Exception{
        return new Result<>(ftpService.getWorkingDir());
    }

    @ApiOperation(value = "检查文件夹是否存在")
    @ApiImplicitParam(name = "DirName", value = "文件夹名称", dataType = "string", required = true, paramType = "query")
    @RequestMapping(value = "existDirectory", method = RequestMethod.GET)
    public Result<Boolean> existDirectory(@RequestParam("DirName")String DirName) throws Exception{
        return new Result<>(ftpService.existDirectory(DirName));
    }

    @ApiOperation(value = "创建文件夹")
    @ApiImplicitParam(name = "dirName", value = "文件夹名称", dataType = "string", required = true, paramType = "query")
    @RequestMapping(value = "createDir", method = RequestMethod.GET)
    public Result<Boolean> createDir(@RequestParam("dirName")String dirName) throws Exception{
        return new Result<>(ftpService.createDir(dirName));
    }

    @ApiOperation(value = "重命名文件夹")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldDirName", value = "文件夹老名称", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(name = "newDirName", value = "文件夹新名称", dataType = "string", required = true, paramType = "query")
    })
    @RequestMapping(value = "renameDir", method = RequestMethod.GET)
    public Result<Boolean> renameDir(@RequestParam("oldDirName")String oldDirName, @RequestParam("newDirName")String newDirName) throws Exception{
        return new Result<>(ftpService.renameDir(oldDirName,newDirName));
    }

    @ApiOperation(value = "改变工作路径")
    @ApiImplicitParam(name = "path", value = "路径", dataType = "string", required = true, paramType = "query")
    @RequestMapping(value = "changeFTPWorkingDir", method = RequestMethod.GET)
    public Result<Boolean> changeFTPWorkingDir(@RequestParam("path")String path) throws Exception{
        return new Result<>(ftpService.changeFTPWorkingDir(path));
    }

    @ApiOperation(value = "上传文件")
    @RequestMapping(value = "uploadToFtp", method = RequestMethod.POST)
    public Result<Boolean> uploadToFtp(@RequestParam("uploadFile") MultipartFile file) throws Exception{
        return new Result<>(ftpService.uploadToFtp(file));
    }

}
