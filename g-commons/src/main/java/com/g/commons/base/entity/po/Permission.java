package com.g.commons.base.entity.po;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Gtf
 * @Date: 2022-04-29 22:08:50
 * @Description: 
 * @Version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("permission")
public class Permission implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @TableId   
    private String id;
    //权限标识符    
    private String code;
    //描述    
    private String description;
    //请求地址    
    private String url;
}

