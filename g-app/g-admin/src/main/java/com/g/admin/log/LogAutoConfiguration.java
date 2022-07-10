package com.g.admin.log;


import com.g.log.event.SysLogListener;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LogAutoConfiguration {

    private final LogService logService;

    //自动配置日志监听器组件
    @Bean
    @ConditionalOnMissingBean
    public SysLogListener sysLogListener(){
        return new SysLogListener(optLogDTO -> logService.saveLog(optLogDTO));
    }
}