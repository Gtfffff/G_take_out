package com.g.auth.authentication.sms;

import cn.hutool.core.util.ObjectUtil;
import com.g.auth.authentication.service.UserDetailsServiceImpl;
import com.g.commons.base.constant.CacheConstant;
import com.g.commons.base.entity.vo.result.Result;
import com.g.commons.base.enums.LoginTypeConstant;
import com.g.feign.api.UserClient;
import lombok.Setter;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

/**
 * @Author: Gtf
 * @Date: 2022/5/24-05-24-17:59
 * @Description: 手机验证码模式的认证模块，仿制 AbstractUserDetailsAuthenticationProvider 的实现
 * @see org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
 * @Version: 1.0
 */
@Setter
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private CacheChannel cacheChannel;
    private UserDetailsServiceImpl userDetailsService;
    private UserClient userClient;


    /**
     * 检测该 Provider 是否能校验该 Token
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (SmsCodeAuthenticationToken.class.isAssignableFrom(authentication));
    }

    /**
     * 认证
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //验证手机号是否是本人
        String phone = (String)authentication.getPrincipal();
        String code = (String) authentication.getCredentials();

        String key = CacheConstant.USER_SMS_CODE + phone;
        //缓存取出信息
        CacheObject cacheObject = cacheChannel.get(CacheConstant.REGION_USER_SMS_CODE, key);
        String cacheCode = (String) cacheObject.getValue();
        //验证不通过操作
        if (!StringUtils.hasText(cacheCode) || !code.equals(cacheCode)){
                throw new BadCredentialsException("验证码错误");
        }
        //移除验证成功的验证码
        cacheChannel.evict(CacheConstant.REGION_USER_SMS_CODE,key);
        //查询用户是否存在，不存在就创建一个
        Result result = userClient.getUserDetailsByNum(phone);
        if (ObjectUtil.isNull(result.getData())){
            if (!createUser(phone)){
                throw new BadCredentialsException("用户不存在且注册失败");
            }
            //重新获取用户信息
            UserDetails userDetails = userDetailsService.loadUserByPhoneNum(phone);
            return createSuccessAuthentication(phone,authentication,userDetails);
        }
        //存在则直接将 result 封装成 UserDetails
        UserDetails userDetails = userDetailsService.loadUserByResult(result, LoginTypeConstant.SMS_CODE);
        return createSuccessAuthentication(phone,authentication,userDetails);
    }

    /**
     * 创建认证成功的 SmsCodeAuthenticationToken,包含用户id，用户权限
     * @param principal
     * @param authentication
     * @param user
     * @return
     */
    private Authentication createSuccessAuthentication(Object principal, Authentication authentication,
                                                         UserDetails user) {
        SmsCodeAuthenticationToken result = new SmsCodeAuthenticationToken(principal,
                authentication.getCredentials(), user.getAuthorities());
        result.setDetails(authentication.getDetails());
//        this.logger.debug("Authenticated phone");
        return result;
    }

    /**
     * 创建用户
     * @param phoneNum
     */
    private boolean createUser(String phoneNum){
        Result result = userClient.registerUserByNum(phoneNum);
        return Result.isSuccess(result);
    }
}
