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
}
