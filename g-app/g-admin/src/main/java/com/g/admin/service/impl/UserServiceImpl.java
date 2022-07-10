package com.g.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.g.admin.mapper.UserMapper;
import com.g.admin.service.PermissionService;
import com.g.admin.service.UserRoleService;
import com.g.admin.service.UserService;
import com.g.commons.base.constant.EntityConstant;
import com.g.commons.base.entity.dto.UserForm;
import com.g.commons.base.entity.dto.UserInfo;
import com.g.commons.base.entity.po.User;
import com.g.commons.base.entity.po.UserRole;
import com.g.commons.base.entity.vo.result.Result;
import com.g.oauth.entity.Role;
import com.g.oauth.entity.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import net.oschina.j2cache.CacheChannel;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @Author: Gtf
 * @Date: 2022-04-22 17:53:17
 * @Description: 
 * @Version: 1.0
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;
    private final PermissionService permissionService;
    private final UserRoleService userRoleService;
    private final CacheChannel cacheChannel;



    /**
     * 获取 User
     */


    @Override
    public User getUserByName(String username) {
        return userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getName,username));
    }

    @Override
    public User getUserByNum(String num) {
        return userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getPhone,num));
    }

    @Override
    public User getUserByWeChat(String oid) {
        return userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getWechatOid,oid));
    }

    /**
     * 获取 UserDetailsDto
     */

    @Override
    public UserDetailsDto getUserDetailsByName(String name) {
        User user = getUserByName(name);
        if(user == null){
            return null;
        }
//        List<String> permission = permissionService.getPermissionByUserId(user.getId());
        List<Role> roles = permissionService.getRoleByUserId(user.getId());
        return new UserDetailsDto(user,roles);
    }

    @Override
    public UserDetailsDto getUserDetailsByNum(String num) {
        User user = getUserByNum(num);
        if(user == null){
            return null;
        }
        List<Role> roles = permissionService.getRoleByUserId(user.getId());
        return new UserDetailsDto(user,roles);
    }

    @Override
    public UserDetailsDto getUserDetailsByWechat(String unionId) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getWechatUid, unionId));
        if(user == null){
            return null;
        }
        List<Role> roles = permissionService.getRoleByUserId(user.getId());
        return new UserDetailsDto(user,roles);
    }

    /**
     * 注册 User
     */


    @Override
    public Result registerUser(UserForm userForm) {
        User user = new User();
        BeanUtil.copyProperties(userForm,user);
        User dUser = addDefault(user);
        boolean save = this.save(preAddUser(dUser));
        if (save){
            UserRole userRole = new UserRole(dUser.getId(), 2L);
            boolean result = userRoleService.save(userRole);
            if (!result){
                return Result.failed("权限设置失败");
            }
        }
        return Result.judge(save);
    }

    @Override
    public Result registerUserByOid(User user) {
        boolean save = this.save(preAddUser(user));
        if (save){
            UserRole userRole = new UserRole(user.getId(), 2L);
            boolean result = userRoleService.save(userRole);
            if (!result){
                return Result.failed("权限设置失败");
            }
        }
        return Result.judge(save);
    }

    @Override
    public Result registerUserByNum(String num) {
        User user = new User();
        user.setPhone(num);
        User dUser = addDefault(user);
        boolean save = this.save(preAddUser(dUser));
        if (save){
            UserRole userRole = new UserRole(dUser.getId(), 2L);
            boolean result = userRoleService.save(userRole);
            if (!result){
                return Result.failed("权限设置失败");
            }
        }
        return Result.judge(save);
    }

    /**
     * 删除User
     * @param name
     * @return
     */
    @Override
    public Result deleteUser(String name) {
        int i = userMapper.update(
                new User(), new LambdaUpdateWrapper<User>().eq(User::getName, name).set(User::getStatusEnable, 0)
        );
        return Result.judge(i != -1);
    }

    /**
     * 更新User
     * @param userInfo
     * @return
     */
    @Override
    public Result updateUser(UserInfo userInfo) {
        return null;
    }


    /**
     * 为 User 添加基本状态
     * @param user
     * @return
     */
    private User preAddUser(User user){

        user.setStatusEnable(EntityConstant.USER_STATUS_ENABLE);
        user.setStatusLock(EntityConstant.USER_STATUS_ENABLE);
        user.setCreateTime(DateTime.now());
        return user;
    }

    /**
     * 为 User 添加默认头像和昵称
     * @param user
     * @return
     */
    private User addDefault(User user){
        user.setAvatar(EntityConstant.USER_DEFAULT_AVATAR);
        user.setNickName(EntityConstant.USER_DEFAULT_NICKNAME);
        return user;
    }
}

