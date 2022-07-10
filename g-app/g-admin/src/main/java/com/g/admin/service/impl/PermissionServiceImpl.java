package com.g.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.g.admin.mapper.PermissionMapper;
import com.g.admin.service.PermissionService;
import com.g.commons.base.entity.po.Permission;
import com.g.oauth.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @Author: Gtf
 * @Date: 2022-04-29 22:08:50
 * @Description: 
 * @Version: 1.0
 */
@RequiredArgsConstructor
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    private final PermissionMapper permissionMapper;

    @Override
    public List<String> getPermissionByUserId(Long id) {
        return permissionMapper.selectPermissionByUserId(id);
    }
    @Override
    public List<Role> getRoleByUserId(Long id){
        return permissionMapper.selectRoleByUserId(id);
    }

    /**
     * 初始化普通用户权限信息
     * @param id
     * @return
     */
    @Override
    public boolean initUserPermission(Long id) {

        return false;
    }
}

