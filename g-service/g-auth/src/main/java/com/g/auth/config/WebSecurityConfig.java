package com.g.auth.config;

import com.g.auth.authentication.sms.SmsCodeAuthenticationProvider;
import com.g.auth.authentication.wechat.WeChatAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * @Author: Gtf
 * @Date: 2022/4/25-04-25-22:54
 * @Description: 配置资源的安全约束
 * @Version: 1.0
 */
@Order(99)
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final WeChatAuthenticationProvider weChatAuthenticationProvider;
    private final SmsCodeAuthenticationProvider smsCodeAuthenticationProvider;


    /**
     * 暴露认证管理器
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 认证管理器的配置，采用多provider的方式
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(daoAuthenticationProvider)
                .authenticationProvider(smsCodeAuthenticationProvider)
                .authenticationProvider(weChatAuthenticationProvider);
    }

    /**
     * 安全配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests(req -> req
                        .antMatchers("/oauth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exc -> exc
                        .accessDeniedHandler(accessDeniedHandler)
                )
                //身份认证模块，提供两种方式
                .httpBasic(
                        basic -> basic.authenticationEntryPoint(authenticationEntryPoint)
                )
                .formLogin(
//                        form -> form.loginProcessingUrl("/auth/login")
                )
        ;
    }


}
