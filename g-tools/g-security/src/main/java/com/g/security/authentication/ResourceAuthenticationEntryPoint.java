package com.g.security.authentication;

import com.g.commons.base.entity.vo.result.ResultCode;
import com.g.commons.utils.ResponseUtils;
import com.g.security.state.ExceptionStateManager;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Gtf
 * @Date: 2022/6/13-06-13-22:18
 * @Description: com.g.security.authentication
 * @Version: 1.0
 */
@Setter
@Slf4j
public class ResourceAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private ExceptionStateManager manager;

    /**
     * 处理异常信息
     * @param request
     * @param response
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.error("AuthenticationEntryPoint提示：" + e);
        boolean isMatch = manager.execute(request, response, e);
        if (!isMatch){
            ResponseUtils.sendResultAboutSecurityE(response, ResultCode.UN_AUTHENTICATION);
        }
    }


}