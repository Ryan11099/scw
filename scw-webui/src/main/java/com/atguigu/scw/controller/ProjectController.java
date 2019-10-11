package com.atguigu.scw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.atguigu.scw.common.utils.AppResponse;
import com.atguigu.scw.response.vo.ProjectVo;
import com.atguigu.scw.service.ProjectServiceFeign;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class ProjectController {
	
	@Autowired
	ProjectServiceFeign  projectServiceFeign;
	
	@GetMapping("/project/info/{id}")
	public String detailsProject(Model model,@PathVariable("id") Integer id) {
		//。远程调用project中的方法
		AppResponse<ProjectVo> response = projectServiceFeign.detailsInfo(id);
		//。将发布的项目信息的值存到域中共享到前端页面中去
		log.info("查询到的详情为{}  +  {}"+id+response.getData());
		model.addAttribute("project", response.getData());
		//。在前端页面进行显示
		return "project/project";
	}

}
