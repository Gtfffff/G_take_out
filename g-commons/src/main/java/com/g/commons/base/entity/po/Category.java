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
 * @Date: 2022-04-22 17:43:05
 * @Description: 
 * @Version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("category")
public class Category implements Serializable{

    private static final long serialVersionUID = 1L;

    //主键
    @TableId   
    private Long id;
    //类型   1 菜品分类 2 套餐分类    
    private Integer type;
    //分类名称    
    private String name;
    //顺序    
    private Integer sort;
    //创建时间    
    private Date createTime;
    //更新时间    
    private Date updateTime;
    //创建人    
    private Long createUser;
    //修改人    
    private Long updateUser;
}

