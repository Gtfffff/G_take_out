package com.g.auth.authentication.service;


import cn.hutool.json.JSONObject;
import com.alibaba.druid.sql.ast.statement.SQLIfStatement;
import com.g.commons.base.constant.SecurityConstant;
import com.g.commons.base.enums.LoginTypeConstant;
import com.g.commons.utils.JwtUtils;
import com.g.commons.utils.RequestUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

/**
 * @Author: Gtf
 * @Date: 2022/4/10-04-10-0:02
 * @Description: 对刷新token加载客户端的改动
 * @see org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper
 * @Version: 1.0
 */
public class PreAuthenticatedUserDetailsService<T extends Authentication> implements AuthenticationUserDetailsService<T>, InitializingBean {

    private UserDetailsServiceImpl userDetailsService = null;

    public PreAuthenticatedUserDetailsService() {

    }

    public PreAuthenticatedUserDetailsService(final UserDetailsService userDetailsService) {
        Assert.notNull(userDetailsService, "userDetailsService cannot be null.");
        this.userDetailsService = (UserDetailsServiceImpl)userDetailsService;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(this.userDetailsService, "UserDetailsService must be set");
    }


    /**
     * 按照不同的登录类型选择不同的方式加载 userDetails
     * @param authentication
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserDetails(T authentication) throws UsernameNotFoundException {
        JSONObject jwtPayload = JwtUtils.getJwtPayload(RequestUtils.getRefreshToken());
        String name = authentication.getName();
        String loginType = (String)jwtPayload.get(SecurityConstant.LOGIN_TYPE);
        //用户密码方式
        if (loginType.equals(LoginTypeConstant.PASSWORD.getType())){
            return this.userDetailsService.loadUserByUsername(name);
        //手机号方式
        }else if (loginType.equals(LoginTypeConstant.SMS_CODE.getType())){
            return this.userDetailsService.loadUserByPhoneNum(name);
        //剩下都按openid方式处理
        }else{
            return this.userDetailsService.loadUserByOpenId(name);
        }
    }
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = (UserDetailsServiceImpl)userDetailsService;
    }
}
