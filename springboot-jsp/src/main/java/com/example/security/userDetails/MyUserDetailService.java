package com.example.security.userDetails;

import com.example.business.sysUser.dao.IRoleRepository;
import com.example.business.sysUser.dao.ISysUserRepository;
import com.example.entity.SysUserInfo;
import com.example.global.constants.SystemDefines;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhaolei
 * Create: 2019/11/19 16:25
 * Modified By:
 * Description: 当用户登录时会进入此类的loadUserByUsername方法对用户进行验证，验证成功后会被保存在当前回话的principal对象中
 * 系统获取当前登录对象信息方法 WebUserDetails webUserDetails =(WebUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 * 异常信息： UsernameNotFoundException 用户找不到
 *          BadCredentialsException 坏的凭据
 *          AccountExpiredException 账户过期
 *          LockedException 账户锁定
 *          DisabledException 账户不可用
 *          CredentialsExpiredException 证书过期
 */
@Service
public class MyUserDetailService implements UserDetailsService {

    private final static Logger LOGGER = LoggerFactory.getLogger(MyUserDetailService.class);

    private ISysUserRepository sysUserRepository;

    @Autowired
    public MyUserDetailService(ISysUserRepository sysUserRepository) {
        this.sysUserRepository = sysUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        LOGGER.info("username: {}", userName);
        SysUserInfo sysUser = sysUserRepository.findByUserName(userName);

        if (sysUser == null) {
            LOGGER.info("登录用户：{} 不存在.", userName);
            throw new UsernameNotFoundException("登录用户：" + userName + " 不存在");
        }
        // 超级管理员
        if (SystemDefines.USER_TYPE_SUPER_ADMIN.equals(sysUser.getUserType())) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(SystemDefines.USER_TYPE_SUPER_ADMIN_TIP);
            LOGGER.info("*****userHasRoleName: {}", authority.getAuthority());
            authorities.add(authority);
        } else {
            if (sysUser.getRoleInfos() != null && sysUser.getRoleInfos().size() > 0)
                authorities = sysUser.getRoleInfos().stream().map(roleInfo -> new SimpleGrantedAuthority(roleInfo.getName())).collect(Collectors.toList());
        }
        authorities.forEach(a -> LOGGER.info("****aName: {}", a.getAuthority()));
        return MyUserDetailsFactory.create(sysUser, authorities);
    }
}
