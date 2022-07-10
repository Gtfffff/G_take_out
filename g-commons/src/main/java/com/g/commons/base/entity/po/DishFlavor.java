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
 * @Date: 2022-04-22 17:43:59
 * @Description: 
 * @Version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("dish_flavor")
public class DishFlavor implements Serializable{

    private static final long serialVersionUID = 1L;

    //主键
    @TableId   
    private Long id;
    //菜品    
    private Long dishId;
    //口味名称    
    private String name;
    //口味数据list    
    private String value;
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

