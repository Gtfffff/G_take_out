package com.g.admin.service;

import com.g.commons.base.entity.vo.result.Result;

/**
 * @Author: Gtf
 * @Date: 2022/5/28-05-28-1:51
 * @Description: com.g.admin.service
 * @Version: 1.0
 */
public interface SmsService {

    boolean checkCode(String phone, String code);

    Result sentCode(String phoneNum);
}
