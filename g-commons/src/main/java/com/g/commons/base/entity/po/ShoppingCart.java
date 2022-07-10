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
 * @Date: 2022-04-23 00:33:40
 * @Description: 
 * @Version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("shopping_cart")
public class ShoppingCart implements Serializable{

    private static final long serialVersionUID = 1L;

    //主键
    @TableId   
    private Long id;
    //名称    
    private String name;
    //图片    
    private String image;
    //主键    
    private Long userId;
    //菜品id    
    private Long dishId;
    //套餐id    
    private Long setmealId;
    //口味    
    private String dishFlavor;
    //数量    
    private Integer number;
    //金额    
    private Double amount;
    //创建时间    
    private Date createTime;
    //删除标志（0代表未删除）
    private Integer delFlag;
    //完成标志（0代表未完成）
    private Integer isFinish;
}

