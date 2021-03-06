package com.woniu.yujiaweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2//开启swagger
public class SwaggerConfig {
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                //扫描接口的包
                .apis(RequestHandlerSelectors.basePackage("com.woniu.yujiaweb.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfoBuilder()
                        //标题
                        .title("瑜伽web")
                        //描述
                        .description("线上瑜伽馆")
                        //版本
                        .version("1.0")
                        //联系人信息
                        .contact(new Contact("49期第八组","http://www.baidu.com","@qq.com"))
                        //项目主页
                        .license("项目主页")
                        .licenseUrl("http://www.baidu.com")
                        .build());
    }

}
