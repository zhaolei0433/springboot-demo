package com.ipanel.web.app.cv.business.myLog.service;

import com.ipanel.web.app.cv.entity.LogInfo;

/**
 * @author zhaolei
 * Create: 2018/12/28 16:31
 * Modified By:
 * Description:
 */
public interface IMyLogService {
    /**
     * 添加日志
     *
     * @param logInfo
     * @return
     */
    Boolean addLog(LogInfo logInfo) throws Exception;
}
