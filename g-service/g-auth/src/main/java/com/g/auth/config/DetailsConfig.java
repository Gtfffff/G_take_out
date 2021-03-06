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
 * @Description: ??????????????????Bean?????????
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
     * BC??????????????????
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * ??????????????????
     * @return
     */
    @Bean
    public KeyPair keyPair() {
        //???Cloud?????????keyProperties?????????????????????
        KeyProperties.KeyStore keyStore = keyProperties.getKeyStore();
        //?????????KeyPair
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                keyStore.getLocation(), keyStore.getPassword().toCharArray()
        );
        return keyStoreKeyFactory.getKeyPair(keyStore.getAlias());
    }

    /**
     * token?????????????????????
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(this.accessTokenConverter());
    }

    /**
     * ???????????????
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
     * ???????????????????????????????????????????????????
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * ????????????????????????
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
     * ???????????????????????????
     * @return
     */
    @Bean
    public AuthorizationServerTokenServices tokenService() {
        //????????????????????????????????????
//        DefaultTokenServices service = new DefaultTokenServices();
        MyTokenServices service = new MyTokenServices();
        //????????????
        service.setCacheChannel(cacheChannel);
        //??????????????????????????????
        service.setClientDetailsService(this.clientDetailsService());
        //????????????????????????
        service.setSupportRefreshToken(true);
        //??????????????????
        service.setTokenStore(this.tokenStore());
        //?????????????????????,??????????????????????????????client?????????????????????
//        service.setAccessTokenValiditySeconds(7200);
        service.setAccessTokenValiditySeconds(oauthConfigProperties.getAccessTokenValiditySeconds());
        //???????????????????????????
//        service.setRefreshTokenValiditySeconds(259200);
        service.setRefreshTokenValiditySeconds(oauthConfigProperties.getRefreshTokenValiditySeconds());
        //????????????????????????
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(this.accessTokenConverter()));
        service.setTokenEnhancer(tokenEnhancerChain);
        //????????????token??????
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(new PreAuthenticatedUserDetailsService<>(userDetailsService));
        service.setAuthenticationManager(new ProviderManager(Arrays.asList(provider)));
        return service;
    }
    /**
     * ???????????????????????????
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
     * ???????????????????????????
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
     * ???????????????
     * @return
     */
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        //amdin??????user?????????
        hierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return hierarchy;
    }


}
