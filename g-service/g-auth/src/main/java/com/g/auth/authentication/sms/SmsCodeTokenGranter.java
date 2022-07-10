package com.g.auth.authentication.sms;

import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: Gtf
 * @Date: 2022/5/24-05-24-16:39
 * @Description: 手机验证码模式的授权模块，仿制密码模式 ResourceOwnerPasswordTokenGranter实现
 * @see org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter
 * @Version: 1.0
 */
public class SmsCodeTokenGranter extends AbstractTokenGranter {
    /**
     * 需要前端提前调用验证码服务
     */
    // 需要匹配 GRANT_TYPE 字段才能使用该授权模块
    private static final String GRANT_TYPE = "sms_code";
    private final AuthenticationManager authenticationManager;

    public SmsCodeTokenGranter(
            AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices,
            ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        this(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
    }

    protected SmsCodeTokenGranter(
            AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices,
            ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
        this.authenticationManager = authenticationManager;
    }

    /**
     * OAuth2Authentication的签发
     * @param client
     * @param tokenRequest
     * @return
     */
    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap(tokenRequest.getRequestParameters());
        String phone = parameters.get("phone");
        String code = parameters.get("code");
        // 将验证码code从Details中移除，防止验证码泄漏
        parameters.remove("code");
        // 组装sms验证码模式的认证信息
        Authentication userAuth = new SmsCodeAuthenticationToken(phone, code);
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
        try {
            // authenticationManager 的实现 ProviderManager通过轮询的方式匹配到 SmsCodeAuthenticationProvider
            userAuth = authenticationManager.authenticate(userAuth);
        }
        catch (AccountStatusException ase) {
            throw new InvalidGrantException(ase.getMessage());
        }
        catch (BadCredentialsException e) {
            throw new InvalidGrantException(e.getMessage());
        }
        if (userAuth == null || !userAuth.isAuthenticated()) {
            throw new InvalidGrantException("Could not authenticate user: " + phone);
        }
        // OAuth2Request，包含client相关信息
        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        // 返回OAuth2认证
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }
}
