package com.atguigu.scw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;



@EnableFeignClients
@EnableDiscoveryClient
@EnableHystrix
@EnableHystrixDashboard

@SpringBootApplication
public class ScwWebuiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScwWebuiApplication.class, args);
	}

}
