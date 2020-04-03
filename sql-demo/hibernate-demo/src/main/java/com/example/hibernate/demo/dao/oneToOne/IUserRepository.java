package com.example.hibernate.demo.dao.oneToOne;

import com.example.hibernate.demo.entity.one.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhaolei
 * Create: 2019/4/19 15:25
 * Modified By:
 * Description:
 */
@Repository
public interface IUserRepository extends JpaRepository<UserInfo, Integer>, JpaSpecificationExecutor {

    List<UserInfo> findByUserNameIsLike(String userName);

    Page<UserInfo> findByUserNameIsLike(String UserName, Pageable pageable);

    /**
     * hql语句，这里使用使用@Param注解注入参数
     *
     * @param name
     * @return
     */
    @Transactional(rollbackFor = {Throwable.class})
    @Modifying
    @Query(value = "FROM UserInfo WHERE userName LIKE %:name%", nativeQuery = false)
    List<UserInfo> findUserNameIsLikeByHql(@Param("name") String name);

    /**
     * 原生sql语句
     *
     * @param name
     * @return
     */
    @Transactional(rollbackFor = {Throwable.class})
    @Modifying
    @Query(value = "select * from user_info where user_name like %:name%", nativeQuery = true)
    List<UserInfo> findUserNameIsLikeBySql(@Param("name") String name);
}
