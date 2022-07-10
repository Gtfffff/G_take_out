package com.g.admin;

import com.g.security.annotation.EnableResourceServerSecurity;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author: Gtf
 * @Date: 2022/5/23-05-23-20:32
 * @Description: com.g.admin
 * @Version: 1.0
 */
@EnableResourceServerSecurity
@EnableFeignClients("com.g.feign")
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.g.*"})
@MapperScan(basePackages = "com.g.admin.mapper")
public class AdminApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(AdminApplication.class, args);
//        System.out.println(run.getBean("defaultSecurityConfig"));
//        System.out.println(run.getBean(WebSecurityConfigurerAdapter.class));
//        System.out.println(run.getBean(ResourceServerConfig.class));
    }
}
