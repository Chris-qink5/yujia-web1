package com.woniu.yujiaweb.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.woniu.yujiaweb.component.CustomerRealm;
import com.woniu.yujiaweb.filter.JwtFilter;
import com.woniu.yujiaweb.util.JWTUtil;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public Realm realm(){
        CustomerRealm customerRealm = new CustomerRealm();
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(2048);
        customerRealm.setCredentialsMatcher(matcher);
        return customerRealm;
    }
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm());
        return securityManager;
    }
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        System.out.println("进入过滤器");
        JWTUtil.setUsernames(new HashSet<String>());
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager());
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("jwt",new JwtFilter());
        LinkedHashMap<String,String> map=new LinkedHashMap<>();
        //设置白名单
        map.put("/user/login","anon");
        map.put("/**","jwt");
        //设置黑名单

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }

   @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
   }


}
