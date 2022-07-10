package com.g.commons.base.entity.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Gtf
 * @Date: 2022-05-27 21:50:20
 * @Description: 
 * @Version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User implements Serializable{

private static final long serialVersionUID = 1L;

    //主键
    @TableId   
    private Long id;
    //姓名    
    private String name;
    //手机号    
    private String phone;
    //性别(0女1男)    
    private String sex;
    //身份证号    
    private String idNumber;
    //头像    
    private String avatar;
    //状态 0:禁用，1:正常    
    private Integer statusEnable;
    //状态 0:锁定，1:正常
    private Integer statusLock;
    //用户密码
    private String password;
    //创建时间
    private Date createTime;
    //更新时间
    private Date modifyTime;
    //微信unionId
    private String wechatUid;
    //微信openId
    private String wechatOid;
    //昵称
    private String nickName;
}

