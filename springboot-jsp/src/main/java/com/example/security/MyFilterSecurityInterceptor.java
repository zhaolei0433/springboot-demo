package com.example.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhaolei
 * Create: 2019/11/19 17:22
 * Modified By:
 * Description:  该过滤器的主要作用就是通过spring著名的IoC生成securityMetadataSource。
 *  securityMetadataSource相当于本包中自定义的MyInvocationSecurityMetadataSource。
 *  该MyInvocationSecurityMetadataSource的作用提从数据库提取权限和资源，装配到HashMap中， 供Spring
 *  Security使用，用于权限校验。
 */
@Service
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyFilterSecurityInterceptor.class);


    @Autowired
    private MyInvocationSecurityMetadataSource myInvocationSecurityMetadataSource;

    @Autowired
    private MyAccessDecisionManager myAccessDecisionManager;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostConstruct
    public void init() {
        super.setAuthenticationManager(authenticationManager);
        super.setAccessDecisionManager(myAccessDecisionManager);
    }

    /**
     * Method that is actually called by the filter chain. Simply delegates to
     * the {@link #invoke(FilterInvocation)} method.
     *
     * @param request
     *            the servlet request
     * @param response
     *            the servlet response
     * @param chain
     *            the filter chain
     *
     * @throws IOException
     *             if the filter chain fails
     * @throws ServletException
     *             if the filter chain fails
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,PUT");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Credentials","true");
        ((HttpServletResponse) response).setHeader("Access-Control-Max-Age", "600");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Headers","Origin,X-Requested-With,Content-Type,Access-Control,Accept,Authorization");
        invoke(fi);
    }

    public void invoke(FilterInvocation fi) throws IOException,
            ServletException {
        LOGGER.info("**************开始权限检测,url:" + fi.getRequestUrl());
        // 在这儿调用了父类（AbstractSecurityInterceptor）的方法， 也就调用了accessDecisionManager
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public void destroy() {

    }

    public Class<? extends Object> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.myInvocationSecurityMetadataSource;
    }

    public void init(FilterConfig arg0) throws ServletException {
    }
}
