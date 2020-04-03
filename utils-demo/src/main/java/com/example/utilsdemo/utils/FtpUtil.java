package com.example.utilsdemo.utils;

import com.example.utilsdemo.global.exception.MethodParamErrorException;
import com.example.utilsdemo.global.exception.OperationErrorException;
import lombok.Data;
import org.apache.commons.net.ftp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

/**
 * @author zhaolei
 * Create: 2019/4/23 14:08
 * Modified By:
 * Description:ftp上传下载工具类
 */
@Data
public class FtpUtil {
    private static Logger log = LoggerFactory.getLogger(FtpUtil.class);
    public static final int imageCutSize=300;
    public static final String DIRSPLIT="/appcms/tv";

    private String userName;
    private String passWord;
    private String ip;
    private int port;
    private String CURRENT_DIR;
    private FTPClient ftpClient;

    public FtpUtil() {
    }

    public FtpUtil(String userName, String passWord, String ip, int port, String CURRENT_DIR, FTPClient ftpClient) {
        this.userName = userName;
        this.passWord = passWord;
        this.ip = ip;
        this.port = port;
        this.CURRENT_DIR = CURRENT_DIR;
        this.ftpClient = ftpClient;
    }

    /**
     * 功能：连接到ftp服务器
     * @throws Exception
     */
    public void connectToServer() throws Exception {
        if (ftpClient.isConnected()) {
            throw new OperationErrorException("FTP服务器【" + ip + "】已经是连接状态，无需再次连接");
        }
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(ip, port);
            ftpClient.login(userName, passWord);
        } catch (IOException e) {
            throw new OperationErrorException("FTP服务器【" + ip + "】连接失败！"+e.getMessage());
        }
        if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
            ftpClient.disconnect();
            throw new OperationErrorException("FTP服务器【" + ip + "】拒绝连接，请确认用户名密码");
        }
        log.info("FTP服务器【" + ip + "】连接成功！");
    }

    /**
     * 功能：关闭连接
     */
    public void closeConnect() throws OperationErrorException {
        if (!ftpClient.isConnected()) {
            throw new OperationErrorException("FTP服务器【" + ip + "】已经是断开状态状态，无需再次断开");
        }
        try {
            ftpClient.logout();
            ftpClient.disconnect();
        } catch (IOException e) {
            throw new OperationErrorException("FTP服务器【" + ip + "】连接失败！" + e.getMessage());
        }
        log.info("FTP服务器【" + ip + "】断开连接成功！");
    }


    /**
     * 功能：查看当前工作目录
     * @return
     */
    public String getWorkingDirectory() throws Exception {
        try {
            return ftpClient.printWorkingDirectory();
        } catch (Exception e) {
            throw new OperationErrorException(e.getMessage());
        }
    }

    /**
     * 功能：检查当前工作路径下文件夹是否存在
     * @return
     * @throws Exception
     */
    public boolean existDirectory(String DirName) throws Exception {
        try {
            boolean flag = false;
            FTPFile[] ftpFileArr = ftpClient.listFiles();
            for (FTPFile ftpFile : ftpFileArr) {
                if (ftpFile.isDirectory()&& ftpFile.getName().equals(DirName)) {
                    flag = true;
                    break;
                }
            }
            return flag;
        } catch (Exception e) {
            throw new OperationErrorException(e.getMessage());
        }
    }

    /**
     * 功能：根据文件名在工作路径下获取文件或文件夹
     * @param fileName
     * @return
     * @throws Exception
     */
    public FTPFile getFile(String fileName) throws Exception{
        if(fileName == null || fileName.trim().equals("")) {
            return null;
        }
        try {
            FTPFile file = null;
            fileName = fileName.trim();
            FTPFile[] files = ftpClient.listFiles();
            for(int i=0; i<files.length; i++){
                if(files[i].getName().equals(fileName)){
                    file = files[i];
                    log.info(ftpClient.printWorkingDirectory()+"中，找到文件名为" + fileName + "的文件");
                    break;
                }
            }
            if (file == null)
                log.info(ftpClient.printWorkingDirectory()+"中，找不到该文件夹下文件名为：" + fileName + "的文件");
            return file;
        }catch (Exception e){
            throw new OperationErrorException(e.getMessage());
        }
    }

    /**
     * 当前工作路径下创建文件夹
     * @param DirName 文件夹名
     * @return true：成功；false：失败，没有读写权限
     * @throws Exception
     */
    public boolean createDirectory(String DirName) throws Exception{
        String path = "";
        try {
            path = ftpClient.printWorkingDirectory();
        }catch (Exception e){
            throw new OperationErrorException(e.getMessage());
        }
        FTPFile file = this.getFile(DirName);
        if (file != null && !file.isDirectory()) {
            throw new OperationErrorException(path+"路径下已存在文件名为" + DirName + "的文件，无法创建相同命名的文件夹目录！");
        } else if (file != null && file.isDirectory()) {
            log.info(path+"路径下，文件名：" + DirName + "的目录已经存在");
            return true;
        }
        boolean flag = false;
        try {
            flag = ftpClient.makeDirectory(DirName);
        } catch (Exception e) {
            throw new OperationErrorException(e.getMessage());
        }
        log.info(path+"路径下，创建文件目录名为" + DirName + "的目录，结果："+ (flag ? "成功" : "失败"));
        return flag;
    }

    /**
     * 当前工作路径下重命名文件夹
     * @param oldDirName 旧文件夹名
     * @param newDirName 新文件件名
     * @return true：成功；false：失败，没有读写权限
     */
    public boolean renameDir(String oldDirName, String newDirName) throws Exception {
        String path = "";
        try {
            path = ftpClient.printWorkingDirectory();
        } catch (Exception e) {
            throw new OperationErrorException(e.getMessage());
        }
        if (!this.existDirectory(oldDirName))
            throw new OperationErrorException(path + "路径下，不存在名为：" + oldDirName + "的文件夹");
        FTPFile file = this.getFile(newDirName);
        if (file != null) {
            throw new OperationErrorException(path + "路径下，已存在文件名为" + newDirName + "的文件或文件夹，无法创建相同命名的文件夹目录！");
        }
        boolean flag = false;
        try {
            flag = ftpClient.rename(oldDirName, newDirName);
        } catch (Exception e) {
            throw new OperationErrorException(e.getMessage());
        }
        log.info(path + "路径下，重命名文件夹：" + oldDirName + " -> " + newDirName + "，结果："+ (flag ? "成功" : "失败"));
        return flag;
    }

    /**
     * 切换ftp目录/ 开头则回到此次登录的跟目录再创建目录 否则从当前目录开始
     * @param ftpPath 必须是以“/”开头的路径,且必须是文件夹的完整路径
     * @param flag true:无此路径时，创建此路径返回true；false:无此路径时返回false
     * @return true：成功；false：失败，flag = false,此路径不存在;flag = true,此路径下无读写权限.
     * @throws Exception
     */
    public Boolean changeDir(String ftpPath, Boolean flag) throws Exception{
        if(ftpPath == null || "".equals(ftpPath))
            throw new MethodParamErrorException("访问路径不能为空");
        if (!ftpPath.startsWith("/") || (ftpPath.length() > 1 && ftpPath.endsWith("/")))
            throw new MethodParamErrorException("访问路径格式不正确");
        try {
            ftpPath=ftpPath.replaceAll("^[ 　]+","");
            ftpPath=ftpPath.replaceAll("[ 　]+$","");
            if (flag){
                if("/".equals(ftpPath)){
                    ftpClient.changeWorkingDirectory("/");
                    return true;
                }
                ftpClient.changeWorkingDirectory("/");
                String[] dirArray = ftpPath.split("/");
                for (int i = 1; i < dirArray.length; i++) {
                    this.createDirectory(dirArray[i]);
                    ftpClient.changeWorkingDirectory(dirArray[i]);
                }
                return ftpPath.equals(ftpClient.printWorkingDirectory());
            }else {
                ftpClient.changeWorkingDirectory(ftpPath);
                return ftpPath.equals(ftpClient.printWorkingDirectory());
            }
        }catch (Exception e){
            throw new OperationErrorException(e.getMessage());
        }
    }

    /**
     * 上传流文件
     * @param buffIn 文件流
     * @param fileName 文件名
     * @return true :成功 ；false：失败，无上传权限
     * @throws Exception
     */
    public boolean uploadToFtp(InputStream buffIn, String fileName) throws Exception {
        String path = "";
        try {
            path = ftpClient.printWorkingDirectory();
        } catch (Exception e) {
            throw new OperationErrorException(e.getMessage());
        }
        int reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpClient.disconnect();
            throw new IOException("FTP服务器【" + ip + "】连接失败！");
        }
        //设置传输二进制文件
        try {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
        } catch (Exception e) {
            throw new OperationErrorException("FTP服务器【" + ip + "】设置文件传输类型失败!");
        }
        //进入工作文件夹
        if (path.equals("/")){
            path = path + CURRENT_DIR;
        }else {
            path = path + "/" + CURRENT_DIR;
        }
        this.changeDir(path,true);
        // 上传文件
        boolean flag = false;
        try {
            flag = ftpClient.storeFile(fileName, buffIn);
            log.info("FTP服务器【" + ip + "】" + path + "下文件上传" + (flag ? "成功" : "失败"));
        } catch (Exception e) {
            throw new OperationErrorException("FTP服务器【" + ip + "】" + path + "下文件上传失败！" + e.getMessage());
        } finally {
            try {
                if (buffIn != null) {
                    buffIn.close();
                }
            } catch (Exception e) {
                log.error("ftp关闭输入流时失败！", e);
            }
        }
        return flag;
    }

    /**
     *  功能：根据当前时间获取文件目录
     * @param dateParam
     * @return
     */
    public static String getDateDir(Date dateParam) {
        Calendar cal = Calendar.getInstance();
        if (null != dateParam) {
            cal.setTime(dateParam);
        }
        int currentYear = cal.get(Calendar.YEAR);
        int currentMouth = cal.get(Calendar.MONTH) + 1;
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
        int currentHour = cal.get(Calendar.HOUR_OF_DAY);
        return currentYear + FtpUtil.DIRSPLIT + currentMouth + FtpUtil.DIRSPLIT + currentDay + FtpUtil.DIRSPLIT + currentHour;

    }
}
