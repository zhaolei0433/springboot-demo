package com.example.rediscache.dao;

import com.example.rediscache.entity.one.UserInfo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author zhaolei
 * Create: 2019/4/19 15:25
 * Modified By:
 * Description:
 */

@Repository
public interface IUserRepository extends JpaRepository<UserInfo, Integer>, JpaSpecificationExecutor {

    @CachePut(value = "userInfo", key = "#result.id")
    @Override
    <S extends UserInfo> S save(S entity);


    @Cacheable(value = "userInfo", key = "#id")
    @Override
    Optional<UserInfo> findById(Integer id);

    @Cacheable(value = "userInfo", key = "#userName")
    List<UserInfo> findByUserName(String userName);

    @CacheEvict(value="userInfo",key = "#entity.id")
    @Override
    void delete(UserInfo entity);

    @CacheEvict(value="userInfo")
    void deleteById(Integer id);

    List<UserInfo> findByUserNameIsLike(String userName);

    Page<UserInfo> findByUserNameIsLike(String UserName, Pageable pageable);

    /**
     * hql语句，这里使用使用@Param注解注入参数
     *
     * @param name
     * @return
     */
    @Query(value = "FROM UserInfo WHERE userName LIKE %:name%", nativeQuery = false)
    List<UserInfo> findUserNameIsLikeByHql(@Param("name") String name);

    /**
     * 原生sql语句
     *
     * @param name
     * @return
     */
    @Query(value = "select * from user_info where user_name like %:name%", nativeQuery = true)
    List<UserInfo> findUserNameIsLikeBySql(@Param("name") String name);
}
