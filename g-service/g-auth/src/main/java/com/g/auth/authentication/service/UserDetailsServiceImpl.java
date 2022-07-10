package com.g.auth.authentication.service;

import cn.hutool.core.util.ObjectUtil;
import com.g.commons.base.constant.SecurityConstant;
import com.g.commons.base.entity.po.User;
import com.g.commons.base.entity.vo.result.Result;
import com.g.commons.base.enums.LoginTypeConstant;
import com.g.feign.api.UserClient;
import com.g.oauth.entity.Role;
import com.g.oauth.entity.UserDetailsDto;
import com.g.oauth.entity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Gtf
 * @Date: 2022/4/29-04-29-18:50
 * @Description: com.g.auth.service
 * @Version: 1.0
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserClient userClient;


    /**
     * 通过用户名查找用户信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username){
        //feign获取UserDetails所需要的信息
        Result<UserDetailsDto> userResult = userClient.getUserDetailsByName(username);

        return toUserDetailsImpl(userResult,LoginTypeConstant.PASSWORD);
    }

    /**
     * 通过手机号查找用户信息
     * @param num
     * @return
     */
    public UserDetails loadUserByPhoneNum(String num){
        //feign获取UserDetails所需要的信息
        Result<UserDetailsDto> userResult = userClient.getUserDetailsByNum(num);

        return toUserDetailsImpl(userResult,LoginTypeConstant.SMS_CODE);
    }

    /**
     * 通过 openId 查找用户信息
     * @param openId
     * @return
     */
    public UserDetails loadUserByOpenId(String openId){
        //feign获取UserDetails所需要的信息
        Result<UserDetailsDto> userResult = userClient.getUserDetailsByNum(openId);

        return toUserDetailsImpl(userResult,LoginTypeConstant.WECHAT);
    }

    /**
     * 直接从 Result 中读出 UserDetails
     * @param result
     * @param loginTypeConstant
     * @return
     */
    public UserDetails loadUserByResult(Result result, LoginTypeConstant loginTypeConstant){
        return toUserDetailsImpl(result,loginTypeConstant);
    }

    /**
     * 将feign的结果转换成 UserDetails
     * @param result
     * @return
     */
    private UserDetails toUserDetailsImpl(Result result, LoginTypeConstant loginTypeConstant){
        UserDetailsImpl userDetails = null;
        if (Result.isSuccess(result)) {
            //将得到json转回对象
            UserDetailsDto userDetailsDto = (UserDetailsDto) result.getData();
            //取出具体的Details信息
            User user = userDetailsDto.getUser();
            if (ObjectUtil.isNull(user)) {
                throw new UsernameNotFoundException(SecurityConstant.ACCOUNT_NOT_FOUND);
            }
            List<Role> roles = userDetailsDto.getRoles();
//            //转换permissions的类型
//            List<SimpleGrantedAuthority> authorities = permissions
//                    .stream()
//                    .map(SimpleGrantedAuthority::new)
//                    .collect(Collectors.toList());
            //封装
            userDetails = new UserDetailsImpl(user, roles,loginTypeConstant);
        }
        //判断账户是否正常
        if (!userDetails.isEnabled()) {
            throw new DisabledException(SecurityConstant.ACCOUNT_Disabled);
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException(SecurityConstant.ACCOUNT_Locked);
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException(SecurityConstant.ACCOUNT_Expired);
        }
        return userDetails;
    }
}
