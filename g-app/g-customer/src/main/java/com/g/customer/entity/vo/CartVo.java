package com.g.customer.entity.vo;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;


/**
 * @Author: Gtf
 * @Date: 2022/4/22-04-22-21:34
 * @Description: com.g.customer.entity.vo
 * @Version: 1.0
 */
@AllArgsConstructor
@Data
public class CartVo {

    //id
    private Long id;
    //名称
    private String name;
    //图片
    private String image;
    //口味
    private String dishFlavor;
    //数量
    private Integer number;
    //金额
    private Double amount;
    //创建时间
    private Date createTime;

}
