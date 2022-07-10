package com.g.auth.authentication.wechat;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.util.ObjectUtil;
import com.g.auth.authentication.sms.SmsCodeAuthenticationToken;
import com.g.commons.utils.EncryptAlgorithm;
import lombok.Setter;
import lombok.SneakyThrows;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import redis.clients.jedis.args.Rawable;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: Gtf
 * @Date: 2022/5/24-05-24-16:39
 * @Description: 微信验证码模式的授权模块，仿制密码模式 ResourceOwnerPasswordTokenGranter实现
 * @see org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter
 * @Version: 1.0
 */
public class WeChatTokenGranter extends AbstractTokenGranter {
    /**
     *  需要前端提前调用 wx.login()和 wx.getUserInfo 分别获取code值和iv、rawData、signature、encryptedData等加密数据
     */
    // 需要匹配 GRANT_TYPE 字段才能使用该授权模块
    private static final String GRANT_TYPE = "wechat";
    private final AuthenticationManager authenticationManager;

    public WeChatTokenGranter(
            AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices,
            ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        this(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
    }

    protected WeChatTokenGranter(
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
    @SneakyThrows
    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap(tokenRequest.getRequestParameters());
        // 获取信息
        String code = parameters.get("code");
        String rawDate = parameters.get("rawDate");
        String signature = parameters.get("signature");
        String encryptedData = parameters.get("encryptedData");
        String iv = parameters.get("iv");

        // 移除信息
        parameters.remove("code");
        parameters.remove("rawDate");
        parameters.remove("signature");
        parameters.remove("encryptedData");
        parameters.remove("iv");
        // 组装微信模式的认证信息
        Authentication userAuth = new WeChatAuthenticationToken(code,rawDate,signature,encryptedData,iv);
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
        try {
            // authenticationManager 的实现 ProviderManager通过轮询的方式匹配到 WeChatAuthenticationToken
            userAuth = authenticationManager.authenticate(userAuth);
        }
        catch (AccountStatusException ase) {
            throw new InvalidGrantException(ase.getMessage());
        }
        catch (BadCredentialsException e) {
            throw new InvalidGrantException(e.getMessage());
        }
        if (userAuth == null || !userAuth.isAuthenticated()) {
            throw new InvalidGrantException("Could not authenticate user: " + code);
        }
        // OAuth2Request，包含client相关信息
        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        // 返回OAuth2认证
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }

}
