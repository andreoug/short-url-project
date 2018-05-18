package com.neueda.shorty;

import com.neueda.shorty.aspect.LoggingHandler;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by gandreou on 16/05/2018.
 */
@Configuration
@EnableAspectJAutoProxy
@Aspect
public class AppConfig {

    @Bean
    public LoggingHandler loggingHandler() {
        return new LoggingHandler();
    }

}
