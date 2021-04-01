package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 这儿有疑问  order服务添加了此@EnableHystrix注解，provider服务使用的@EnableCircuitBreaker
 * 这两个注解都是激活hystrix的功能，我们根据上面代码得出来结论，只需要在服务启动类加入@EnableHystrix注解即可，
 * 无须增加@EnableCircuitBreaker注解，本身@EnableHystrix注解已经涵盖了EnableCircuitBreaker的功能。
 */
@SpringBootApplication
@EnableFeignClients
//@EnableFeignClients("com.atguigu.springcloud.service")   # Feign客户端的两种写法
@EnableHystrix
public class OrderHystrixMain80
{
    public static void main(String[] args)
    {
        SpringApplication.run(OrderHystrixMain80.class,args);
    }
}

