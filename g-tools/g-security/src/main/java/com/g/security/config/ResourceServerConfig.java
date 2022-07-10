package com.g.security.config;

import com.g.oauth.properties.OauthConfigProperties;
import com.g.security.filter.FeignClientFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;


/**
 * @Author: Gtf
 * @Date: 2022/4/28-04-28-14:56
 * @Description: 网关资源服务器的统一配置
 * @Version: 1.0
 */
@ConditionalOnProperty(name = "security",havingValue = "enable")
@RequiredArgsConstructor
@Configuration
@Order(4)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    private final Converter<Jwt,AbstractAuthenticationToken> converter;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;
    private final FeignClientFilter feignClientFilter;
    private final OauthConfigProperties oauthConfigProperties;
//    @Setter
//    private String jwkSetUri;
//    @Setter
//    private List<String> ignoreUrls;


    /**
     * 资源服务的配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        this.defaultConfig(http)
                .oauth2ResourceServer(
                        server -> server
                                //使用jwt方式验证
                                .jwt()
                                //设置jwks的获取路径
                                .jwkSetUri(oauthConfigProperties.getJwkSetUri())
                                //加入jwt增强类
                                .jwtAuthenticationConverter(converter)
                                //自定义鉴权管理器
//                                .authenticationManager()
                        .and()
                                //自定义认证失败返回操作
                                .authenticationEntryPoint(authenticationEntryPoint)
                                //自定义无权限返回操作
                                .accessDeniedHandler(accessDeniedHandler)
//                                //自定义Token解析器
//                                .bearerTokenResolver()

                );
    }
    /**
     * 其他配置
     * @param http
     * @return
     * @throws Exception
     */
    public HttpSecurity defaultConfig(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                //基于拦截器的鉴权实现
//                .authorizeRequests(req -> req
//                        .antMatchers("/addressBook/test3").access("hasRole('admin')")
//                        .antMatchers(Convert.toStrArray(oauthConfigProperties.getIgnoreUrls())).permitAll()
//                        .anyRequest().authenticated()
//                )
                .addFilterBefore(
                        feignClientFilter, SecurityContextPersistenceFilter.class
                );
    }


}
