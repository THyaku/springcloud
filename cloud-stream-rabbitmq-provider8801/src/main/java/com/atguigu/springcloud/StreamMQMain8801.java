package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 这儿解释下不加尤里卡客户端注解也能注册进注册中心的原因：
 *  当我们引用了EurekaClient的依赖后，并且我们的两个开关不手动置为false，
 *  Spring就会自动帮助我们执行EurekaAutoServiceRegistration类里的start（）方法，
 *  而注册的动作就是在该方法里完成的。
 *  所以，我们的EurekaClient工程，并不需要显式的在SpringBoot的启动类上标注@EnableEurekaClient注解。
 */
@SpringBootApplication
//@EnableEurekaClient
public class StreamMQMain8801 {
    public static void main(String[] args) {
        SpringApplication.run(StreamMQMain8801.class,args);
    }
}

