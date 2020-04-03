package com.example.ehcachecache.dao;

import com.example.ehcachecache.entity.one.UserDetailsInfo;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author zhaolei
 * Create: 2019/4/19 15:25
 * Modified By:
 * Description:
 */
@Repository
public interface IUserDetailsRepository extends JpaRepository<UserDetailsInfo, Integer>, JpaSpecificationExecutor {
}
