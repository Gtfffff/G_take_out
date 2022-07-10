package com.g.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author: Gtf
 * @Date: 2022/6/22-06-22-0:10
 * @Description: com.g.admin.config
 * @Version: 1.0
 */
@Configuration
public class EncodeConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
