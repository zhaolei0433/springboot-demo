package com.ipanel.web.app.cv.business.myLog.dao;

import com.ipanel.web.app.cv.entity.LogInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhaolei
 * Create: 2018/12/28 16:30
 * Modified By:
 * Description:
 */
@Repository
public interface IMyLogRepository extends CrudRepository<LogInfo, Integer>, JpaSpecificationExecutor {

}
