package com.example.neo4jdemo.dao;

import com.example.neo4jdemo.entity.SurroundRelationship;
import com.example.neo4jdemo.entity.UserInfo;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

/**
 * @author zhaolei
 * Create: 2020/1/22 15:28
 * Modified By:
 * Description:
 */
public interface ISurroundRelationshipRepository extends Neo4jRepository<SurroundRelationship,Long> {

    List<SurroundRelationship> findByUserInfo(UserInfo userInfo);
}
