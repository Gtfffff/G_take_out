package com.g.gateway.security.config;

//import org.checkerframework.checker.units.qual.h;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
///**
// * @Author: Gtf
// * @Date: 2022/5/19-05-19-22:33
// * @Description: com.g.gateway.config
// * @Version: 1.0
// */
//@Configuration
//@EnableWebSecurity
//public class ClientServerConfiguration extends WebSecurityConfigurerAdapter {
//
//    /**
//     * 客户端配置
//     * @param http
//     * @throws Exception
//     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        this
//                .defaultConfigure(http)
//                .oauth2Login(Customizer.withDefaults())
//                .oauth2Client();
//    }
//
//    /**
//     * 其他配置
//     * @param http
//     * @return
//     * @throws Exception
//     */
//    public HttpSecurity defaultConfigure(HttpSecurity http) throws Exception{
//        http
//                .authorizeRequests(req -> req
//                        .anyRequest().permitAll()
//                );
//        return http;
//    }
//}
