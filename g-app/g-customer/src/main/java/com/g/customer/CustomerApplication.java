package com.g.customer;


import com.g.commons.validation.annotation.EnableFormValidator;
import com.g.security.annotation.EnableResourceServerSecurity;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;


/**
 * @Author: Gtf
 * @Date: 2022/5/12-05-12-0:20
 * @Description: com.g.customer
 * @Version: 1.0
 */
@EnableResourceServerSecurity
@EnableDiscoveryClient
@EnableFeignClients("com.g.feign")
//默认为启动类所在包的包名
@SpringBootApplication(scanBasePackages = {"com.g.*"})
@MapperScan("com.g.commons")
@EnableFormValidator
public class CustomerApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(CustomerApplication.class, args);
    }
}
