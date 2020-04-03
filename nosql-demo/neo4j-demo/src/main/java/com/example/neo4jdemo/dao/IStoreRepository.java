package com.example.neo4jdemo.dao;

import com.example.neo4jdemo.entity.StoreInfo;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

/**
 * @author zhaolei
 * Create: 2020/1/22 15:27
 * Modified By:
 * Description:
 */
public interface IStoreRepository extends Neo4jRepository<StoreInfo,Long> {

    /**
     * 根据用户名查询关联的节点信息，且路径
     * @param userName
     * @return
     */
    @Query("MATCH(:UserInfo{userName:{userName}})-[r:HAS_SURROUND_STORE{distance:{distance}}]->(StoreInfo) RETURN StoreInfo")
List<StoreInfo> findStoreInfo(String userName,String distance);
}
