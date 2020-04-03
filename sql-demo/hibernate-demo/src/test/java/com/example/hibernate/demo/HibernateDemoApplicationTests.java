package com.example.hibernate.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HibernateDemoApplicationTests {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Test
    public void contextLoads() {
    }

}
