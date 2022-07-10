package com.g.security.filter;

import cn.hutool.json.JSONObject;
import cn.hutool.jwt.Claims;
import com.g.commons.utils.JwtUtils;
import com.g.commons.utils.RequestUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @Author: Gtf
 * @Date: 2022/6/11-06-11-22:24
 * @Description: com.g.security.filter
 * @Version: 1.0
 */
//public class RefreshTokenFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
//        byte[] contentAsByteArray = responseWrapper.getContentAsByteArray();
//        if (response.getStatus() == HttpServletResponse.SC_UNAUTHORIZED){
//
//        }
//        filterChain.doFilter(request,response);
//        JSONObject jwtPayload = JwtUtils.getJwtPayload(RequestUtils.getAccessToken());
//        jwtPayload.get("")
//    }
//}
