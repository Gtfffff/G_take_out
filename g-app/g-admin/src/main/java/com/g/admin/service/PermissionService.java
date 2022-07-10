package com.g.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.g.commons.base.entity.po.Permission;
import com.g.oauth.entity.Role;

import java.util.List;


/**
 * @Author: Gtf
 * @Date: 2022-04-29 22:08:50
 * @Description: 
 * @Version: 1.0
 */

public interface PermissionService extends IService<Permission> {

    /**
     * 根据用户id获取用户权限信息
     * @param id
     * @return
     */
    List<String> getPermissionByUserId(Long id);

    List<Role> getRoleByUserId(Long id);

    boolean initUserPermission(Long id);
}

