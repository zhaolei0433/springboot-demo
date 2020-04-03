package com.ipanel.web.app.cv.business.user.service;

import com.ipanel.web.app.cv.business.user.controller.request.AddUserReq;
import com.ipanel.web.app.cv.entity.User;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhaolei
 * Create: 2018/9/21 16:14
 * Modified By:
 * Description:
 */
public interface IUserService {

    User addUser(AddUserReq req);

    boolean batchImport(String fileName, MultipartFile file) throws Exception;
}
