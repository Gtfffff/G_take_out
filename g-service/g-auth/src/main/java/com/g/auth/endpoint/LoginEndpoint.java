package com.g.auth.endpoint;


import com.g.auth.authentication.token.MyToken;
import com.g.commons.base.constant.CacheConstant;
import com.g.commons.base.constant.SecurityConstant;
import com.g.commons.base.entity.vo.result.Result;
import lombok.AllArgsConstructor;
import net.oschina.j2cache.CacheChannel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;
import java.util.Map;

/**
 * @Author: Gtf
 * @Date: 2022/5/26-05-26-0:09
 * @Description: com.g.auth.endpoint
 * @Version: 1.0
 */
@AllArgsConstructor
@FrameworkEndpoint
@RequestMapping("/auth")
//@Api(tags = "登录相关的接口")
public class LoginEndpoint {

    private final CacheChannel cacheChannel;


//    @ApiOperation(value = "登出",notes = "需要携带token")
    @DeleteMapping("/logout")
    public Result logout(){
        //获取访问者当前token
        MyToken authentication = (MyToken) SecurityContextHolder.getContext().getAuthentication();
        //获取jti和过期时间
        Map<String, Object> map = authentication.getMap();
        String jti = (String) map.get(SecurityConstant.JTI);
        Long jwtExpireTime= (Long) map.get(SecurityConstant.JWT_EXPIRE);
        Long expireTime = jwtExpireTime - Instant.now().getEpochSecond();
        //将token加入黑名单
        cacheChannel.set(CacheConstant.REGION_BLACK_LIST_USER,CacheConstant.BLACK_LIST_USER,jti,expireTime);

        return Result.success();
    }
}
