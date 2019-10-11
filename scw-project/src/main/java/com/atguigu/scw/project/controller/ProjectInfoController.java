package com.atguigu.scw.project.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.scw.common.utils.AppResponse;
import com.atguigu.scw.project.bean.TProject;
import com.atguigu.scw.project.bean.TProjectImages;
import com.atguigu.scw.project.bean.TProjectInitiator;
import com.atguigu.scw.project.bean.TReturn;
import com.atguigu.scw.project.service.ProjectService;
import com.atguigu.scw.project.vo.response.ProjectVo;
import com.atguigu.scw.project.vo.response.ReturnPayConfirmVo;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ProjectInfoController {

	@Autowired
	ProjectService projectInfoService;

	// 查询指定id的回报信息
	@GetMapping("/project/return/info")
	public AppResponse<ReturnPayConfirmVo> returnInfo(@RequestParam("returnId") Integer returnId) {
		// 1、查询回报信息
		TReturn tReturn = projectInfoService.getReturnById(returnId);
		// 2、查询项目信息
		TProject tProject = projectInfoService.getProjectInfo(tReturn.getProjectid());
		// 3、查询发起人信息
		TProjectInitiator tProjectInitiator = projectInfoService.getProjectInitiatorByPId(tProject.getId());

		// 4、封装数据并返回,所有的vo都是需要的数据进行封装起来
		ReturnPayConfirmVo confirmVo = new ReturnPayConfirmVo();
		confirmVo.setFreight(tReturn.getFreight());
		confirmVo.setMemberId(tProjectInitiator.getId());
		confirmVo.setMemberName(tProjectInitiator.getSelfintroduction());
		confirmVo.setNum(1);
		confirmVo.setPrice(tReturn.getSupportmoney());
		confirmVo.setProjectId(tProject.getId());
		confirmVo.setProjectName(tProject.getName());
		confirmVo.setProjectRemark(tProject.getRemark());
		confirmVo.setReturnId(tReturn.getId());
		confirmVo.setReturnName(tReturn.getContent());
		confirmVo.setReturnContent(tReturn.getContent());
		confirmVo.setSignalpurchase(tReturn.getSignalpurchase());

		Integer totalMoney = tReturn.getSupportmoney() * 1 + tReturn.getFreight();
		BigDecimal bigDecimal = new BigDecimal(totalMoney.toString());
		confirmVo.setTotalPrice(bigDecimal);

		return AppResponse.ok(confirmVo, "查询确认回报信息成功");
	}

	// 查询指定id的项目详情
	@ApiOperation("[+]获取项目信息详情")
	@GetMapping("/project/details/info/{projectId}")
	public AppResponse<ProjectVo> detailsInfo(@PathVariable("projectId") Integer projectId) {
		TProject p = projectInfoService.getProjectInfo(projectId);
		ProjectVo projectVo = new ProjectVo();

		// 1、查出这个项目的所有图片
		List<TProjectImages> projectImages = projectInfoService.getProjectImages(p.getId());
		for (TProjectImages tProjectImages : projectImages) {
			if (tProjectImages.getImgtype() == 0) {
				projectVo.setHeaderImage(tProjectImages.getImgurl());
			} else {
				List<String> detailsImage = projectVo.getDetailsImage();
				detailsImage.add(tProjectImages.getImgurl());
			}
		}

		// 2、项目的所有支持档位；
		List<TReturn> returns = projectInfoService.getProjectReturns(p.getId());
		projectVo.setReturns(returns);

		BeanUtils.copyProperties(p, projectVo);
		// 在此处对status进行设置,因为在封装的vo和对应的JavaBean中，status的属性值不同
		projectVo.setStatus(Byte.parseByte(p.getStatus()));
		return AppResponse.ok(projectVo, "查询项目详情成功");
	}

	@ApiOperation("[+]获取系统所有的项目")
	@GetMapping("/project/info/all")
	public AppResponse<List<ProjectVo>> all() {
		// 1、分步查询，先查出所有项目
		// 2、再查询这些项目图片
		List<ProjectVo> prosVo = new ArrayList<>();

		// 1、连接查询，所有的项目left join 图片表，查出所有的图片
		// left join：笛卡尔积 A*B 1000万*6 = 6000万
		// 大表(行数多)禁止连接查询； 小表驱动大表
		List<TProject> pros = projectInfoService.getAllProjects();

		for (TProject tProject : pros) {
			Integer id = tProject.getId();
			List<TProjectImages> images = projectInfoService.getProjectImages(id);

			System.out.println("项目的图片为：" + images);

			ProjectVo projectVo = new ProjectVo();
			BeanUtils.copyProperties(tProject, projectVo);

			for (TProjectImages tProjectImages : images) {
				if (tProjectImages.getImgtype() == 0) {
					projectVo.setHeaderImage(tProjectImages.getImgurl());
				} else {
					List<String> detailsImage = projectVo.getDetailsImage();
					detailsImage.add(tProjectImages.getImgurl());
				}
			}
			prosVo.add(projectVo);
		}
		return AppResponse.ok(prosVo, "项目列表查询成功");
	}

}
