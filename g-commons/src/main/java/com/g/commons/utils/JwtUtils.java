package com.g.commons.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.nimbusds.jose.JWSObject;
import lombok.SneakyThrows;

/**
 * @Author: Gtf
 * @Date: 2022/6/2-06-02-20:46
 * @Description: com.g.commons.utils
 * @Version: 1.0
 */
public class JwtUtils {


    @SneakyThrows
    public static JSONObject getJwtPayload(String token){
        String payload = StrUtil.toString(JWSObject.parse(token).getPayload());
        JSONObject jsonObject = JSONUtil.parseObj(payload);
        return jsonObject;
    }
}
