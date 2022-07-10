package com.g.feign.api;

import com.g.commons.base.entity.vo.result.Result;
//import com.g.feign.fallback.OauthClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: Gtf
 * @Date: 2022/6/13-06-13-2:50
 * @Description: com.g.feign.api
 * @Version: 1.0
 */
@FeignClient(value = "auth")
public interface OauthClient {
    @PostMapping("/oauth/token")
    Object postAccessToken(@RequestParam MultiValueMap<String, String> parameters);
//    ResponseEntity postAccessToken(@RequestParam MultiValueMap<String, String> parameters, @RequestHeader MultiValueMap<String, String> headers);
}
