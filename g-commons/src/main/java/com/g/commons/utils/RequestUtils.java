package com.g.commons.utils;

import cn.hutool.core.util.StrUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Gtf
 * @Date: 2022/5/22-05-22-22:33
 * @Description: com.g.commons.utils
 * @Version: 1.0
 */
public class RequestUtils {

//    private static HttpServletRequest request;

    private static final String PV = "pv";
    private static final String REFRESH_TOKEN ="refresh_token";
    private static final String ACCESS_TOKEN ="Authorization";
    private static final String PREFIX = "Bearer ";

//    {
//        request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//    }
    /**
     * 判断是否内部私有方法
     * @param requestURI 请求路径
     * @return boolean
     */
    public static boolean isPv(String requestURI) {
        int index = requestURI.indexOf(PV);
        return index >= 0 && StringUtils.countOccurrencesOf(requestURI.substring(0,index),"/") < 1;
    }

    /**
     * 获取刷新Token
     * @return
     */
    public static String getRefreshToken(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String refreshToken = request.getParameter(REFRESH_TOKEN);
        return refreshToken;
    }

    /**
     * 获取授权Token
     * @return
     */
    public static String getAccessToken(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String accessToken = request.getHeader(ACCESS_TOKEN);
        return StrUtil.removePrefix(accessToken,PREFIX);
    }

}
