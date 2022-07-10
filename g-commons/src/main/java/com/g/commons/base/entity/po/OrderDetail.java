package com.g.commons.base.entity.po;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Gtf
 * @Date: 2022-04-22 17:44:31
 * @Description: 
 * @Version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("order_detail")
public class OrderDetail implements Serializable{

    private static final long serialVersionUID = 1L;

    //主键
    @TableId   
    private Long id;
    //名字    
    private String name;
    //图片    
    private String image;
    //订单id    
    private Long orderId;
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
}

