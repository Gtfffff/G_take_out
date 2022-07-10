package com.g.oauth.properties;

import lombok.Data;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.List;

/**
 * @Author: Gtf
 * @Date: 2022/6/14-06-14-22:38
 * @Description: 通用的 oauth 配置
 * @Version: 1.0
 */
@ConfigurationProperties(prefix = "spring.security.oauth2")
@Component
@Data
public class OauthConfigProperties {

    private String jwkSetUri = "http://localhost:10020/pb/.well-known/openid-configuration/jwks.json";
    private List<String> ignoreUrls;
    private String loginFormUrl = "/login";
    private String clientSecret = "secret";
    private int accessTokenValiditySeconds = 120;
    private int refreshTokenValiditySeconds = 259200;
}
