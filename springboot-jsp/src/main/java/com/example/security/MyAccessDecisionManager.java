package com.example.security;

import com.example.global.constants.SystemDefines;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author zhaolei
 * Create: 2019/11/20 14:08
 * Modified By:
 * Description: 访问决策管理器
 */
@Service
public class MyAccessDecisionManager implements AccessDecisionManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyAccessDecisionManager.class);

    // authentication用户认证信息，具有的权限，object
    // 用户请求访问的URL，configAttributes权限集合，即访问资源需要具有的权限
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        LOGGER.info("****enter decide,configAttributes==null:{}", configAttributes == null);

        for (GrantedAuthority ga : authentication.getAuthorities()) {
            if (ga.getAuthority().equals(SystemDefines.USER_TYPE_SUPER_ADMIN_TIP)) {// 超级管理员具备所有权限
                return;
            }
        }
        // 该资源没有配置访问的角色权限，暂定不可访问
        if (configAttributes == null)
            throw new AccessDeniedException("no right!");
        Iterator<ConfigAttribute> ite = configAttributes.iterator();
        while (ite.hasNext()) {
            ConfigAttribute ca = ite.next();
            String needRole = ((SecurityConfig) ca).getAttribute();// 访问该资源所需的权限
            LOGGER.info("****enter MyAccessDecisionManager needRole:{}", needRole);
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                LOGGER.info("***ga.getAuthority():{}", ga.getAuthority());
                if (needRole.equals(ga.getAuthority())) {
                    return;
                }
            }
        }
        LOGGER.info("****无权限:{}", object);
        throw new AccessDeniedException("no right!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
