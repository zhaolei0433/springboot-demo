package com.example.utilsdemo.business.file.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author zhaolei
 * Create: 2019/4/28 10:50
 * Modified By:
 * Description:
 */
public interface IFileService {
    /**
     * 保存文件到指定文件夹
     * @param multipartFile
     * @return
     */
    Boolean uploadToDir(MultipartFile multipartFile) throws Exception;

    /**
     * 保存文件到指定文件夹
     * @param multipartFile
     * @return
     */
    Boolean unZipFileToDir(MultipartFile multipartFile,String unZipDirectory) throws Exception;
}
