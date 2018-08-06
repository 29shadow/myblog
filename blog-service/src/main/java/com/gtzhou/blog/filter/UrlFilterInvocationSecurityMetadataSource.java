package com.gtzhou.blog.filter;

import com.gtzhou.blog.domain.Menu;
import com.gtzhou.blog.domain.Role;
import com.gtzhou.blog.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Description:自定义url过滤，过滤出当前url需要的用户角色
 * User: ZGW
 * Date: 2018/6/30
 */
@Slf4j
@Component
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private MenuService menuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String reqUrl = ((FilterInvocation)object).getRequestUrl();
        if (Objects.equals(reqUrl,"/login")){
            return null;
        }
        List<Menu> menus = menuService.getAllMenus();
        for (Menu m:menus){
            if (antPathMatcher.match(m.getUrl(),reqUrl) && !CollectionUtils.isEmpty(m.getRoles())){
                List<Role> roles = m.getRoles();
                int size = roles.size();
                String[] values = new String[size];
                for (int i=0 ;i<size;i++){
                    values[i] = roles.get(i).getRoleName();
                }
                return SecurityConfig.createList(values);
            }
        }
//        return SecurityConfig.createList("ROLE_LOGIN");
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
