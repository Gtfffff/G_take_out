package com.g.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.g.commons.base.entity.po.Permission;
import com.g.oauth.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: Gtf
 * @Date: 2022-04-29 22:08:50
 * @Description: 
 * @Version: 1.0
 */

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    List<String> selectPermissionByUserId(Long id);

    List<Role> selectRoleByUserId(Long id);
}

