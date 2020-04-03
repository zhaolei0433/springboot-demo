package com.example.hibernate.demo;

import com.example.hibernate.demo.dao.manyToMany.ISysUserRepository;
import com.example.hibernate.demo.dao.oneToMany.IAuthorRepository;
import com.example.hibernate.demo.dao.oneToMany.IBookRepository;
import com.example.hibernate.demo.entity.manyToMany.RoleInfo;
import com.example.hibernate.demo.entity.manyToMany.SysUserInfo;
import com.example.hibernate.demo.entity.oneToMany.AuthorInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * @author zhaolei
 * Create: 2019/11/22 16:10
 * Modified By:
 * Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HibernateDemoManyToManyTests {

    @Autowired
    private ISysUserRepository sysUserRepository;

    @Test
    public void addSysUser() {
        SysUserInfo sysUserInfo = new SysUserInfo();
        sysUserInfo.setUserName("zhaolei");
        sysUserInfo.setPassword("zhaolei");
        sysUserInfo.setPhone("15680732285");

        System.out.println(sysUserRepository.save(sysUserInfo));
    }

    @Test
    public void findByUserName() {
        SysUserInfo sysUserInfo = sysUserRepository.findByUserName("zhaolei");
        Set<RoleInfo> roleInfoSet = sysUserInfo.getRoleInfos();
        System.out.println(roleInfoSet);
    }

}
