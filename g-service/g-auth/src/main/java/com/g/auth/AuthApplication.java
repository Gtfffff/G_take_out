package com.g.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author: Gtf
 * @Date: 2022/4/25-04-25-15:49
 * @Description: com.g.auth
 * @Version: 1.0
 */
@EnableFeignClients("com.g.feign")
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.g.*"})
public class AuthApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext cax = SpringApplication.run(AuthApplication.class, args);
    }
}
