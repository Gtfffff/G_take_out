package com.g.customer.entity.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Gtf
 * @Date: 2022/4/23-04-23-17:16
 * @Description: com.g.customer.entity.vo
 * @Version: 1.0
 */
@AllArgsConstructor
@Data
public class AddressVo {
    //主键
    private Long id;
    //用户id
    private Long userId;
    //收货人
    private String consignee;
    //性别 0 女 1 男
    private Integer sex;
    //手机号
    private String phone;
    //省级区划编号
    private String provinceCode;
    //省级名称
    private String provinceName;
    //市级区划编号
    private String cityCode;
    //市级名称
    private String cityName;
    //区级区划编号
    private String districtCode;
    //区级名称
    private String districtName;
    //详细地址
    private String detail;
    //标签
    private String label;
    //默认 0 否 1是
    private Integer isDefault;

}
