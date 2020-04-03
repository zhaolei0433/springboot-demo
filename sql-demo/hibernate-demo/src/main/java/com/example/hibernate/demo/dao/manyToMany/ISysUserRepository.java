package com.example.hibernate.demo.dao.manyToMany;

import com.example.hibernate.demo.entity.manyToMany.SysUserInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhaolei
 * Create: 2018/9/19 15:32
 * Modified By:
 * Description:
 */
@Repository
public interface ISysUserRepository extends CrudRepository<SysUserInfo, Integer>, JpaSpecificationExecutor {

    /**
     * 根据系统用户名查找系统用户信息
     *
     * @param userName 用户名
     * @return SysUserInfo
     */
    SysUserInfo findByUserName(String userName);
}
