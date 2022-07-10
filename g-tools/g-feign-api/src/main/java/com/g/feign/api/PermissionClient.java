package com.g.feign.api;

import com.g.commons.base.entity.vo.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Author: Gtf
 * @Date: 2022/5/23-05-23-21:58
 * @Description: com.g.feign.api
 * @Version: 1.0
 */
@FeignClient(value = "admin")
public interface PermissionClient {

//    @GetMapping("pb/p/permission/{userId}")
//    Result getPermissionByUserId(@PathVariable(value = "userId") Long userId);
}
