package com.woniu.yujiaweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.woniu.yujiaweb.mapper")
public class YujiaWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(YujiaWebApplication.class, args);
    }

}
