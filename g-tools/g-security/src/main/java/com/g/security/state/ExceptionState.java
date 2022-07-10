package com.g.security.state;

import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Gtf
 * @Date: 2022/7/5-07-05-21:46
 * @Description: com.g.security.strategy
 * @Version: 1.0
 */
public interface ExceptionState {
    /**
     * 返回是否支持该异常的处理
     * @param exception
     * @return
     */
    boolean supports(Class<?> exception);

    /**
     * 处理异常逻辑
     * @param request
     * @param response
     * @param e
     * @throws IOException
     */
    void execute(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException;
}
