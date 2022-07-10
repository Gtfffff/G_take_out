package com.g.admin.config;

import cn.hutool.core.util.StrUtil;
import com.g.commons.base.constant.ApiConstant;
import com.g.commons.http.CustomHttpServletRequest;
import com.g.commons.utils.JwtUtils;
import com.g.commons.utils.RequestUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

@Configuration
public class MyWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(payloadInterceptor()).addPathPatterns("/**");
    }

    private HandlerInterceptor payloadInterceptor(){
        return new HandlerInterceptor(){

            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                //替换request
                CustomHttpServletRequest customHttpServletRequest = new CustomHttpServletRequest(request);
                String accessToken = RequestUtils.getAccessToken();
                //如果是认证过的请求就把payload加载到请求头方便以后使用
                if (!StrUtil.isBlank(accessToken)) {
                    String payload = StrUtil.toString(JwtUtils.getJwtPayload(accessToken));
                    customHttpServletRequest.addHeader(ApiConstant.PAYLOAD, URLEncoder.encode(payload, "UTF-8"));
                }
                return HandlerInterceptor.super.preHandle(customHttpServletRequest, response, handler);
            }
        };
    }
}
