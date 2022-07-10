package com.g.feign.api;

import com.g.commons.base.entity.dto.UserForm;
import com.g.commons.base.entity.po.User;
import com.g.commons.base.entity.vo.result.Result;
import com.g.oauth.entity.UserDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: Gtf
 * @Date: 2022/5/23-05-23-19:59
 * @Description: com.g.feign.api
 * @Version: 1.0
 */
@FeignClient(value = "admin")
public interface UserClient {
    /**
     * PathVariable(value = "name")解决PathVariable annotation was empty on param 0报错问题
     * @param name
     * @return
     */
    @GetMapping("/pv/userDetails/Name/{name}")
    Result<UserDetailsDto> getUserDetailsByName(@PathVariable(value = "name") String name);

    @GetMapping("/pv/userDetails/Num/{num}")
    Result<UserDetailsDto> getUserDetailsByNum(@PathVariable(value = "num") String num);

    @GetMapping("/pv/userDetails/Wechat/{unionId}")
    Result<UserDetailsDto> getUserDetailsByWeChat(@PathVariable(value = "unionId") String unionId);

    @PostMapping("/pv/user_r/pw")
    Result registerUserByPw(@RequestBody UserForm userForm);

    @PostMapping("/pv/user_r/num/{num}")
    Result registerUserByNum(@PathVariable(value = "num") String num);

    @PostMapping("/pv/user_r/oid")
    Result registerUserByOid(@RequestBody User user);
}
