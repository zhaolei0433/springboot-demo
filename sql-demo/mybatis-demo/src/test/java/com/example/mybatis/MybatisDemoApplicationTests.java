package com.example.mybatis;

import com.example.mybatis.entity.User;
import com.example.mybatis.repository.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisDemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void save() {
        User user = new User();
        user.setUsername("zzzz");
        user.setPassword("bbbb");
        user.setSex(1);
        user.setAge(18);
        // 返回插入的记录数 ，期望是1条 如果实际不是一条则抛出异常
        Assert.assertEquals(1,userMapper.save(user));
    }

}
