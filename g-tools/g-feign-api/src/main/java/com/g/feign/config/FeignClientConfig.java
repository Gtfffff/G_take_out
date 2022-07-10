package com.g.feign.config;

import com.alibaba.fastjson.JSON;
import com.g.commons.base.constant.ApiConstant;
import com.g.commons.base.constant.SecurityConstant;
import com.g.commons.base.entity.vo.result.Result;
import com.g.commons.utils.RequestUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.springframework.cloud.openfeign.security.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Gtf
 * @Date: 2022/6/2-06-02-19:21
 * @Description: com.g.feign.config
 * @Version: 1.0
 */
@Configuration
public class FeignClientConfig {

    /**
     * 为 PV Client 添加简单验证机制
     * @return
     */
    @Bean
    public RequestInterceptor headerInterceptor() {
        return (RequestTemplate template) -> {
            //为 pv 添加请求头，确保是内部通信
            String url = template.request().url();
            if(RequestUtils.isPv(url)){
                byte[] token = Base64Utils.encode((SecurityConstant.PV_ACCESS_VALUE).getBytes(StandardCharsets.UTF_8));
                String[] headerValues = {new String(token)};
                template.header(SecurityConstant.PV_ACCESS_KEY,headerValues);
            }
            //添加标识请求头，区分feign请求和外部请求
//            template.header(ApiConstant.FEIGN_MARK,ApiConstant.FEIGN_HEADER);
        };
    }
    /**
     * Feign异常处理
     */
//    @Bean
//    public ErrorDecoder errorDecoder(){
//        return (String method, Response response) -> {
//            try {
//                //获取数据
//                String errorContent = IOUtils.toString(response.body().asInputStream());
//                String body = Util.toString(response.body().asReader(Charset.defaultCharset()));
//
//                Result result = JSON.parseObject(body,Result.class);
//                if(!Result.isSuccess(result)){
//                    return new BizException(result.getData(),result.getMsg());
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return new BizException("Feign client 调用异常");
//        };
//    }

//    @Bean
//    public RequestInterceptor OAuth2FeignRequestInterceptor(){
//        return new OAuth2FeignRequestInterceptor();
//    }
}
