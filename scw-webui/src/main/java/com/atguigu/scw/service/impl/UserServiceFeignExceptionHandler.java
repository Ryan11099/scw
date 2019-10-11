package com.atguigu.scw.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.atguigu.scw.common.utils.AppResponse;
import com.atguigu.scw.response.vo.TMemberAddress;
import com.atguigu.scw.service.UserServiceFeign;
@Service
public class UserServiceFeignExceptionHandler implements UserServiceFeign{

	@Override
	public AppResponse<Object> doLogin(String loginacct, String userpswd) {

		
		return AppResponse.fail(null, "登录失败");
	}

	@Override
	public AppResponse<List<TMemberAddress>> addressInfo(String accessToken) {

		
		return AppResponse.fail(null, "登录失败");
	}

}
