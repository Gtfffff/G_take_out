package com.g.security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Author: Gtf
 * @Date: 2022/6/10-06-10-19:24
 * @Description: com.g.security.config
 * @Version: 1.0
 */
@Configuration
public class DefaultConfig {

    @ConditionalOnProperty(name = "security",havingValue = "disable")
    @Bean(name = "defaultResourceServerConfig")
    public WebSecurityConfigurerAdapter defaultResourceServerConfig(){
        return new WebSecurityConfigurerAdapter(){
            @Override
            protected void configure(HttpSecurity http) throws Exception {
                http
                        .csrf().disable()
                        .authorizeRequests(req -> req.anyRequest().permitAll());
            }
        };
    }
}
