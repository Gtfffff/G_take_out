package com.g.commons.utils;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.g.commons.base.entity.vo.result.Result;
import com.g.commons.base.entity.vo.result.ResultCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Gtf
 * @Date: 2022/5/17-05-17-20:37
 * @Description: com.g.security.utils
 * @Version: 1.0
 */
public class ResponseUtils {
    /**
     * 返回失败的响应
     * @param response
     * @param resultCode
     * @throws IOException
     */
    public static void sendResultAboutSecurityE(HttpServletResponse response,ResultCode resultCode) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpStatus.HTTP_OK);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        Result result = Result.failed(resultCode);
        response.getWriter().print(JSONUtil.toJsonStr(result));
        response.getWriter().flush();
    }
}
