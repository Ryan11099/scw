package com.atguigu.scw.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.scw.common.utils.AppResponse;
import com.atguigu.scw.response.vo.TMemberAddress;
import com.atguigu.scw.service.impl.UserServiceFeignExceptionHandler;

@FeignClient(value="SCW-USER",fallback =UserServiceFeignExceptionHandler.class)//标明自己所调用的方法来及哪个微服务项目，远程调用其他服务的方法
public interface UserServiceFeign {
	
	@PostMapping("/user/doLogin")
	public AppResponse<Object> doLogin(@RequestParam("loginacct")String loginacct , @RequestParam("userpswd")String userpswd);

	@PostMapping("/user/info/address")
	public AppResponse<List<TMemberAddress>> addressInfo(@RequestParam("accessToken")String accessToken);
	
}
