package com.g.session.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * @Author: Gtf
 * @Date: 2022/5/31-05-31-22:51
 * @Description: com.g.security.config
 * @Version: 1.0
 */
@EnableRedisHttpSession
@Configuration
public class HttpSessionConfig {
    /**
     * cookie序列化器
     * @return
     */
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("SESSION");
        serializer.setCookiePath("/");
        // 设置cookie的作用域为父域名
        serializer.setDomainName("localhost");
        return serializer;
    }
//    @Bean
//    public RedisSerializer<Object> springSessionDefaultRedisSerializer(){
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModules(SecurityJackson2Modules.getModules(getClass().getClassLoader()));
//        return new GenericJackson2JsonRedisSerializer(objectMapper);
//    }
//    @Bean
//    @Primary
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory, ObjectMapper objectMapper) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setKeySerializer(RedisSerializer.string());
//        redisTemplate.setHashKeySerializer(RedisSerializer.string());
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
//        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//
//        return redisTemplate;
//    }

}
