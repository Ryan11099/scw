package com.atguigu.scw.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.atguigu.scw.bean.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags="第一个程序")
public class HelloController {
	@GetMapping("/hello")
	@ApiOperation(value="第一个方法")
	
	@ApiImplicitParams(value= {
			@ApiImplicitParam(name="亚洲",value="中国",required=true),
			@ApiImplicitParam(name="欧洲",value="英国")
	})
	
	public String hello(MultipartFile file) {
		
		return "Hemm";
	}
	
	@GetMapping("/login")
	@ApiOperation(value="登录方法")
	@ApiImplicitParams(value= {
			@ApiImplicitParam(name="userName" , value="JACK"),
			@ApiImplicitParam(name="userPassword" , value="1234567890")
	})
	public User login(String username , @RequestParam("password")String password) {
		return new User(username,password , 8);
	}
}
