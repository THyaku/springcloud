package com.atguigu.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {

    @Bean
    //@LoadBalanced//使用@LoadBalanced注解赋予RestTemplate负载均衡的能力
    //注掉该注解，为了自己用cas自旋锁手写验证ribbon的轮询负载均衡底层代码
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}
