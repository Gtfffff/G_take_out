package com.g.commons.base.entity.po;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Gtf
 * @Date: 2022-04-22 17:44:13
 * @Description: 
 * @Version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("employee")
public class Employee implements Serializable{

    private static final long serialVersionUID = 1L;

    //主键
    @TableId   
    private Long id;
    //姓名    
    private String name;
    //用户名    
    private String username;
    //密码    
    private String password;
    //手机号    
    private String phone;
    //性别    
    private String sex;
    //身份证号    
    private String idNumber;
    //状态 0:禁用，1:正常    
    private Integer status;
    //创建时间    
    private Date createTime;
    //更新时间    
    private Date updateTime;
    //创建人    
    private Long createUser;
    //修改人    
    private Long updateUser;
}

