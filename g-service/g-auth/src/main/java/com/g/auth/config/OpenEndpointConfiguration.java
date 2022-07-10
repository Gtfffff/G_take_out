package com.g.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerSecurityConfiguration;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;

/**
 * @Author: Gtf
 * @Date: 2022/5/6-05-06-22:48
 * @Description: 对jwks端点的安全配置
 * @Version: 1.0
 */
@Order(1)
@Configuration
public class OpenEndpointConfiguration extends AuthorizationServerSecurityConfiguration {


    /**
     * 配置JwkSetEndpoint的安全
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .requestMatchers(req -> req.mvcMatchers(
                        "/pb/.well-known/openid-configuration/**",
                        "/pb/auth/**")
                )
                .authorizeRequests(req -> req.mvcMatchers(
                        "/pb/.well-known/openid-configuration/**",
                        "/pb/auth/**"
                ).permitAll());
    }
}
