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
 * @Date: 2022-04-22 17:46:11
 * @Description: 
 * @Version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("orders")
public class Orders implements Serializable{

    private static final long serialVersionUID = 1L;

    //主键
    @TableId   
    private Long id;
    //订单号    
    private String number;
    //订单状态 1待付款，2待派送，3已派送，4已完成，5已取消    
    private Integer status;
    //下单用户    
    private Long userId;
    //地址id    
    private Long addressBookId;
    //下单时间    
    private Date orderTime;
    //结账时间    
    private Date checkoutTime;
    //支付方式 1微信,2支付宝    
    private Integer payMethod;
    //实收金额    
    private Double amount;
    //备注    
    private String remark;
        
    private String phone;
        
    private String address;
        
    private String userName;
        
    private String consignee;
}

