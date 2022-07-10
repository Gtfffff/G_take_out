package com.g.commons.base.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Gtf
 * @Date: 2022/6/24-06-24-23:53
 * @Description: com.g.commons.base.entity.po
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    //userId
    private Long userId;
    //roleId
    private Long roleId;

}
