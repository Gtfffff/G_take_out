package com.g.auth.authentication.wechat;

import lombok.Getter;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * @Author: Gtf
 * @Date: 2022/5/24-05-24-17:09
 * @Description: smsToken 仿制 UsernamePasswordAuthenticationToken
 * @see UsernamePasswordAuthenticationToken
 * @Version: 1.0
 */
public class WeChatAuthenticationToken extends AbstractAuthenticationToken{

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private String openId;
    private String jsCode;
    @Getter
    private String rawDate;
    @Getter
    private String signature;
    @Getter
    private String encryptedData;
    @Getter
    private String iv;

    /**
     * 认证前token
     * @param jsCode 获取sessionInfo
     * @param rawDate 验证一致性素材
     * @param signature 验证一致性样本
     * @param encryptedData 加密数据
     * @param iv 初始化向量
     */
    public WeChatAuthenticationToken (String jsCode,String rawDate,String signature,String encryptedData,String iv) {
        super(null);
        this.jsCode = jsCode;
        this.rawDate = rawDate;
        this.signature = signature;
        this.encryptedData = encryptedData;
        this.iv = iv;
        setAuthenticated(false);
    }

    /**
     * 认证后token
     * @param openId 可以通过 openId 找到 unionId 从而定位一个用户
     * @param jsCode 可以获取sessionInfo从而认证openId
     * @param authorities 权限
     */
    public WeChatAuthenticationToken (String openId,String jsCode, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.openId = openId;
        this.jsCode = jsCode;
        super.setAuthenticated(true);
    }

    /**
     * 弃用
     * @return
     */
    @Override
    public Object getCredentials() {
        return this.jsCode;
    }

    @Override
    public Object getPrincipal() {
        return this.openId;
    }

    /**
     * 设置是否被认证
     * @param isAuthenticated
     * @throws IllegalArgumentException
     */
    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated,
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    /**
     * 擦除数据
     */
    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.encryptedData = null;
        this.iv = null;
    }
}
