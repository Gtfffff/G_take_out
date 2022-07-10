package com.g.oauth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Gtf
 * @Date: 2022/6/17-06-17-23:47
 * @Description: com.g.oauth.properties
 * @Version: 1.0
 */
@ConfigurationProperties(prefix = "spring.security.openid")
@Component
@Data
public class OpenIdConfigProperties {
    /**
     * 默认属性
     */
    private String token_endpoint = "http://localhost:10020/oauth/token";
    private List<String> token_endpoint_auth_methods_supported = new ArrayList<>();
    private String jwks_uri = "http://localhost:10020/oauth/token";
    private String issuer = "http://localhost:10020";
    private String userinfo_endpoint = "http://localhost:10020";
    private List<String> response_types_supported = new ArrayList<>();
    private List<String> claims_supported = new ArrayList<>();


    /**
     * 初始化默认值
     */
    {
        token_endpoint_auth_methods_supported.add("sms_code");
        token_endpoint_auth_methods_supported.add("wechat");
        token_endpoint_auth_methods_supported.add("authorization_code");
        token_endpoint_auth_methods_supported.add("password");
        token_endpoint_auth_methods_supported.add("client_credentials");
        token_endpoint_auth_methods_supported.add("implicit");
        token_endpoint_auth_methods_supported.add("refresh_token");

        response_types_supported.add("code");
        response_types_supported.add("token");


        claims_supported.add("od");
        claims_supported.add("client_id");
        claims_supported.add("l_type");
        claims_supported.add("user_name");
        claims_supported.add("authorities");
        claims_supported.add("aud");
        claims_supported.add("scope");
        claims_supported.add("exp");
        claims_supported.add("jti");
    }
}
