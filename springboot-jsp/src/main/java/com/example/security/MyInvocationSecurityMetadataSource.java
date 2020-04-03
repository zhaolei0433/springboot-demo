package com.example.security;

import com.example.business.sysUser.service.ISysUserService;
import com.example.entity.PermissionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

/**
 * @author zhaolei
 * Create: 2019/11/20 14:08
 * Modified By:
 * Description: 调用安全策略的元数据源
 */
@Service
public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource{

    private static final Logger LOGGER = LoggerFactory.getLogger(MyInvocationSecurityMetadataSource.class);


    @Autowired
    private ISysUserService sysUserService;

    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;


    public MyInvocationSecurityMetadataSource() {

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<>();
        for (Map.Entry<String, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
            allAttributes.addAll(entry.getValue());
        }
        return allAttributes;
    }

    /**
     * 参数是要访问的url，返回这个url对于的所有权限（或角色） 每次请求后台就会调用得到请求所拥有的权限
     * 这个方法在url请求时才会调用，服务器启动时不会执行这个方法
     * getAttributes这个方法会根据你的请求路径去获取这个路径应该是有哪些权限才可以去访问。
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) object;
        if (resourceMap == null) {
            try {
                loadResourceDefine();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Collection<ConfigAttribute> roleList = new LinkedList<>();
        for (String url : resourceMap.keySet()) {
            RequestMatcher requestMatcher = new AntPathRequestMatcher(url);
            if (requestMatcher.matches(filterInvocation.getHttpRequest())) {
                roleList = resourceMap.get(url);
                LOGGER.info("*****access url:{} need role,role.size:{},role.name:{}", url,
                        roleList.size(),roleList.toArray()[0].toString());
                return roleList;
            }
        }
        //避免返回null，否则将不进入AccessDecisionManager中进行权限判断
        //ConfigAttribute configAttribute = new SecurityConfig("超级管理员");
        //roleList.add(configAttribute);
        //return roleList;
        //返回为null表示这个url不需要任何角色就能访问
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        // 要返回true，否则要报异常(SecurityMetadataSource does not support secure object
        // class: class)
        return true;
    }

    /**
     * 重新加载资讯
     */
    public static void reLoadResource() {
        try {
            new MyInvocationSecurityMetadataSource().loadResourceDefine();
        } catch (Exception e) {
            LOGGER.info("***reLoadResource throw exception:", e);
        }
    }

    /**
     * 形成以URL为key，权限列表未value的map时，要注意key和value的对应性，避免value的不正确对应形成重复，这样会导致
     * 没有权限的人也能访问到不该访问到的资源 这里是每个url对应了那些角色
     * 被@PostConstruct修饰的方法会在服务器加载Servle的时候运行，
     * 并且只会被服务器执行一次。PostConstruct在构造函数之后执行,init()方法之前执行 在Web服务器启动时，提取系统中的所有权限
     *
     * @throws Exception
     */
    @PostConstruct
    private void loadResourceDefine() throws Exception {
        if (sysUserService == null)
            return;
        List<PermissionInfo> permissionInfoList = sysUserService.queryAllData();
        LOGGER.info("**************permissionInfoList.size:{}", permissionInfoList.size());
        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

        for (PermissionInfo data : permissionInfoList) {
            String url = data.getUrl();
            Integer id = data.getId();
            List<String> roelNames = sysUserService.getRoleNameByPermissionInfoId(id);
            LOGGER.info("****id:{}", id);
            if (url == null || url.equals(""))
                continue;
            String[] urlArray = url.split(",");
            for (String item : urlArray) {
                Collection<ConfigAttribute> atts = resourceMap.get(item);
                if (atts == null)
                    atts = new ArrayList<ConfigAttribute>();
                for (String roleName : roelNames) {
                    ConfigAttribute ca = new SecurityConfig(roleName);
                    atts.add(ca);
                    LOGGER.info("****url:{},roleName:{}", item, roleName);
                }
                resourceMap.put(item, atts);
            }
        }

        // //打印查看权限与资源的对应关系
        LOGGER.info("资源（URL）数量：{}", resourceMap.size());
        Set<String> keys = resourceMap.keySet();
        for (String key : keys) {
            LOGGER.info("**url:{},role:{}", key, resourceMap.get(key));
        }
    }
}
