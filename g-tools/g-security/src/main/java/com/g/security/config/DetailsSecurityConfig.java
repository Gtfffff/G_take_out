package com.g.security.config;

import com.g.commons.base.constant.CacheConstant;
import com.g.commons.base.constant.SecurityConstant;
import com.g.commons.base.entity.vo.result.ResultCode;
import com.g.commons.utils.ResponseUtils;
import com.g.feign.api.OauthClient;
import com.g.oauth.properties.OauthConfigProperties;
import com.g.security.authentication.MyToken;
import com.g.security.authentication.ResourceAuthenticationEntryPoint;
import com.g.security.state.ExceptionStateManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: Gtf
 * @Date: 2022/5/11-05-11-13:52
 * @Description: 存放自定义的零散的bean组件
 * @Version: 1.0
 */
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@Slf4j
public class DetailsSecurityConfig {
    private final CacheChannel cacheChannel;
    private final ExceptionStateManager manager;
    private final OauthClient oauthClient;
    private final OauthConfigProperties oauthConfigProperties;

    /**
     * 默认的权限异常处理
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = {"accessDeniedHandler","defaultResourceServerConfig"})
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, e) -> {
            log.error("AccessDeniedHandler提示：" + e);
            ResponseUtils.sendResultAboutSecurityE(response,ResultCode.UN_AUTHORIZED);
        };
    }


    /**
     * 默认的认证异常处理
     * @see org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = {"authenticationEntryPoint","defaultResourceServerConfig"})
    public AuthenticationEntryPoint authenticationEntryPoint() {
        ResourceAuthenticationEntryPoint entryPoint = new ResourceAuthenticationEntryPoint();
        entryPoint.setManager(manager);
        return entryPoint;
        //普通实现
//        ResourceAuthenticationEntryPointCopy entryPoint = new ResourceAuthenticationEntryPointCopy();
//        entryPoint.setCacheChannel(cacheChannel);
//        entryPoint.setOauthClient(oauthClient);
//        entryPoint.setOauthConfigProperties(oauthConfigProperties);
//        return entryPoint;
    }


    /**
     * 默认令牌增强类
     * 使用Lambda写法会导致BeanCreateException，未找到出错原因
     * 默认方式下只有scope集合生效，所以加入authorities集合并返回
     * 设置token黑名单
     * @return
     */
    @ConditionalOnMissingBean(name = {"converter","defaultResourceServerConfig"})
    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> converter(){
        Converter<Jwt, AbstractAuthenticationToken> converter = new Converter<Jwt, AbstractAuthenticationToken>() {
            @Override
            public AbstractAuthenticationToken convert(Jwt jwt) {
                //黑名单机制
                String id = jwt.getId();
                String key = CacheConstant.BLACK_LIST_USER + id;

                CacheObject cacheObject = cacheChannel.get(CacheConstant.REGION_BLACK_LIST_USER, key);
                Object isBlacklist = cacheObject.getValue();
                if (isBlacklist != SecurityConstant.JWT_BLACKLIST){
                    throw new BadCredentialsException("jwt已过期");
                }

                //封装新的权限集合
                //获取Jwt中的authorities对应的字段集合
                List<String> authorities = jwt.getClaimAsStringList("authorities");
                //获取Jwt中的scope对应的字段集合
                List<String> scopes = jwt.getClaimAsStringList("scope");
                //将两种权限组合成新的权限集合
                List<SimpleGrantedAuthority> combinedAuthorities = Stream.concat(
                        authorities.stream(), scopes.stream().map(s -> "SCOPE_" + s)
                )
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                //获取用户名信息
                List<String> username = jwt.getClaimAsStringList("user_name");

                //存入jti和过期时间
                MyToken myToken = new MyToken(username, null, combinedAuthorities);
                long expiresTime = jwt.getExpiresAt().getEpochSecond();

                Map<String,Object> map = myToken.getMap();
                map.put(SecurityConstant.JTI,id);
                //无法直接将 Instant 类型存入redis，所以存入他的 long 值
                map.put(SecurityConstant.JWT_EXPIRE,expiresTime);
                return myToken;
            }
        };
        return converter;
    }
}
