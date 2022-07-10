package com.g.commons.base.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Gtf
 * @Date: 2022/6/21-06-21-19:33
 * @Description: com.g.commons.base.entity.dto
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//@ApiModel("公开的用户信息")
public class UserInfo {

    //昵称
    private String nickName;
    //性别(0女1男)
    private String sex;
}
