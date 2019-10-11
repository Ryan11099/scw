package com.atguigu.scw.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.scw.common.utils.AppResponse;
import com.atguigu.scw.response.vo.ProjectVo;
import com.atguigu.scw.response.vo.ReturnPayConfirmVo;
import com.atguigu.scw.response.vo.TReturn;
@FeignClient(value = "SCW-PROJECT")//指定自己要远程调用的方法的名字
public interface ProjectServiceFeign {

	@GetMapping("/project/info/all")
	public AppResponse<List<ProjectVo>> all();
	@GetMapping("/project/details/info/{projectId}")
	public AppResponse<ProjectVo> detailsInfo(@PathVariable("projectId") Integer projectId);
	@GetMapping("/project/return/info")
	public AppResponse<ReturnPayConfirmVo> returnInfo(@RequestParam("returnId")Integer returnId);
	
}
