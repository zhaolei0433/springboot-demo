package com.ipanel.web.app.cv.business.user.dao;

import com.ipanel.web.app.cv.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhaolei
 * Create: 2018/9/21 16:10
 * Modified By:
 * Description:
 */
@Repository
public interface IUserRepository extends CrudRepository<User, Integer>, JpaSpecificationExecutor {
}
