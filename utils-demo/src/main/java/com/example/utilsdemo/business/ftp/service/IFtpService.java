package com.example.utilsdemo.business.ftp.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author zhaolei
 * Create: 2018/9/19 15:49
 * Modified By:
 * Description:
 */
public interface IFtpService {

    /**
     * 连接FTP服务器
     * @return
     */
    Boolean connectToServer() throws Exception;

    /**
     * 断开FTP服务器连接
     * @return
     * @throws Exception
     */
    Boolean closeConnect() throws Exception;

    /**
     * 获取工作路径
     * @return
     * @throws Exception
     */
    String getWorkingDir() throws Exception;

    /**
     * 检查工作路径是否存在
     * @return
     * @throws Exception
     */
    Boolean existDirectory(String dirName) throws  Exception;

    /**
     * 创建文件夹
     * @param dirName
     * @return
     * @throws Exception
     */
    Boolean createDir(String dirName) throws  Exception;

    /**
     * 重命名文件夹
     * @param oldDirName
     * @param newDirName
     * @return
     */
    Boolean renameDir(String oldDirName, String newDirName) throws Exception;

    /**
     * 修改ftp工作路径
     * @param path
     * @return
     */
    Boolean changeFTPWorkingDir(String path) throws Exception;

    /**
     * 上传文件
     * @param multipartFile
     * @return
     */
    Boolean uploadToFtp(MultipartFile multipartFile) throws Exception;
}
