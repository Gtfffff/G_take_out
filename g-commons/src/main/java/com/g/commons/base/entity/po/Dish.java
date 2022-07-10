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
 * @Date: 2022-04-22 17:43:20
 * @Description: 
 * @Version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("dish")
public class Dish implements Serializable{

    private static final long serialVersionUID = 1L;

    //主键
    @TableId   
    private Long id;
    //菜品名称    
    private String name;
    //菜品分类id    
    private Long categoryId;
    //菜品价格    
    private Double price;
    //商品码    
    private String code;
    //图片    
    private String image;
    //描述信息    
    private String description;
    //0 停售 1 起售    
    private Integer status;
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
    //是否删除    
    private Integer isDeleted;
}

