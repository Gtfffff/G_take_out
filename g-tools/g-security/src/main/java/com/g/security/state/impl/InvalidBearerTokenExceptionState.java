package com.g.security.state.impl;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.g.commons.base.constant.CacheConstant;
import com.g.commons.base.constant.SecurityConstant;
import com.g.commons.base.entity.vo.result.ResultCode;
import com.g.commons.utils.JwtUtils;
import com.g.commons.utils.RequestUtils;
import com.g.commons.utils.ResponseUtils;
import com.g.feign.api.OauthClient;
import com.g.oauth.properties.OauthConfigProperties;
import com.g.security.state.ExceptionState;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Gtf
 * @Date: 2022/7/5-07-05-21:48
 * @Description: InsufficientAuthenticationException 的处理类，如果请求者是前端者不需要携带 refreshToken 就可以自动尝试刷新，如果是其他客户端则需要携带 refreshToken
 * @Version: 1.0
 */
@Component
@Setter
@Slf4j
public class InvalidBearerTokenExceptionState implements ExceptionState {

    private CacheChannel cacheChannel;
    private OauthClient oauthClient;
    private OauthConfigProperties oauthConfigProperties;

    @Override
    public boolean supports(Class<?> exception) {
        return (InvalidBearerTokenException.class.isAssignableFrom(exception));
    }
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException{
        //满足 e 包含 jwt 过期信息则尝试进行token刷新
        if (e.getMessage().contains(SecurityConstant.REFRESH_CONDITION)){
            JSON result = tryRefreshToken(request,response);
            returnResult(request,response,result);
        }else {
            ResponseUtils.sendResultAboutSecurityE(response, ResultCode.UN_AUTHENTICATION);
        }
    }

    /**
     * 尝试进行 token 刷新
     * @param request
     * @param response
     * @return
     */
    private JSON tryRefreshToken(HttpServletRequest request, HttpServletResponse response){
//        String clientId = request.getParameter(SecurityConstant.CLIENT_ID);
        String accessToken = RequestUtils.getAccessToken();
        JSONObject jwtPayload = JwtUtils.getJwtPayload(accessToken);
        String clientId = (String)jwtPayload.get(SecurityConstant.CLIENT_ID);
        JSON result = null;
        //卫语句写法
//        do {
//            if (!SecurityConstant.LOCAL_CLIENT.equals(clientId)) {
//                break;
//            }
//            String oid = (String) jwtPayload.get(SecurityConstant.OPEN_ID);
//            String key = CacheConstant.REFRESH_TOKEN + oid;
//            CacheObject cacheObject = cacheChannel.get(CacheConstant.REGION_REFRESH_TOKEN, key);
//            String refreshToken = (String) cacheObject.getValue();
//            if (refreshToken == null){
//                break;
//            }
//            Object resultObj = getRefreshToken(clientId, refreshToken);
//            result = JSONUtil.parse(resultObj);
//        }while (false);
//
//        do {
//            if ((SecurityConstant.LOCAL_CLIENT.equals(clientId))){
//                break;
//            }
//            String refreshToken = request.getParameter(SecurityConstant.TYPE_REFRESH_TOKEN);
//            if (refreshToken == null){
//                break;
//            }
//            String clientSecret = request.getParameter(SecurityConstant.CLIENT_SECRET);
//            Object resultObj = getRefreshToken(clientId, refreshToken, clientSecret);
//            result = JSONUtil.parse(resultObj);
//        }while (false);
        //判断是否为服务器前端

        if (clientId.equals(SecurityConstant.LOCAL_CLIENT)){
            String oid = (String)jwtPayload.get(SecurityConstant.OPEN_ID);
            String key = CacheConstant.REFRESH_TOKEN + oid;
            CacheObject cacheObject = cacheChannel.get(CacheConstant.REGION_REFRESH_TOKEN, key);
            String refreshToken = (String)cacheObject.getValue();
            //判断refreshToken是否过期
            if (refreshToken != null){
                Object resultObj = getRefreshToken(clientId, refreshToken);
                result = JSONUtil.parse(resultObj);
            }
        }else {
            String refreshToken = request.getParameter(SecurityConstant.TYPE_REFRESH_TOKEN);
            //判断是否携带refreshToken
            if (refreshToken != null){
                String clientSecret = request.getParameter(SecurityConstant.CLIENT_SECRET);
                Object resultObj = getRefreshToken(clientId, refreshToken, clientSecret);
                result = JSONUtil.parse(resultObj);
            }
        }
        return result;
    }

    /**
     * 对 refreshToken 请求的结果进行处理
     * @param request
     * @param response
     * @param result
     */
    @SneakyThrows
    private void returnResult(HttpServletRequest request, HttpServletResponse response,JSON result){
        //如果刷新成功，则替换过期 token 并继续原来的请求
        if (result != null || (int)result.getByPath("code") == ResultCode.SUCCESS.getCode()){
            JSON json = JSONUtil.parse(result.getByPath("data"));
            String refreshToken = (String)json.getByPath(SecurityConstant.TYPE_REFRESH_TOKEN);
            response.addCookie(new Cookie(SecurityConstant.AUTHORIZATION,refreshToken));
            request.getRequestDispatcher(request.getRequestURI()).forward(request,response);
            //刷新失败跳转到登录页面
        }else {
            ResponseUtils.sendResultAboutSecurityE(response, ResultCode.UN_AUTHENTICATION);
            response.sendRedirect(oauthConfigProperties.getLoginFormUrl());
        }
    }

    /**
     * 组装并发送 refreshToken 请求
     * @param clientId
     * @param refreshToken
     * @return
     */
    private Object getRefreshToken(String clientId,String refreshToken){
        //请求头
//        MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
        MultiValueMap<String,String> body = new LinkedMultiValueMap<>();
        //params
        body.add(SecurityConstant.CLIENT_ID, clientId);
        body.add(SecurityConstant.CLIENT_SECRET,oauthConfigProperties.getClientSecret());
        body.add(SecurityConstant.GRANT_TYPE,SecurityConstant.TYPE_REFRESH_TOKEN);
        body.add(SecurityConstant.TYPE_REFRESH_TOKEN,refreshToken);
        return oauthClient.postAccessToken(body);
    }
    private Object getRefreshToken(String clientId,String refreshToken,String clientSecret){
        //请求头
//        MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
        MultiValueMap<String,String> body = new LinkedMultiValueMap<>();
        //params
        body.add(SecurityConstant.CLIENT_ID, clientId);
        body.add(SecurityConstant.CLIENT_SECRET,clientSecret);
        body.add(SecurityConstant.GRANT_TYPE,SecurityConstant.TYPE_REFRESH_TOKEN);
        body.add(SecurityConstant.TYPE_REFRESH_TOKEN,refreshToken);
        return oauthClient.postAccessToken(body);
    }
}
