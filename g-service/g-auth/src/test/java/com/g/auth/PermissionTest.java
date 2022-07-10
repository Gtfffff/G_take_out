package com.g.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/**
 * @Author: Gtf
 * @Date: 2022/5/2-05-02-20:23
 * @Description: com.g.auth
 * @Version: 1.0
 */
@SpringBootTest
public class PermissionTest {

//    @Autowired
//    private PermissionService permissionService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

//    @Test
//    public void permissionService() {
//        List<String> permission = permissionService.getPermissionByUserId(1L);
//        System.out.println(permission);
//    }

    @Test
    public void bCryptPasswordEncoder() {
        String admin = bCryptPasswordEncoder.encode("admin");
        System.out.println(admin);
    }
}
