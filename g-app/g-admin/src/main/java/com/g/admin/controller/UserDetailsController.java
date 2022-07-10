package com.g.admin.controller;

import com.g.admin.service.UserService;
import com.g.commons.base.entity.vo.result.Result;
import com.g.oauth.entity.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Gtf
 * @Date: 2022/5/27-05-27-23:31
 * @Description: 获取用户信息内部接口
 * @Version: 1.0
 */
@RestController
@RequestMapping("/pv/userDetails")
@RequiredArgsConstructor
@Slf4j
//@Api(tags = "私有的返回用户安全信息接口")
public class UserDetailsController {

    private final UserService userService;

//    @ApiOperation(value = "根据用户名获取用户安全信息")
//    @ApiImplicitParam(name = "name",value = "用户名",required = true)
    @GetMapping("/Name/{name}")
    public Result<UserDetailsDto> getUserDetailsByName(@PathVariable String name){
        UserDetailsDto user = userService.getUserDetailsByName(name);
        return Result.success(user);
    }

//    @ApiOperation(value = "根据手机号获取用户安全信息")
//    @ApiImplicitParam(name = "num",value = "手机号",required = true)
    @GetMapping("/Num/{num}")
    public Result<UserDetailsDto> getUserDetailsByNum(@PathVariable String num){
        UserDetailsDto user = userService.getUserDetailsByNum(num);
        return Result.success(user);
    }

//    @ApiOperation(value = "根据微信uid获取用户安全信息")
//    @ApiImplicitParam(name = "unionId",value = "微信uid",required = true)
    @GetMapping("/Wechat/{unionId}")
    public Result<UserDetailsDto> getUserDetailsByWeChat(@PathVariable String unionId){
        UserDetailsDto user = userService.getUserDetailsByWechat(unionId);
        return Result.success(user);
    }
}
