package com.g.auth.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.g.auth.authentication.service.UserDetailsServiceImpl;
import com.g.auth.authentication.sms.SmsCodeAuthenticationProvider;
import com.g.auth.authentication.wechat.WeChatAuthenticationProvider;
import com.g.feign.api.UserClient;
import lombok.RequiredArgsConstructor;
import net.oschina.j2cache.CacheChannel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author: Gtf
 * @Date: 2022/5/23-05-23-18:42
 * @Description: com.g.auth.config
 * @Version: 1.0
 */
@Configuration
@RequiredArgsConstructor
public class ProviderConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserClient userClient;
    private final CacheChannel cacheChannel;
    private final WxMaService wxMaService;


    /**
     * 账户密码验证方式的提供者
     * @return
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }
    /**
     * 微信登录验证方式的提供者
     * @return
     */
    @Bean
    public WeChatAuthenticationProvider weChatAuthenticationProvider(){
        WeChatAuthenticationProvider provider = new WeChatAuthenticationProvider();
        provider.setUserDetailsService((UserDetailsServiceImpl) userDetailsService);
        provider.setWxMaService(wxMaService);
        provider.setUserClient(userClient);
        return provider;
    }
    /**
     * 手机登录验证方式的提供者
     * @return
     */
    @Bean
    public SmsCodeAuthenticationProvider smsCodeAuthenticationProvider(){
        SmsCodeAuthenticationProvider provider = new SmsCodeAuthenticationProvider();
        provider.setCacheChannel(cacheChannel);
        provider.setUserClient(userClient);
        provider.setUserDetailsService((UserDetailsServiceImpl) userDetailsService);
        return provider;
    }

}
