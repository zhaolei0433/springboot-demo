package com.ipanel.web.app.cv.business.myLog.service.impl;

import com.ipanel.web.app.cv.business.myLog.dao.IMyLogRepository;
import com.ipanel.web.app.cv.business.myLog.service.IMyLogService;
import com.ipanel.web.app.cv.entity.LogInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhaolei
 * Create: 2018/12/28 16:34
 * Modified By:
 * Description:
 */
@Service
public class MyLogServiceImpl implements IMyLogService {

    private IMyLogRepository myLogRepository;

    @Autowired
    public MyLogServiceImpl(IMyLogRepository myLogRepository) {
        this.myLogRepository = myLogRepository;
    }

    @Override
    public Boolean addLog(LogInfo logInfo) throws Exception {
        myLogRepository.save(logInfo);
        return true;
    }
}
