package com.g.auth.authentication.wechat;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.BaseWxMaServiceImpl;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceOkHttpImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.g.auth.authentication.service.UserDetailsServiceImpl;
import com.g.auth.authentication.sms.SmsCodeAuthenticationToken;
import com.g.commons.base.entity.po.User;
import com.g.commons.base.entity.vo.result.Result;
import com.g.commons.base.entity.vo.result.ResultCode;
import com.g.commons.base.enums.LoginTypeConstant;
import com.g.commons.utils.EncryptAlgorithm;
import com.g.feign.api.UserClient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import me.chanjar.weixin.common.error.WxErrorException;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * @Author: Gtf
 * @Date: 2022/5/24-05-24-17:59
 * @Description: 手机验证码模式的认证模块，仿制 AbstractUserDetailsAuthenticationProvider 的实现
 * @see org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
 * @Version: 1.0
 */
@Setter
public class WeChatAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsServiceImpl userDetailsService;
    private WxMaService wxMaService;
    private UserClient userClient;


    /**
     * 检测该 Provider 是否能校验该 Token
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (WeChatAuthenticationToken.class.isAssignableFrom(authentication));
    }

    /**
     * 认证
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        WeChatAuthenticationToken weChatToken = (WeChatAuthenticationToken) authentication;
        String code = (String)weChatToken.getCredentials();
        String rawDate = weChatToken.getRawDate();
        String signature = weChatToken.getSignature();
        // 获取sessionInfo
        WxMaJscode2SessionResult sessionInfo = getSessionInfo(code, rawDate, signature);
        if (ObjectUtil.isNull(sessionInfo)){
            throw new InvalidGrantException("签名无法解析，或被篡改，无法登录");
        }
        // 取出具体信息
        String unionId = sessionInfo.getUnionid();
        String openId = sessionInfo.getOpenid();

        Result result = userClient.getUserDetailsByWeChat(unionId);
        // 不存在就创建新用户
        if (ObjectUtil.isNull(result.getData())){
            // 解密得到用户信息
            String sessionKey = sessionInfo.getSessionKey();
            String encryptedData = weChatToken.getEncryptedData();
            String iv = weChatToken.getIv();

            WxMaUserInfo userInfo = wxMaService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
            // 注册新用户
            if (!createUser(userInfo)){
                throw new BadCredentialsException("用户不存在且注册失败");
            }
            //重新获取用户信息
            UserDetails userDetails = userDetailsService.loadUserByOpenId(unionId);
            return createSuccessAuthentication(openId,authentication,userDetails);
        }
        // 如果存在则直接使用 userDetailsService 对信息进行封装
        UserDetails userDetails = userDetailsService.loadUserByResult(result, LoginTypeConstant.WECHAT);

        return createSuccessAuthentication(openId,authentication,userDetails);
    }

    /**
     * 创建认证成功的 WeChatAuthenticationToken
     * @param principal
     * @param authentication
     * @param user
     * @return
     */
    private Authentication createSuccessAuthentication(Object principal, Authentication authentication,
                                                         UserDetails user) {
        WeChatAuthenticationToken result = new WeChatAuthenticationToken(
                (String) principal,(String) authentication.getCredentials(),user.getAuthorities()
        );
        result.setDetails(authentication.getDetails());
//        this.logger.debug("Authenticated phone");
        return result;
    }

    /**
     * 创建新用户
     * @param userInfo
     * @return
     */
    private boolean createUser(WxMaUserInfo userInfo){
        User user = new User();
        // 手动填充信息
        user.setNickName(userInfo.getNickName());
        user.setWechatUid(userInfo.getUnionId());
        user.setWechatOid(userInfo.getOpenId());
        user.setAvatar(userInfo.getAvatarUrl());
        user.setSex(userInfo.getGender());
        // 注册User
        Result result = userClient.registerUserByOid(user);
        return Result.isSuccess(result);
    }

    /**
     * 根据 jsCode 请求微信 API 获取 SessionInfo
     * @param jsCode 获取 SessionInfo
     * @param rawDate 生成验证字符串的素材
     * @param signature 校验用字符串
     * @return
     * @throws WxErrorException
     */
    private WxMaJscode2SessionResult getSessionInfo(String jsCode,String rawDate,String signature) throws WxErrorException {
        WxMaJscode2SessionResult sessionInfo =  wxMaService.getUserService().getSessionInfo(jsCode);
        // 验证 SessionInfo 是否被篡改
        String sessionKey = sessionInfo.getSessionKey();
        String str = rawDate + sessionKey;
        String signatureLocal = EncryptAlgorithm.useSHA1(str);
        if (StringUtils.equals(signature,signatureLocal)){
            throw null;
        }
        return sessionInfo;
    }
}
