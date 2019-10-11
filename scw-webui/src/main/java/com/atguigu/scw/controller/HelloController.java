package com.atguigu.scw.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.scw.common.utils.AppResponse;
import com.atguigu.scw.response.vo.ProjectVo;
import com.atguigu.scw.service.ProjectServiceFeign;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HelloController {
	// 跳转到首页的方法

	@Autowired
	ProjectServiceFeign projectServiceFeign;

	@GetMapping(value = { "/index.html", "/" })
	public String toIndexPage(Model model) {
		// 。准备首页需要的数据，通过远程调用projectServiceFeign
		AppResponse<List<ProjectVo>> all = projectServiceFeign.all();

		System.out.println("头图为：" + all.toString());

		// 。将准备好的数据存入域中
		model.addAttribute("projects", all);
		log.info("所查询到的数据为{}" + all);
		return "index";
	}

	@RequestMapping("/hello")
	public String hello(Model model, HttpSession session) {
		model.addAttribute("ModelKey", "ModelValue");
		session.setAttribute("SessionKey", "SessionValue");
		session.getServletContext().setAttribute("appKey", "appValue");

		return "hello";
	}
}
