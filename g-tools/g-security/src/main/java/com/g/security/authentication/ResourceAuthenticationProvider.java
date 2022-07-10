package com.g.security.authentication;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * @Author: Gtf
 * @Date: 2022/5/29-05-29-19:58
 * @Description: com.g.security.authentication
 * @see org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider
 * @Version: 1.0
 */
//public class ResourceAuthenticationProvider implements AuthenticationProvider {
//    private final Log logger = LogFactory.getLog(getClass());
//
//    private final JwtDecoder jwtDecoder;
//
//    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter = new JwtAuthenticationConverter();
//
//    public ResourceAuthenticationProvider(JwtDecoder jwtDecoder) {
//        Assert.notNull(jwtDecoder, "jwtDecoder cannot be null");
//        this.jwtDecoder = jwtDecoder;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        BearerTokenAuthenticationToken bearer = (BearerTokenAuthenticationToken) authentication;
//        Jwt jwt = getJwt(bearer);
//        AbstractAuthenticationToken token = this.jwtAuthenticationConverter.convert(jwt);
//        token.setDetails(bearer.getDetails());
//        this.logger.debug("Authenticated token");
//        return token;
//    }
//
//    private Jwt getJwt(BearerTokenAuthenticationToken bearer) {
//        try {
//            Jwt decode = this.jwtDecoder.decode(bearer.getToken());
//            Map<String, Object> claims = decode.getClaims();
//            return decode;
//        }
//        catch (BadJwtException failed) {
//            this.logger.debug("Failed to authenticate since the JWT was invalid");
//            throw new InvalidBearerTokenException(failed.getMessage(), failed);
//        }
//        catch (JwtException failed) {
//            throw new AuthenticationServiceException(failed.getMessage(), failed);
//        }
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return BearerTokenAuthenticationToken.class.isAssignableFrom(authentication);
//    }
//
//    public void setJwtAuthenticationConverter(
//            Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter) {
//        Assert.notNull(jwtAuthenticationConverter, "jwtAuthenticationConverter cannot be null");
//        this.jwtAuthenticationConverter = jwtAuthenticationConverter;
//    }

//}
