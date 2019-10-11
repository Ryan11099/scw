package com.atguigu.scw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableFeignClients
@MapperScan(basePackages = "com.atguigu.scw.order.mapper")
@EnableHystrix    //支持熔断保护
@EnableDiscoveryClient   //开启注册中心客户端的功能

@SpringBootApplication
public class ScwOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScwOrderApplication.class, args);
	}

}
