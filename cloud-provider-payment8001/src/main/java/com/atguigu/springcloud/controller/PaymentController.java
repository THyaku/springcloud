package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.entities.User;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 */
@RestController
@Slf4j
public class PaymentController{
    @Resource
    private PaymentService paymentService;

    //对于注册进eureka里面的微服务，可以通过服务发现来获得该服务的信息
    @Resource
    private DiscoveryClient discoveryClient;

    @Value("${server.port}")
    private String serverPort;//添加serverPort

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment)
    {
        int result = paymentService.create(payment);
        log.info("*****插入结果："+result);

        if(result > 0)
        {
                log.info("插入数据库成功");
                return new CommonResult(200,"插入数据库成功,serverPort: "+serverPort,result);
        }else{

            return new CommonResult(444,"插入数据库失败",null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id)
    {
        Payment payment = paymentService.getPaymentById(id);

        if(payment != null)
        {
            log.info("查询成功,serverPort:  "+serverPort,payment);
            return new CommonResult(200,"查询成功,serverPort:  "+serverPort,payment);
        }else{
            log.info("没有对应记录,查询ID: "+id);
            return new CommonResult(444,"没有对应记录,查询ID: "+id,null);
        }
    }

    @PostMapping(value = "/payment/login")
    public CommonResult<Payment> login(@RequestBody User user)
    {
        Payment payment = paymentService.getPaymentById(user.getUserCode());
        log.info(user.toString());
        if(payment != null && payment.getSerial() != null && payment.getSerial().equals(user.getPassword()))
        {
            log.info("登录成功,serverPort:  "+serverPort,payment);
            return new CommonResult(200,"查询成功,serverPort:  "+serverPort,payment);
        }else{
            log.info("没有对应记录,查询ID: "+user.getPassword());
            return new CommonResult(444,"没有对应记录,查询ID: "+user.getPassword(),null);
        }
    }

    //对于注册进eureka里面的微服务，可以通过服务发现来获得该服务的信息,全部  或者你指定获取
    @GetMapping(value = "/payment/discovery")
    public Object discovery()
    {
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            log.info("*****element: "+element);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }

        return this.discoveryClient;
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB() {
        return serverPort;//返回服务接口
    }

    // 测试feign超时控制，但是需要耗费3秒钟
    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout()
    {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }

    /**
     * 在微服务框架中，一个由客户端发起的请求在后端系统中会经过多个不同的的服务节点调用来协同产生最后的请求结果，
     * 每一个前段请求都会形成一条复杂的分布式服务调用链路，链路中的任何一环出现高延时或错误都会引起整个请求最后的失败。
     * Spring Cloud Sleuth提供了一套完整的服务跟踪的解决方案
     * SpringCloud从F版起已不需要自己构建Zipkin Server了，只需调用jar包即可 我现在用的H版
     * 完整的调用链路：表示一请求链路，一条链路通过Trace ld唯一标识，Span标识发起的请求信息，各span通过parent id关联起来，直白说就是链表
     * Sleuth负责收集   Zipkin负责展示
     * @return
     */
    @GetMapping("/payment/zipkin")
    public String paymentZipkin() {
        return "hi ,i'am paymentzipkin server fall back，welcome to here, O(∩_∩)O哈哈~";
    }
}
