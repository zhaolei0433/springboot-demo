package com.example.security.userDetails;

import com.example.entity.SysUserInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Set;

/**
 * @author zhaolei
 * Create: 2019/11/19 11:35
 * Modified By:
 * Description: 用户登录验证
 */
public class MyUserDetails implements UserDetails {
    private Integer userId;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean enabled; //是否开启
    //private boolean accountNonExpired; //是否过期



    MyUserDetails(SysUserInfo userInfo, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userInfo.getId();
        this.username = userInfo.getUserName();
        this.password = userInfo.getPassword();
        this.authorities = authorities;
        this.enabled = userInfo.isEnabled();
        //this.accountNonExpired =
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    public Integer getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "MyUserDetails{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                ", enabled=" + enabled +
                '}';
    }
}
