package com.atguigu.scw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;



@EnableEurekaServer   //开启注册中心的服务端

@SpringBootApplication
public class ScwRegisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScwRegisterApplication.class, args);
	}

}
