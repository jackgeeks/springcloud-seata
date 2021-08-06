package com.jackgreek.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: jackgeeks
 * @ProjectName: gulishop
 * @Package: com.jackgreek.gulishop.product.config
 * @ClassName: FeignConfig
 * @Description: @todo
 * @CreateDate: 2021/4/18 18:05
 * @Version: 1.0
 */
@Configuration
public class FeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}

