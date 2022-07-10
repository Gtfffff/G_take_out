package com.g.auth.config;


import com.g.auth.authentication.service.PreAuthenticatedUserDetailsService;
import com.g.auth.authentication.service.UserDetailsServiceImpl;
import com.g.auth.authentication.token.MyJwtAccessTokenConverter;
import com.g.auth.authentication.token.MyTokenServices;
import com.g.commons.base.entity.vo.result.ResultCode;
import com.g.commons.utils.ResponseUtils;
import com.g.oauth.properties.OauthConfigProperties;
import lombok.RequiredArgsConstructor;
import net.oschina.j2cache.CacheChannel;
import org.springframework.cloud.bootstrap.encrypt.KeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.util.Arrays;

/**
 * @Author: Gtf
 * @Date: 2022/5/1-05-01-21:53
 * @Description: 各种细节配置Bean的提供
 * @Version: 1.0
 */
@Configuration
@RequiredArgsConstructor
public class DetailsConfig {

    private final DataSource dataSource;

    private final KeyProperties keyProperties;

    private final UserDetailsServiceImpl userDetailsService;

    private final OauthConfigProperties oauthConfigProperties;

    private final CacheChannel cacheChannel;
//    @Setter
//    private int accessTokenValiditySeconds;
//
//    private int refreshTokenValiditySeconds;

    /**
     * BC编码器的提供
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 钥匙对的提供
     * @return
     */
    @Bean
    public KeyPair keyPair() {
        //从Cloud提供的keyProperties中读取密钥文件
        KeyProperties.KeyStore keyStore = keyProperties.getKeyStore();
        //转化为KeyPair
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                keyStore.getLocation(), keyStore.getPassword().toCharArray()
        );
        return keyStoreKeyFactory.getKeyPair(keyStore.getAlias());
    }

    /**
     * token存储方案的提供
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(this.accessTokenConverter());
    }

    /**
     * 令牌增强类
     * @return
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        MyJwtAccessTokenConverter converter = new MyJwtAccessTokenConverter();
        converter.setKeyPair(this.keyPair());
        return converter;
    }

    /**
     * 授权码服务，包含授权码的校验和存储
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * 自定义客户端类型
     * @param
     * @return
     */
    @Bean
    public ClientDetailsService clientDetailsService() {
        JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        clientDetailsService.setPasswordEncoder(this.passwordEncoder());
        return clientDetailsService;
    }


    /**
     * 配置对应的令牌管理
     * @return
     */
    @Bean
    public AuthorizationServerTokenServices tokenService() {
        //使用自定义的令牌管理服务
//        DefaultTokenServices service = new DefaultTokenServices();
        MyTokenServices service = new MyTokenServices();
        //设置缓存
        service.setCacheChannel(cacheChannel);
        //设置对应的客户端服务
        service.setClientDetailsService(this.clientDetailsService());
        //是否设置刷新令牌
        service.setSupportRefreshToken(true);
        //令牌管理方式
        service.setTokenStore(this.tokenStore());
        //令牌默认有效期,想要生效想要将数据库client的有效时间删除
//        service.setAccessTokenValiditySeconds(7200);
        service.setAccessTokenValiditySeconds(oauthConfigProperties.getAccessTokenValiditySeconds());
        //刷新默认令牌有效期
//        service.setRefreshTokenValiditySeconds(259200);
        service.setRefreshTokenValiditySeconds(oauthConfigProperties.getRefreshTokenValiditySeconds());
        //添加令牌增强类链
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(this.accessTokenConverter()));
        service.setTokenEnhancer(tokenEnhancerChain);
        //添加刷新token策略
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(new PreAuthenticatedUserDetailsService<>(userDetailsService));
        service.setAuthenticationManager(new ProviderManager(Arrays.asList(provider)));
        return service;
    }
    /**
     * 默认的权限异常处理
     * @return
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, e) -> {
            System.out.println(e);
            ResponseUtils.sendResultAboutSecurityE(response,ResultCode.UN_AUTHORIZED);
        };
    }


    /**
     * 默认的认证异常处理
     * @return
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, e) -> {
            System.out.println(e);
            ResponseUtils.sendResultAboutSecurityE(response, ResultCode.UN_AUTHENTICATION);
        };
    }

    /**
     * 角色继承类
     * @return
     */
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        //amdin拥有user的权限
        hierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return hierarchy;
    }


}
