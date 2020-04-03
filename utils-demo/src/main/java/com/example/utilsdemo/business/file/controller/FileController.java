package com.example.utilsdemo.business.file.controller;

import com.example.utilsdemo.business.file.service.IFileService;
import com.example.utilsdemo.business.ftp.controller.FtpController;
import com.example.utilsdemo.business.ftp.service.IFtpService;
import com.example.utilsdemo.global.constants.SwaggerUIConstants;
import com.example.utilsdemo.global.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhaolei
 * Create: 2019/4/28 10:48
 * Modified By:
 * Description:
 */
@Api(tags = SwaggerUIConstants.FILE_PC_API)
@RestController
@RequestMapping(value = "file")
@CrossOrigin
public class FileController {

    private static Logger logger = LoggerFactory.getLogger(FileController.class);
    private IFileService fileService;

    @Autowired
    public FileController(IFileService fileService) {
        this.fileService = fileService;
    }

    @ApiOperation(value = "上传文件,保存到指定文件夹")
    @RequestMapping(value = "uploadToDir", method = RequestMethod.POST)
    public Result<Boolean> uploadToDir(@RequestParam("uploadToDir") MultipartFile multipartFile) throws Exception{
        return new Result<>(fileService.uploadToDir(multipartFile));
    }

    @ApiOperation(value = "上传ZIP文件,并解压保存到指定文件夹")
    @RequestMapping(value = "unZipFileToDir", method = RequestMethod.POST)
    public Result<Boolean> unZipFileToDir(@RequestParam("uploadToDir") MultipartFile multipartFile, @RequestParam("unZipDirectory") String unZipDirectory) throws Exception{
        return new Result<>(fileService.unZipFileToDir(multipartFile,unZipDirectory));
    }
}
