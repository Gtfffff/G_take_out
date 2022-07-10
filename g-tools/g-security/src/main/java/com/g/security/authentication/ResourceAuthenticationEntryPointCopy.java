package com.g.security.authentication;

/**
 * @Author: Gtf
 * @Date: 2022/6/13-06-13-22:18
 * @Description: 旧写法的备份
 * @Version: 1.0
 */
//@Setter
//@Slf4j
//public class ResourceAuthenticationEntryPointCopy implements AuthenticationEntryPoint {
//
//    private CacheChannel cacheChannel;
//    private OauthClient oauthClient;
//    private OauthConfigProperties oauthConfigProperties;
//
//    /**
//     * 处理异常信息
//     * @param request
//     * @param response
//     * @param e
//     * @throws IOException
//     * @throws ServletException
//     */
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
//        log.error("AuthenticationEntryPoint提示：" + e);
//        switch (AuthenticationExceptionEnum.InsufficientAuthenticationException){
//            case InsufficientAuthenticationException:
//                //满足 e 包含 jwt 过期信息则尝试进行token刷新
//                if (e.getMessage().contains(SecurityConstant.REFRESH_CONDITION)){
//                    JSON result = tryRefreshToken(request,response);
//                    returnResult(request,response,result);
//                }else {
//                    ResponseUtils.sendResultAboutSecurityE(response, ResultCode.UN_AUTHENTICATION);
//                }
//                break;
//            case InvalidBearerTokenException:
//                ResponseUtils.sendResultAboutSecurityE(response, ResultCode.UN_AUTHENTICATION);
//                break;
//            default:
//                ResponseUtils.sendResultAboutSecurityE(response, ResultCode.UN_AUTHENTICATION);
//                break;
//        }
//    }
//
//    /**
//     * 尝试进行 token 刷新
//     * @param request
//     * @param response
//     * @return
//     */
//    private JSON tryRefreshToken(HttpServletRequest request, HttpServletResponse response){
////        String clientId = request.getParameter(SecurityConstant.CLIENT_ID);
//        String accessToken = RequestUtils.getAccessToken();
//        JSONObject jwtPayload = JwtUtils.getJwtPayload(accessToken);
//        String clientId = (String)jwtPayload.get(SecurityConstant.CLIENT_ID);
//        JSON result = null;
//        //判断是否为服务器前端
//        if (clientId.equals(SecurityConstant.LOCAL_CLIENT)){
//            String oid = (String)jwtPayload.get(SecurityConstant.OPEN_ID);
//            String key = CacheConstant.REFRESH_TOKEN + oid;
//            CacheObject cacheObject = cacheChannel.get(CacheConstant.REGION_REFRESH_TOKEN, key);
//            String refreshToken = (String)cacheObject.getValue();
//            //判断refreshToken是否过期
//            if (refreshToken != null){
//                Object resultObj = getRefreshToken(clientId, refreshToken);
//                result = JSONUtil.parse(resultObj);
//            }
//        }else {
//            String refreshToken = request.getParameter(SecurityConstant.TYPE_REFRESH_TOKEN);
//            //判断是否携带refreshToken
//            if (refreshToken != null){
//                String clientSecret = request.getParameter(SecurityConstant.CLIENT_SECRET);
//                Object resultObj = getRefreshToken(clientId, refreshToken, clientSecret);
//                result = JSONUtil.parse(resultObj);
//            }
//        }
//        return result;
//    }
//
//    /**
//     * 对 refreshToken 请求的结果进行处理
//     * @param request
//     * @param response
//     * @param result
//     */
//    @SneakyThrows
//    private void returnResult(HttpServletRequest request, HttpServletResponse response,JSON result){
//        //如果刷新成功，则替换过期 token 并继续原来的请求
//        if (result != null || (int)result.getByPath("code") == ResultCode.SUCCESS.getCode()){
//            JSON json = JSONUtil.parse(result.getByPath("data"));
//            String refreshToken = (String)json.getByPath(SecurityConstant.TYPE_REFRESH_TOKEN);
//            response.addCookie(new Cookie(SecurityConstant.AUTHORIZATION,refreshToken));
//            request.getRequestDispatcher(request.getRequestURI()).forward(request,response);
//        //刷新失败跳转到登录页面
//        }else {
//            ResponseUtils.sendResultAboutSecurityE(response, ResultCode.UN_AUTHENTICATION);
//            response.sendRedirect(oauthConfigProperties.getLoginFormUrl());
//        }
//    }
//
//    /**
//     * 组装并发送 refreshToken 请求
//     * @param clientId
//     * @param refreshToken
//     * @return
//     */
//    private Object getRefreshToken(String clientId,String refreshToken){
//        //请求头
////        MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
//        MultiValueMap<String,String> body = new LinkedMultiValueMap<>();
//        //params
//        body.add(SecurityConstant.CLIENT_ID, clientId);
//        body.add(SecurityConstant.CLIENT_SECRET,oauthConfigProperties.getClientSecret());
//        body.add(SecurityConstant.GRANT_TYPE,SecurityConstant.TYPE_REFRESH_TOKEN);
//        body.add(SecurityConstant.TYPE_REFRESH_TOKEN,refreshToken);
//        return oauthClient.postAccessToken(body);
//    }
//    private Object getRefreshToken(String clientId,String refreshToken,String clientSecret){
//        //请求头
////        MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
//        MultiValueMap<String,String> body = new LinkedMultiValueMap<>();
//        //params
//        body.add(SecurityConstant.CLIENT_ID, clientId);
//        body.add(SecurityConstant.CLIENT_SECRET,clientSecret);
//        body.add(SecurityConstant.GRANT_TYPE,SecurityConstant.TYPE_REFRESH_TOKEN);
//        body.add(SecurityConstant.TYPE_REFRESH_TOKEN,refreshToken);
//        return oauthClient.postAccessToken(body);
//    }
//}
