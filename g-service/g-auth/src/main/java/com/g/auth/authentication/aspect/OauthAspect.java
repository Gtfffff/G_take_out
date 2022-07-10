package com.g.auth.authentication.aspect;

import com.g.commons.base.entity.vo.result.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @Author: Gtf
 * @Date: 2022/6/15-06-15-21:46
 * @Description: com.g.auth.authentication.aspect
 * @Version: 1.0
 */
@Component
@Aspect
public class OauthAspect {
    /**
     * 自定义 OauthToken 返回格式
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))")
    public Object handleControllerMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        ResponseEntity result = (ResponseEntity)joinPoint.proceed();
        Object body = result.getBody();
        return ResponseEntity.status(result.getStatusCode()).body(Result.success(body));
    }

}
