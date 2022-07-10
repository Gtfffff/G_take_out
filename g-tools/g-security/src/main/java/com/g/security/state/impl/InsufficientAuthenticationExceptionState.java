package com.g.security.state.impl;

import com.g.commons.base.entity.vo.result.ResultCode;
import com.g.commons.utils.ResponseUtils;
import com.g.security.state.ExceptionState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Gtf
 * @Date: 2022/7/5-07-05-22:49
 * @Description: com.g.security.strategy.impl
 * @Version: 1.0
 */
@Component
@Slf4j
public class InsufficientAuthenticationExceptionState implements ExceptionState {
    @Override
    public boolean supports(Class<?> exception) {
        return InsufficientAuthenticationException.class.isAssignableFrom(exception);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        ResponseUtils.sendResultAboutSecurityE(response, ResultCode.UN_AUTHENTICATION);
    }
}
