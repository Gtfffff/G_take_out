package com.g.commons.base.entity.dto;

import com.g.commons.validation.groups.PassWord;
import com.g.commons.validation.groups.PhoneNum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @Author: Gtf
 * @Date: 2022/5/28-05-28-1:04
 * @Description: com.g.commons.base.entity.dto
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//@ApiModel("用户表单登录")
public class UserForm {
    //用户名
    @NotNull(message = "用户名不能为空",groups = PassWord.class)
    @Length(min = 4,max = 12,message = "账号错误")
    private String name;
    //手机号
    @NotNull(message = "手机号不能为空",groups = PhoneNum.class)
    @Length(min = 11,max = 11,message = "手机号错误")
    private String phone;
    //性别(0女1男)
    @Range(min = 0,max = 1,message = "输入错误")
    private String sex;
    //用户密码
    @NotNull(message = "密码不能为空",groups = PassWord.class)
    @Length(min = 5,max = 18,message = "密码错误")
    private String password;
    //昵称
    @Length(min = 1,max = 12,message = "请输入1到12位合法昵称")
    private String nickName;
    //验证码
    @NotNull(message = "验证码不能为空",groups = PhoneNum.class)
    @Length(min = 6,max = 6,message = "验证码错误")
    private String code;
}
