package com.atguigu.scw.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.atguigu.scw.common.templates.OssTemplate;
@Configuration
public class AppConfig {
	
	
	//创建模板类设置到容器中去。并把相应的配置加载进去
	@ConfigurationProperties(prefix = "oss")//根据oss的前缀在配置文件里面找，如果找到就配置
	
	@Bean
	public OssTemplate getOssTemplate() {
		return new OssTemplate();
	}
	
	
	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
