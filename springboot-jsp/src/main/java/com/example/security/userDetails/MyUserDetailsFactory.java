package com.example.security.userDetails;

import com.example.entity.SysUserInfo;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * @author zhaolei
 * Create: 2019/11/19 16:39
 * Modified By:
 * Description:
 */
public class MyUserDetailsFactory {
    private MyUserDetailsFactory() {}

    public static MyUserDetails create(SysUserInfo userInfo, List<GrantedAuthority> authorities) {
        return new MyUserDetails(userInfo, authorities);
    }
}
