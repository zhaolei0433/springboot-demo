package com.example.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhaolei
 * Create: 2019/11/20 16:46
 * Modified By:
 * Description: 自定义登陆
 */
public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyUsernamePasswordAuthenticationFilter.class);

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String method = request.getMethod();
        String url = request.getRequestURI();
        LOGGER.info("********************enter attemptAuthentication,method:{};url:{}", method, url);
        //return super.attemptAuthentication(request, response);


        if (!method.toUpperCase().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String userName = obtainUsername(request);
        String password = obtainPassword(request);
        LOGGER.info("********userName:{};password:{}", userName , password);

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, password);
        setDetails(request, authRequest);
        LOGGER.info( "*************finished");
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        Object obj = request.getParameter(SPRING_SECURITY_FORM_PASSWORD_KEY);
        return null == obj ? "" : obj.toString();
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        Object obj = request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
        return null == obj ? "" : obj.toString();
    }
}
