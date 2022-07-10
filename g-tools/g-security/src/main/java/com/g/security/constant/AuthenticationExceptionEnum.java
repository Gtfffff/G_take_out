package com.g.security.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;

/**
 * @Author: Gtf
 * @Date: 2022/5/17-05-17-19:54
 * @Description: com.g.security.constant
 * @Version: 1.0
 */
@AllArgsConstructor
@Getter
public enum AuthenticationExceptionEnum {

    InsufficientAuthenticationException,
    InvalidBearerTokenException;

}
