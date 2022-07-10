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
 * @Date: 2022-04-22 17:52:53
 * @Description: 
 * @Version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("setmeal_dish")
public class SetmealDish implements Serializable{

    private static final long serialVersionUID = 1L;

    //主键
    @TableId   
    private Long id;
    //套餐id     
    private String setmealId;
    //菜品id    
    private String dishId;
    //菜品名称 （冗余字段）    
    private String name;
    //菜品原价（冗余字段）    
    private Double price;
    //份数    
    private Integer copies;
    //排序    
    private Integer sort;
    //创建时间    
    private Date createTime;
    //更新时间    
    private Date updateTime;
    //创建人    
    private Long createUser;
    //修改人    
    private Long updateUser;
    //是否删除    
    private Integer isDeleted;
}

