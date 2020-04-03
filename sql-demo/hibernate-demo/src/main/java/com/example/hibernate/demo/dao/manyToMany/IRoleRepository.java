package com.example.hibernate.demo.dao.manyToMany;

import com.example.hibernate.demo.entity.manyToMany.RoleInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhaolei
 * Create: 2019/11/19 16:16
 * Modified By:
 * Description:
 */
@Repository
public interface IRoleRepository extends CrudRepository<RoleInfo, Integer>, JpaSpecificationExecutor {
}
