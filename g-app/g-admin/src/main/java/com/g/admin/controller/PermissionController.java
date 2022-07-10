package com.g.admin.controller;

import com.g.admin.service.PermissionService;
import com.g.commons.base.entity.vo.result.Result;
import com.g.oauth.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Gtf
 * @Date: 2022/5/23-05-23-21:00
 * @Description: com.g.admin.controller
 * @Version: 1.0
 */
@RestController
@RequestMapping("/pv/p")
@RequiredArgsConstructor
//@Api(tags = "私有的权限接口")
public class PermissionController {

    private final PermissionService permissionService;

//    @ApiOperation(value = "获取权限信息")
//    @ApiImplicitParam(name = "userId",value = "用户id",required = true)
    @GetMapping("/permission/{userId}")
    public Result getPermissionByUserId(@PathVariable Long userId){
        List<String> permission = permissionService.getPermissionByUserId(userId);
        return Result.success(permission);
    }
//    @ApiOperation(value = "获取角色信息")
//    @ApiImplicitParam(name = "userId",value = "用户id",required = true)
    @GetMapping("/role/{userId}")
    public Result getRoleByUserId(@PathVariable Long userId){
        List<Role> permission = permissionService.getRoleByUserId(userId);
        return Result.success(permission);
    }
}
