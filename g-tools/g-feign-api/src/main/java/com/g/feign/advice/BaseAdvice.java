package com.g.feign.advice;

import com.g.commons.base.constant.ApiConstant;
import com.g.commons.base.entity.vo.result.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @Author: Gtf
 * @Date: 2022/6/18-06-18-19:05
 * @Description: 统一修改feign的返回格式
 * @Version: 1.0
 */
//@RestControllerAdvice(basePackages = "com.g")
//public class BaseAdvice implements ResponseBodyAdvice  {
//    @Override
//    public boolean supports(MethodParameter returnType, Class converterType) {
//        return true;
//    }
//
//    @Override
//    public Object beforeBodyWrite(
//            Object body, MethodParameter returnType, MediaType selectedContentType,
//            Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
//        String mark = request.getHeaders().getFirst(ApiConstant.FEIGN_MARK);
//    }
//}
