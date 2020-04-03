package com.example.redisdemo;

import com.example.redisdemo.entity.UserInfo;
import com.example.redisdemo.global.constants.SystemDefines;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class RedisDemoApplicationTests {


   /* private RedisTemplate<String,String> redisTemplate;

    @Autowired
    public RedisDemoApplicationTests(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }*/

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private Gson gson = new Gson();

    @Test
    void contextLoads() {
        String key = "zhaolei";
        //redisTemplate.opsForValue().set(key,"15680732285");
        //System.out.println(redisTemplate.hasKey(key));
        //System.out.println(redisTemplate.opsForValue().get(key));
        //System.out.println(redisTemplate.delete(key));
        //redisTemplate.opsForValue().set(key,"123",800, TimeUnit.SECONDS);

        //redisTemplate.persist(key);
    }

    @Test
    void contextLoads2() {
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setId(1);
        userInfo1.setUserName("zhaolei");
        userInfo1.setPassword("123456");
        userInfo1.setPhone("15680732285");
        userInfo1.setMailBox("1425089388@qq.com");

        UserInfo userInfo2 = new UserInfo();
        userInfo2.setId(1);
        userInfo2.setUserName("xiaoming111");
        userInfo2.setPassword("789456");
        userInfo2.setPhone("15680732285");
        userInfo2.setMailBox("1425089388@qq.com");

        //redisTemplate.opsForHash().put(SystemDefines.USER_INFO_HASH_KEY,userInfo2.getUserName(),gson.toJson(userInfo2));
        //redisTemplate.opsForHash().delete(SystemDefines.USER_INFO_HASH_KEY,userInfo1.getUserName());
        //System.out.println(redisTemplate.opsForHash().hasKey(SystemDefines.USER_INFO_HASH_KEY, userInfo2.getUserName()));

    }

}
