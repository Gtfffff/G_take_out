package com.g.security.annotation;

//import com.g.security.config.ResourceServerConfig;
import com.g.security.config.ResourceServerConfig;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.lang.annotation.*;

/**
 * @Author: Gtf
 * @Date: 2022/5/11-05-11-13:41
 * @Description: com.g.security.annotation
 * @Version: 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableWebSecurity
@Import({ResourceServerConfig.class})
public @interface EnableResourceServerSecurity {
}

