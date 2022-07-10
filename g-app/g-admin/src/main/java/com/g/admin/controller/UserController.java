package com.g.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import com.g.admin.service.SmsService;
import com.g.admin.service.UserService;
import com.g.commons.base.constant.ApiConstant;
import com.g.commons.base.constant.SecurityConstant;
import com.g.commons.base.entity.dto.UserForm;
import com.g.commons.base.entity.dto.UserInfo;
import com.g.commons.base.entity.po.User;
import com.g.commons.base.entity.vo.result.Result;
import com.g.commons.base.enums.LoginTypeConstant;
import com.g.commons.utils.JwtUtils;
import com.g.commons.utils.RequestUtils;
import com.g.commons.validation.groups.PassWord;
import com.g.commons.validation.groups.PhoneNum;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Gtf
 * @Date: 2022/5/23-05-23-20:41
 * @Description: com.g.admin.controller
 * @Version: 1.0
 */
@RestController
@RequestMapping("/pb/user")
@RequiredArgsConstructor
//@Api(tags = "公开user接口")
public class UserController {

    private final UserService userService;
    private final SmsService smsService;
    private final BCryptPasswordEncoder cryptPasswordEncoder;

//    @ApiOperation(value = "根据用户名获取user信息")
//    @ApiImplicitParam(name = "name",value = "用户名",required = true)
    @GetMapping("/userInfo/{name}")
    public Result getUserInfoByName(
            @Length(min = 4,max = 12,message = "账号错误")
            @PathVariable String name) {
        User user = userService.getUserByName(name);
        //移除敏感信息
        UserInfo userInfo = BeanUtil.copyProperties(user, UserInfo.class);
        return Result.success(userInfo);
    }

//    @ApiOperation(value = "返回用户基本信息")
    @GetMapping("/userInfo")
    public Result getUserInfo() {
        //从token中获取信息
        JSONObject jwtPayload = JwtUtils.getJwtPayload(RequestUtils.getAccessToken());
        String loginType = jwtPayload.getByPath(SecurityConstant.LOGIN_TYPE).toString();
        String name = jwtPayload.getByPath(SecurityConstant.USER_NAME).toString();
        User user;
        if (loginType.equals(LoginTypeConstant.PASSWORD.getType())){
            user = userService.getUserByName(name);
            //手机号方式
        }else if (loginType.equals(LoginTypeConstant.SMS_CODE.getType())){
            user = userService.getUserByNum(name);
            //剩下都按openid方式处理
        }else {
            user = userService.getUserByWeChat(name);
        }
        //移除敏感信息
        UserInfo userInfo = BeanUtil.copyProperties(user, UserInfo.class);
        return Result.success(userInfo);
    }

//    @ApiOperation(value = "使用表单的方式注册用户")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "name",value = "用户名",required = true),
//            @ApiImplicitParam(name = "password",value = "密码",required = true)
//    })
    @PostMapping("/pw")
    public Result registerUserByPw(
            @Validated(PassWord.class)
            @RequestBody UserForm userForm){
        User user = userService.getUserByName(userForm.getName());
        if (!ObjectUtil.isNull(user)){
            return Result.failed(ApiConstant.USER_IS_EXISTS);
        }
        return userService.registerUser(userForm);
    }

//    @ApiOperation(value = "使用手机号的方式注册用户")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "num",value = "手机号",required = true),
//            @ApiImplicitParam(name = "code",value = "验证码",required = true)
//    })
    @PostMapping("/num")
    public Result registerUserByNum(
            @Validated(PhoneNum.class)
            @RequestBody UserForm userForm){
        String phone = userForm.getPhone();
        String code = userForm.getCode();
        boolean result = smsService.checkCode(phone,code);
        if (result == false){
            return Result.failed(ApiConstant.CODE_ILLEGAL);
        }
        User user = userService.getUserByNum(phone);
        if (!ObjectUtil.isNull(user)){
            return Result.failed(ApiConstant.USER_IS_EXISTS);
        }
        return userService.registerUser(userForm);
    }

//    @ApiOperation(value = "发送验证码")
//    @ApiImplicitParam(name = "phoneNum",value = "手机号",required = true)
    @PostMapping("/code")
    public Result sentCode(
            @Length(min = 11,max = 11,message = "手机号错误")
            @RequestParam String phoneNum){
        return smsService.sentCode(phoneNum);
    }

//    @ApiOperation(value = "删除用户")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "name",value = "用户名",required = true),
//            @ApiImplicitParam(name = "password",value = "密码",required = true)
//    })
    @DeleteMapping()
    public Result deleteUser(
            @Validated(PassWord.class)
            @RequestBody UserForm userForm){
        String name = userForm.getName();
        User user = userService.getUserByName(name);
        if (user == null){
            return Result.failed(ApiConstant.NAME_ILLEGAL);
        }
        String encode = cryptPasswordEncoder.encode(userForm.getPassword());
        if (!user.getPassword().equals(encode)){
            return Result.failed(ApiConstant.PASSWORD_ILLEGAL);
        }
        return userService.deleteUser(name);
    }


//    @PostMapping("/userInfo")
//    public Result updateUserInfo(@RequestBody UserInfo userInfo){
//
////        return userService.updateUser(userInfo);
//    }
}
