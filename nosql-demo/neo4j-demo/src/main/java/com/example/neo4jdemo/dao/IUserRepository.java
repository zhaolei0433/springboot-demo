package com.example.neo4jdemo.dao;

import com.example.neo4jdemo.entity.UserInfo;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @author zhaolei
 * Create: 2020/1/22 11:35
 * Modified By:
 * Description:
 */
public interface IUserRepository extends Neo4jRepository<UserInfo,Long> {
    UserInfo findByUserName(String userName);
}
