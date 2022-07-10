package com.g.security.filter;

import com.g.commons.base.constant.SecurityConstant;
import com.g.commons.utils.RequestUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Gtf
 * @Date: 2022/6/2-06-02-18:29
 * @Description: com.g.security.filter
 * @Version: 1.0
 */

public class FeignClientFilter extends OncePerRequestFilter {
    /**
     * 对 pv 路径请求进行安全认证确认是内部请求
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String rawPath = request.getRequestURI();
        if(RequestUtils.isPv(rawPath)){
            String header = request.getHeader(SecurityConstant.PV_ACCESS_KEY);
            String feignHeader = new String(Base64Utils.decode(header.getBytes(StandardCharsets.UTF_8)));
            if (feignHeader != SecurityConstant.PV_ACCESS_VALUE) {
                throw new HttpServerErrorException(HttpStatus.FORBIDDEN, "can't access private API");
            }
        }
      filterChain.doFilter(request,response);
    }
}
