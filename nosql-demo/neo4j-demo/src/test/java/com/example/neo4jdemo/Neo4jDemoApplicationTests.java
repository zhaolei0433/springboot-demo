package com.example.neo4jdemo;

import com.example.neo4jdemo.dao.IStoreRepository;
import com.example.neo4jdemo.dao.ISurroundRelationshipRepository;
import com.example.neo4jdemo.dao.IUserRepository;
import com.example.neo4jdemo.entity.StoreInfo;
import com.example.neo4jdemo.entity.SurroundRelationship;
import com.example.neo4jdemo.entity.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class Neo4jDemoApplicationTests {

    @Resource
    private IUserRepository userRepository;

    @Resource
    private IStoreRepository storeRepository;

    @Resource
    private ISurroundRelationshipRepository surroundRelationshipRepository;


    @Test
    void contextLoads() {
        //UserInfo userInfo = UserInfo.builder().userName("小明").build();
        //userRepository.save(userInfo);
        UserInfo userInfo = userRepository.findById(21L).get();


        StoreInfo storeInfo = StoreInfo.builder().storeName("拉州拉面").build();
        storeRepository.save(storeInfo);

        SurroundRelationship surroundRelationship = SurroundRelationship.builder().distance("1.2km").userInfo(userInfo).storeInfo(storeInfo).build();
        surroundRelationshipRepository.save(surroundRelationship);

    }

    @Test
    void contextLoads2() {
        UserInfo userInfo = userRepository.findByUserName("小明");
        System.out.println(userInfo);
        //List<SurroundRelationship> surroundRelationships = surroundRelationshipRepository.findByUserInfo(userInfo);
        //System.out.println(surroundRelationships);
        //surroundRelationships.forEach(surroundRelationship -> System.out.println(surroundRelationship.getStoreInfo()));

        List<StoreInfo> storeInfos = storeRepository.findStoreInfo(userInfo.getUserName(),"1.2km");
        System.out.println(storeInfos);

    }

}
