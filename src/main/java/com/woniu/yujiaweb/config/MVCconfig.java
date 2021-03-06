package com.woniu.yujiaweb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCconfig implements WebMvcConfigurer {


    @Override
    //解决跨域问题
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("正在解决跨域问题");
        registry.addMapping("/**")
                //设置可以访问的路径
                .allowedOrigins("*")
                //设置可以访问的源
                .allowedHeaders("*")
                //设置那些请求头可以发送跨域请求
                .allowCredentials(true)
                //是否允许ajax附带cookie
                .allowedMethods("*")
                //允许的请求方式
                .maxAge(3600);
                //设置重发跨域请求的最大时长
    }
}
