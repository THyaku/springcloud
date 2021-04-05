package com.atguigu.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GateWayConfig
{
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder)
    {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();

        routes.route("path_route_atguigu1",
                r -> r.path("/guonei")
                        .uri("http://news.baidu.com/guonei")).build();

        routes.route("path_route_atguigu2",//这个id按理说是唯一标识，但是我不唯一也没错
                r -> r.path("/guoji")
                        .uri("http://news.baidu.com/guoji")).build();

        return routes.build();
    }
}

