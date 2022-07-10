package com.g.admin.controller;

import com.g.admin.service.UserService;
import com.g.commons.base.entity.dto.UserForm;
import com.g.commons.base.entity.po.User;
import com.g.commons.base.entity.vo.result.Result;
import com.g.commons.validation.groups.PassWord;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Gtf
 * @Date: 2022/5/28-05-28-18:15
 * @Description: 添加用户内部接口
 * @Version: 1.0
 */
@RestController
@RequestMapping("/pv/user_r")
@RequiredArgsConstructor
//@Api(tags = "私有的注册用户信息接口,简化了验证操作")
@Validated
public class AddUserController {

    private final UserService userService;

//    @ApiOperation(value = "使用表单的方式注册用户")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "name",value = "用户名",required = true),
//            @ApiImplicitParam(name = "password",value = "密码",required = true)
//    })
    @PostMapping("/pw")
    public Result registerUserByPw(@Validated(PassWord.class)@RequestBody UserForm userForm){
        return userService.registerUser(userForm);
    }

//    @ApiOperation(value = "使用手机号的方式注册用户")
//    @ApiImplicitParam(name = "num",value = "手机号",required = true)
    @PostMapping("/num/{num}")
    public Result registerUserByNum(
            @Length(min = 11,max = 11,message = "手机号错误")
            @PathVariable String num){
        return userService.registerUserByNum(num);
    }

//    @ApiOperation(value = "使用微信的方式注册用户")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "NickName",value = "昵称",required = false),
//            @ApiImplicitParam(name = "WechatUid",value = "密码",required = true),
//            @ApiImplicitParam(name = "WechatOid",value = "密码",required = true),
//            @ApiImplicitParam(name = "Avatar",value = "密码",required = false),
//            @ApiImplicitParam(name = "Sex",value = "密码",required = false)
//    })
    @PostMapping("/wechat")
    public Result registerUserByWeChat(@RequestBody User user){
        return userService.registerUserByOid(user);
    }
}
