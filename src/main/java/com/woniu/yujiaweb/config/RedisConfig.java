package com.woniu.yujiaweb.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.woniu.yujiaweb.domain.CMessage;
import com.woniu.yujiaweb.domain.Course;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.Charset;

@Configuration
public class RedisConfig {
    @Bean
    @Scope("singleton")
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> stringObjectRedisTemplate = new RedisTemplate<>();
        stringObjectRedisTemplate.setConnectionFactory(redisConnectionFactory);
        //key序列化为string
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer(Charset.forName("utf-8"));
        stringObjectRedisTemplate.setKeySerializer(stringRedisSerializer);
        stringObjectRedisTemplate.setHashKeySerializer(stringRedisSerializer);
        //value序列化为JSON
        Jackson2JsonRedisSerializer<Object> objectJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        stringObjectRedisTemplate.setValueSerializer(objectJackson2JsonRedisSerializer);
        stringObjectRedisTemplate.setHashValueSerializer(stringRedisSerializer);
        return stringObjectRedisTemplate;
    }
    @Bean
    public CMessage getCMessage(){
        return new CMessage();
    }
    @Bean
    public Course getCourse(){
        return new Course();
    }
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }
}

