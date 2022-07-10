package com.g.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.g.admin.service.SmsService;
import com.g.commons.base.constant.ApiConstant;
import com.g.commons.base.constant.CacheConstant;
import com.g.commons.base.entity.vo.result.Result;
import lombok.RequiredArgsConstructor;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Author: Gtf
 * @Date: 2022/5/28-05-28-16:30
 * @Description: com.g.admin.service.impl
 * @Version: 1.0
 */
@RequiredArgsConstructor
@Service
public class SmsServiceImpl implements SmsService {

    private final CacheChannel cacheChannel;

    @Override
    public boolean checkCode(String phone, String code) {
        String key = CacheConstant.USER_SMS_CODE + phone;
        //缓存取出信息
        CacheObject cacheObject = cacheChannel.get(CacheConstant.REGION_USER_SMS_CODE, key);
        String cacheCode = (String) cacheObject.getValue();
        //验证不通过操作
        if (!StringUtils.hasText(cacheCode) || !code.equals(cacheCode)){
            return false;
        }
        return true;
    }

    @Override
    public Result sentCode(String phoneNum) {
        //缓存key
        String key = CacheConstant.USER_SMS_CODE + phoneNum;
        //防止频繁请求
        CacheObject cacheObject = cacheChannel.get(CacheConstant.REGION_USER_SMS_CODE, key);
        if (!ObjectUtil.isNull(cacheObject.getValue())){
            return Result.failed(ApiConstant.TOO_FREQUENT_REQUEST);
        }
        //模拟发送手机验证码
        String code = RandomUtil.randomString(6);
        cacheChannel.set(CacheConstant.REGION_USER_SMS_CODE,key,code);
        return Result.success(code);
    }
}
