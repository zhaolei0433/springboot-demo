package com.example.utilsdemo.business.file.service.impl;

import com.example.utilsdemo.business.file.service.IFileService;
import com.example.utilsdemo.utils.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author zhaolei
 * Create: 2019/4/28 10:51
 * Modified By:
 * Description:
 */
@Service
public class FileServiceImpl implements IFileService {
    @Override
    public Boolean uploadToDir(MultipartFile multipartFile) throws Exception {
        String OriginalFilename = multipartFile.getOriginalFilename();
        //本地备份保存
        File saveTargetFile = FileUtil.createTargetFile("E:\\utils_demo_backup","\\file");
        assert OriginalFilename != null;
        File outPutfile = new File(saveTargetFile, OriginalFilename);
        FileUtil.outPutFile(multipartFile.getInputStream(),outPutfile);
        return true;
    }

    @Override
    public Boolean unZipFileToDir(MultipartFile multipartFile, String unZipDirectory) throws Exception {
        String OriginalFilename = multipartFile.getOriginalFilename();
        //本地备份保存
        File saveTargetFile = FileUtil.createTargetFile("E:\\utils_demo_backup","\\file");
        assert OriginalFilename != null;
        File outPutfile = new File(saveTargetFile, OriginalFilename);
        FileUtil.outPutFile(multipartFile.getInputStream(),outPutfile);
        System.out.println(FileUtil.unZipFile(outPutfile.getAbsolutePath(),unZipDirectory));
        return true;
    }


}
