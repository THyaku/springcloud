package com.atguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RefreshScope//刷新配置功能
@Slf4j
public class ConfigClientController
{
    @Value("${config.info}")
    private String configInfo = "111";

    @GetMapping("/configInfo")
    public String getConfigInfo()
    {
        log.info("*****************************configInfo*****************************"+configInfo);
        return configInfo;
    }
}


