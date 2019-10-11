package com.atguigu.scw.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.atguigu.scw.common.utils.AppResponse;
import com.atguigu.scw.service.UserServiceFeign;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class UserController {
	
	@Autowired
	UserServiceFeign userServiceFeign; 
	
	//。跳转到登录页面
	@GetMapping(value = "/login.html" )
	public String toLoginPage() {
		return "user/login";
	}
	
	//。处理登录请求
	@PostMapping("/user/doLogin")
	public String doLogin(HttpSession session , Model model , String loginacct ,String userpswd){
		log.info("用户登录密码名为"+userpswd);
		log.info("用户登录名为"+loginacct);
		//。获取登陆的信息
		AppResponse<Object> response = userServiceFeign.doLogin(loginacct, userpswd);
		//。判断登录是否成功
		System.out.println("相应结果："+response.getCode());
		if(response!=null && response.getCode()==10000) {
			//将得到的登录信息数据存入session的域中去，以便后面的获取登录信息时可以用到
			session.setAttribute("member", response.getData());
			//。判断获取到的信息是否为结账页面跳转过来的。如果是结账页面跳转过来的，就给他跳转回去
			String path = (String) session.getAttribute("path");
			
			System.out.println("获取到的路径为："+path);
			
			if(StringUtils.isEmpty(path)) {
				//。如果为空，说明不是结账页面跳转过来的
				return "redirect:/index.html";//登录成功
			}else {
				//。删除完用完的session中的值
				//session.removeAttribute("path");
				//。跳转回结账的页面
				return "redirect:"+path;
			}
			
		}else {
			model.addAttribute("errorMsg", response.getMessage());
			return "user/login";//登录失败
		}
	}
}
