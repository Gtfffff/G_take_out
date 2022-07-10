//package com.g.commons.base.entity.dto;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.g.commons.base.entity.po.User;
//import com.g.commons.base.enums.LoginTypeConstant;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * @Author: Gtf
// * @Date: 2022/4/10-04-10-0:02
// * @Description: security业务的实体类
// * @Version: 1.0
// */
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class UserDetailsImpl implements UserDetails {
//
//    //用户基本信息
//    private User user;
//    //用户权限信息
////    @JsonDeserialize(using = CustomAuthorityDeserializer.class)
//    @JsonIgnore
//    private List<Role> roles;
//    //用户登录方式
//    private LoginTypeConstant loginType;
//
//
//    /**
//     * 获取权限信息
//     * @return
//     */
//    @Override
//    public List<? extends GrantedAuthority> getAuthorities() {
//        List<SimpleGrantedAuthority> authorities= new ArrayList<>();
//
//        for (Role role : roles) {
//            authorities.addAll(role.getAuthorities());
//        }
//
//        return authorities.stream().distinct().collect(Collectors.toList());
//
//    }
//
//    /**
//     * 获取用户密码
//     * @return
//     */
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//
//    /**
//     * 获取用户名
//     * @return
//     */
//    @Override
//    public String getUsername() {
//        return user.getName();
//    }
//
//    /**
//     * 是否过期
//     * @return
//     */
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//
//    /**
//     * 是否锁定
//     * @return
//     */
//    @Override
//    public boolean isAccountNonLocked() {
//        return user.getStatusLock() == 1 ? true : false;
//    }
//
//
//    /**
//     * 凭证是否过期
//     * @return
//     */
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//
//    /**
//     * 是否可用
//     * @return
//     */
//    @Override
//    public boolean isEnabled() {
//        return user.getStatusEnable() == 1 ? true : false;
//    }
//}
