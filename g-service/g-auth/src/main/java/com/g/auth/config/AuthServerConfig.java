package com.g.auth.config;

import com.g.auth.authentication.sms.SmsCodeTokenGranter;
import com.g.auth.authentication.wechat.WeChatTokenGranter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: Gtf
 * @Date: 2022/4/25-04-25-17:43
 * @Description: 配置授权服务的相关参数
 * @Version: 1.0
 */
@Order(3)
@EnableAuthorizationServer
@RequiredArgsConstructor
@Configuration
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    //客户端服务
    private final ClientDetailsService clientDetailsService;
    //授权码服务
    private final AuthorizationCodeServices authorizationCodeServices;
    //认证管理器
    private final AuthenticationManager authenticationManager;
    //令牌管理服务
    @Qualifier("tokenService")
    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;



    /**
     * 配置客户端信息
     * @param clients
     * @throws Exception
     */
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    /**
     * 配置认证服务终端
     * @param endpoints
     * @throws Exception
     */
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenGranter tokenGranter = getTokenGranter(endpoints);
        endpoints
                //账户密码获取模式
                .authenticationManager(authenticationManager)
                //授权码服务，包含校验存储
                .authorizationCodeServices(authorizationCodeServices)
                //使用的令牌管理服务
                .tokenServices(authorizationServerTokenServices)
                //使用的授权逻辑集合
                .tokenGranter(tokenGranter)
                //允许post提交
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }


    /**
     * 配置终端的安全约束
     * @param security
     * @throws Exception
     */
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                //访问获取token的密钥的url
                .tokenKeyAccess("permitAll()")
                //访问验证token的url
                .checkTokenAccess("permitAll()")
                //允许表单验证
                .allowFormAuthenticationForClients();
    }

    /**
     * 将自建的授权逻辑添加进去
     * @see AuthorizationServerEndpointsConfigurer# tokenGranter()
     * @see AuthorizationServerEndpointsConfigurer#tokenGranter(TokenGranter)
     * @param endpoints
     * @return
     */
    private TokenGranter getTokenGranter(AuthorizationServerEndpointsConfigurer endpoints){
        // 获取原有默认授权模式(授权码模式、密码模式、客户端模式、简化模式)的授权者
        List<TokenGranter> granterList = new ArrayList<>(Arrays.asList(endpoints.getTokenGranter()));

        // 添加手机短信验证码授权模式的授权者
        granterList.add(new SmsCodeTokenGranter(authenticationManager, endpoints.getTokenServices(),
                endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()
                ));

        // 添加微信授权模式的授权者
        granterList.add(new WeChatTokenGranter(authenticationManager, endpoints.getTokenServices(),
                endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()
                ));
        return new CompositeTokenGranter(granterList);
    }
}
