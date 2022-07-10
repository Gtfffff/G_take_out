package com.g.auth.authentication.token;


import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

///**
// * @Author: Gtf
// * @Date: 2022/5/27-05-27-22:12
// * @Description: 用于使用 OpenId 体系的第三方登录 Token 的抽象类
// * @Version: 1.0
// */
//public abstract class OpenIdToken extends AbstractAuthenticationToken {
//
//
//
//    public OpenIdToken(Collection<? extends GrantedAuthority> authorities) {
//        super(authorities);
//    }
//}
