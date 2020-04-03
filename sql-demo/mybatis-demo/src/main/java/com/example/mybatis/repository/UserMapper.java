package com.example.mybatis.repository;

import com.example.mybatis.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhaolei
 * Create: 2019/7/17 11:00
 * Modified By:
 * Description:
 */
@Repository
public interface UserMapper {
    /**
     * 新增用户
     * @param user
     * @return
     */
    int save (User user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int update (User user);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteById (int id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Select("SELECT * FROM blog WHERE id = #{id}")
    User selectById (int id);

    /**
     * 查询所有用户信息
     * @return
     */
    List<User> selectAll ();
}
