package com.g.commons.base.constant;


/**
 * @Author: Gtf
 * @Date: 2022/5/24-05-24-23:59
 * @Description: com.g.commons.base.constant
 * @Version: 1.0
 */
public class SecurityConstant {
    public static final String ACCOUNT_NOT_FOUND = "用户不存在!";
    public static final String ACCOUNT_Disabled = "用户已被禁用!";
    public static final String ACCOUNT_Locked = "用户已被锁定!";
    public static final String ACCOUNT_Expired = "用户已过期!";

    /**
     * 微服务内部安全
     */
    public static final String PV_ACCESS_KEY = "pak";
    public static final String PV_ACCESS_VALUE = "pav";

    /**
     * securityContextHolder
     */
    public static final String JWT_EXPIRE = "jwt_e";
    public static final Object JWT_BLACKLIST = null;

    /**
     * jwt payload key
     */
    //自己的oid
    public static final String OPEN_ID = "od";
    public static final String CLIENT_ID = "client_id";
    public static final String LOGIN_TYPE = "l_type";
    public static final String USER_NAME = "user_name";
    public static final String authorities = "authorities";
    //谁发放的令牌
    public static final String AUD = "aud";
    public static final String SCOPE = "scope";
    public static final String EXP = "exp";
    public static final String JTI = "jti";


    public static final String REFRESH_CONDITION = "Jwt expired at";
    public static final String LOCAL_CLIENT = "c1";
    public static final String CLIENT_SECRET = "client_secret";


    public static final String GRANT_TYPE = "grant_type";
    public static final String TYPE_REFRESH_TOKEN = "refresh_token";
    public static final String TYPE_AUTHORIZATION_CODE = "authorization_code";
    public static final String TYPE_PASSWORD = "password";
    public static final String TYPE_CLIENT_CREDENTIALS = "client_credentials";


    public static final String AUTHORIZATION = "Authorization";


    /**
     * openId
     */
    public static final String TOKEN_ENDPOINT = "token_endpoint";
    public static final String TOKEN_ENDPOINT_AUTH_METHODS_SUPPORTED = "token_endpoint_auth_methods_supported";
    public static final String JWKS_URI = "jwks_uri";
    public static final String ISSUER = "issuer";
    public static final String USERINFO_ENDPOINT = "userinfo_endpoint";
    public static final String RESPONSE_TYPES_SUPPORTED = "response_types_supported";
    public static final String CLAIMS_SUPPORTED = "claims_supported";

}