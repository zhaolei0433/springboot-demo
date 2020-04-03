package com.example.utilsdemo.business.ftp.service.impl;


import com.example.utilsdemo.business.ftp.service.IFtpService;
import com.example.utilsdemo.utils.FtpUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhaolei
 * Create: 2018/9/19 15:51
 * Modified By:
 * Description:
 */
@Service
public class FtpServiceImpl implements IFtpService {
    private static Logger logger = LoggerFactory.getLogger(FtpServiceImpl.class);


    FTPClient ftpClient = new FTPClient();
    FtpUtil ftpUtil = new FtpUtil("zhaolei","!zl667799","139.159.177.48",21,"/appcms/tv",ftpClient);

    @Override
    public Boolean connectToServer() throws Exception {
        ftpUtil.connectToServer();
        return true;
    }

    @Override
    public Boolean closeConnect() throws Exception {
        ftpUtil.closeConnect();
        return true;
    }

    @Override
    public String getWorkingDir() throws Exception {
        return ftpUtil.getWorkingDirectory();
    }

    @Override
    public Boolean existDirectory(String dirName) throws Exception {
        return ftpUtil.existDirectory(dirName);
    }

    @Override
    public Boolean createDir(String dirName) throws Exception {
        return ftpUtil.createDirectory(dirName);
    }

    @Override
    public Boolean renameDir(String oldDirName, String newDirName) throws Exception {
        return ftpUtil.renameDir(oldDirName,newDirName);
    }

    @Override
    public Boolean changeFTPWorkingDir(String path) throws Exception {
        return ftpUtil.changeDir(path,true);
    }

    @Override
    public Boolean uploadToFtp(MultipartFile multipartFile) {
        try {
            String OriginalFilename = multipartFile.getOriginalFilename();
            //发送ftp服务器
            OriginalFilename = new String(OriginalFilename.getBytes("UTF-8"),"iso-8859-1");//解决中文乱码
            return ftpUtil.uploadToFtp(multipartFile.getInputStream(),OriginalFilename);
        }catch (Exception e){
            logger.info("uploadToFtp Exception",e);
        }
        return false;
    }
}
