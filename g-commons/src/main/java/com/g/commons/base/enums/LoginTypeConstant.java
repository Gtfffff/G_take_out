package com.g.commons.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Gtf
 * @Date: 2022/5/27-05-27-22:38
 * @Description: com.g.commons.base.enums
 * @Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum LoginTypeConstant {

    PASSWORD("password"),
    SMS_CODE("sms_code"),
    WECHAT("wechat");

    private String type;
}
