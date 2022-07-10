package com.g.auth.endpoint;


import com.g.commons.base.constant.SecurityConstant;
import com.g.oauth.properties.OpenIdConfigProperties;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * @Author: Gtf
 * @Date: 2022/5/6-05-06-1:21
 * @Description: Jwk的接入端点
 * @Version: 1.0
 */
@AllArgsConstructor
@FrameworkEndpoint
@RequestMapping("pb/.well-known/openid-configuration")
//@Api(tags = "获取公开配置的接口")
public class OpenEndpoint {

    private final KeyPair keyPair;
    @Setter
    private OpenIdConfigProperties openIdConfigProperties;

    /**
     * 返回 openid-configuration，格式仿造微软
     * @return
     */
//    @ApiOperation(value = "获取公开信息的获取路径及支持类型")
    @GetMapping()
    @ResponseBody
    public MultiValueMap<String,Object> getOpenConfiguration(){
        MultiValueMap<String,Object> configuration = new LinkedMultiValueMap<>();
         configuration.add(SecurityConstant.TOKEN_ENDPOINT,openIdConfigProperties.getToken_endpoint());
         configuration.add(SecurityConstant.TOKEN_ENDPOINT_AUTH_METHODS_SUPPORTED,openIdConfigProperties.getToken_endpoint_auth_methods_supported());
         configuration.add(SecurityConstant.JWKS_URI,openIdConfigProperties.getJwks_uri());
         configuration.add(SecurityConstant.ISSUER,openIdConfigProperties.getIssuer());
         configuration.add(SecurityConstant.USERINFO_ENDPOINT,openIdConfigProperties.getUserinfo_endpoint());
         configuration.add(SecurityConstant.RESPONSE_TYPES_SUPPORTED,openIdConfigProperties.getResponse_types_supported());
         configuration.add(SecurityConstant.CLAIMS_SUPPORTED,openIdConfigProperties.getClaims_supported());
        return configuration;
    }


    /**
     * 返回 openid 和 对应的可公开信息加密文件
     * @return
     */
    @GetMapping("/me")
    @ResponseBody
    public Object getOpenId(@RequestParam Map<String, String> parameters){
        return null;
    }



    /**
     * 提供公钥集合
     * @return
     */
//    @ApiOperation(value = "获取公钥")
    @GetMapping("/jwks.json")
    @ResponseBody
    public Map<String,Object> getKeySet() {
        //取出要返回的公钥
        RSAPublicKey pubKey = (RSAPublicKey)keyPair.getPublic();
        //转换nimbusds的公钥
        RSAKey key = new RSAKey.Builder(pubKey).build();
        //返回存有公钥的集合
        return new JWKSet(key).toJSONObject();
    }
}
