package com.ipanel.web.app.cv.comfig;

import com.ipanel.web.app.cv.interceptor.MyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author zhaolei
 * Create: 2018/12/19 09:15
 * Modified By:
 * Description:web过滤去配置
 */
public class MyWebAppConfig extends WebMvcConfigurationSupport {
    @Bean
    MyInterceptor localInterceptor() {
        return new MyInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localInterceptor())
                //.addPathPatterns("/*/*")
                .excludePathPatterns("/*/*")
                .excludePathPatterns("/managerInfo/login")//登录接口放行
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");//swagger页面放行
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
