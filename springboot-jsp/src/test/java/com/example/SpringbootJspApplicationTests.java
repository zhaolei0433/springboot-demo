package com.example;

import com.example.business.sysUser.dao.ISysUserRepository;
import com.example.entity.SysUserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootJspApplicationTests {


    @Autowired
    private ISysUserRepository sysUserRepository;

    @Test
    public void contextLoads() {
        SysUserInfo sysUserInfo = sysUserRepository.findByUserName("zhaolei");

        System.out.println(sysUserInfo);
    }

}
