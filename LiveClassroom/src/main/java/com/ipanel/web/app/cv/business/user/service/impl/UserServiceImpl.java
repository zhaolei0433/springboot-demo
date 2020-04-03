package com.ipanel.web.app.cv.business.user.service.impl;

import com.ipanel.web.app.cv.business.user.controller.request.AddUserReq;
import com.ipanel.web.app.cv.business.user.dao.IUserRepository;
import com.ipanel.web.app.cv.business.user.service.IUserService;
import com.ipanel.web.app.cv.entity.User;
import com.ipanel.web.app.cv.global.MyException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaolei
 * Create: 2018/9/21 16:14
 * Modified By:
 * Description:
 */
@Service
public class UserServiceImpl implements IUserService {

    private IUserRepository userRepository;

    @Autowired
    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(AddUserReq req) {
        User user = new User();
        BeanUtils.copyProperties(req, user);
        return userRepository.save(user);
    }

    @Transactional(readOnly = false,rollbackFor = Exception.class)
    @Override
    public boolean batchImport(String fileName, MultipartFile file) throws Exception {

        boolean notNull = false;
        List<User> userList = new ArrayList<User>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new MyException("上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if(sheet!=null){
            notNull = true;
        }
        User user;
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null){
                continue;
            }

            user = new User();

            if( row.getCell(0).getCellType() !=1){
                throw new MyException("导入失败(第"+(r+1)+"行,姓名请设为文本格式)");
            }
            String username = row.getCell(0).getStringCellValue();

            if(username == null || username.isEmpty()){
                throw new MyException("导入失败(第"+(r+1)+"行,姓名未填写)");
            }
            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
            String password = row.getCell(1).getStringCellValue();
            if(password==null || password.isEmpty()){
                throw new MyException("导入失败(第"+(r+1)+"行,密码未填写)");
            }
            user.setUserName(username);
            user.setPassWord(password);
            userList.add(user);
        }
        System.out.println(userList);
        return notNull;
    }
}
