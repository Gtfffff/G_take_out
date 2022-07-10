package com.g.customer.controller;

import com.g.commons.base.entity.vo.result.Result;
import com.g.customer.service.AddressService;
import com.g.feign.api.OauthClient;
import com.g.log.annotation.SysLog;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * @Author: Gtf
 * @Date: 2022/4/23-04-23-17:12
 * @Description: com.g.customer.api
 * @Version: 1.0
 */
@RestController
@RequestMapping("/addressBook")
@RequiredArgsConstructor
@Slf4j
@Api(tags = "目前是测试类")
@Validated
public class AddressApi {

    private final AddressService addressService;
    private final OauthClient oauthClient;


//    @ApiOperation(value = "测试用方法1",notes = "需要认证且拥有admin权限")
    @GetMapping("/test3")
//    @PreAuthorize("hasRole('admin')")
    public Result test1(){
        log.info("test1方法被访问了");
        return Result.success("cg");
    }

    @SysLog("test2方法")
//    @ApiOperation(value = "测试用方法2",notes = "公开方法，不需要任何条件都可以访问")
    @GetMapping("/test2")
    @PreAuthorize("permitAll()")
    public Result test2(){
        log.info("test2方法被访问了");
        return Result.success();
    }

    @GetMapping("/delete")
//    @PreAuthorize("permitAll()")
    public String delete(@NotBlank(message = "id不能为空") String id){
        System.out.println("delete..." + id);
        return "OK";
    }


//    @PostMapping("/add")
//    public Result addAddress(@RequestBody AddressVo addressVo) {
//        return addressService.addAddress(addressVo);
//    }
//
//    @PutMapping("/default")
//    public Result setDefault(@RequestBody AddressVo addressVo) {
//        return addressService.setDefault(addressVo);
//    }
//
//    @GetMapping("/{id}")
//    public Result getAddressById(@PathVariable Long id) {
//        return addressService.getAddressById(id);
//    }
//
//    @GetMapping("/default")
//    public Result getDefault() {
//        return addressService.getDefault();
//    }
//
//    @GetMapping("/list")
//    public Result getAddressList(AddressBook addressBook) {
//
//    }
}
