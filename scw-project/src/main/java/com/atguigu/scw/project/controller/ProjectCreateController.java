package com.atguigu.scw.project.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.atguigu.scw.common.consts.AppConsts;
import com.atguigu.scw.common.templates.OssTemplate;
import com.atguigu.scw.common.utils.AppResponse;
import com.atguigu.scw.common.utils.ScwUtils;
import com.atguigu.scw.project.bean.TMember;
import com.atguigu.scw.project.bean.TReturn;
import com.atguigu.scw.project.service.ProjectService;
import com.atguigu.scw.project.vo.request.ProjectBaseInfoVo;
import com.atguigu.scw.project.vo.request.ProjectRedisStorageVo;
import com.atguigu.scw.project.vo.request.ProjectReturnVo;
import com.atguigu.scw.project.vo.request.ProjectStepThreeInfoVo;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ProjectCreateController {

	@Autowired
	OssTemplate ossTemplate;
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	@Autowired
	ProjectService  projectService;
	
	
	//。收集法人信息
	
	@PostMapping("/project/create/submitProject")
	@ApiOperation("第三步：收集法人信息并提交")
	public AppResponse<Object> stepThree(Integer submitType  ,ProjectStepThreeInfoVo vo){
		//1.1判断用户的登录状态
	
		log.info("收集到的信息为："+vo);
		
		if(submitType==1){
			// 判断用户是否登录
			if (StringUtils.isEmpty(vo.getAccessToken())) {
				return AppResponse.fail(null, "发布项目必须登录!!!");
			}
			// 验证用户是否登录
			String memberStr = stringRedisTemplate.opsForValue().get(vo.getAccessToken());
			if (StringUtils.isEmpty(memberStr)) {
				return AppResponse.fail(null, "登录超时，请重新登录!!!");
			}
			//获取redis中缓存的bigVo对象，将法人信息设置给bigVo
			ProjectRedisStorageVo bigVo = ScwUtils.getBeanFromRedis(stringRedisTemplate, AppConsts.PROJECT_CREATE_TEMP_PREFIX+vo.getProjectToken(), ProjectRedisStorageVo.class);
			
			log.info("从临时库里获得的数据为："+vo.getAccessToken());
			log.info("从临时库里获得的数据为："+vo.getAlipayAccount());
			log.info("从临时库里获得的数据为："+vo.getIdcard());
			log.info("从临时库里获得的数据为："+vo.getProjectToken());
			
			BeanUtils.copyProperties(vo, bigVo);
			//将bigVo的数据转为数据库表对应的javabean对象，然后调用mapper的方法存到对应的表中
			projectService.saveProject(bigVo);
			//删除reids中的缓存
			
			System.out.println("123456475869708");
			
			stringRedisTemplate.delete(AppConsts.PROJECT_CREATE_TEMP_PREFIX+vo.getProjectToken());
			
			return AppResponse.ok(bigVo, "项目发布成功");
		}else {
			
			return AppResponse.ok(null, "保存项目草稿成功");
		}
	
	}

	// 第一步：收集项目及发起人信息: 修改 上一步的big vo的相对应的属性值
	@ApiOperation("第一步:项目及发起人信息")
	@PostMapping("/project/create/stepOne")
	public AppResponse<Object> stepOne(ProjectBaseInfoVo vo) {// 创建收集 项目及发起人信息的vo
		// 判断用户的登录状态
		if (StringUtils.isEmpty(vo.getAccessToken())) {
			return AppResponse.fail(null, "发布项目必须登录!!!");
		}
		// 验证用户是否登录
		String memberStr = stringRedisTemplate.opsForValue().get(vo.getAccessToken());
		if (StringUtils.isEmpty(memberStr)) {
			return AppResponse.fail(null, "登录超时，请重新登录!!!");
		}
		// 1、获取redis中缓存的bigVo对象
		ProjectRedisStorageVo bigVo = ScwUtils.getBeanFromRedis(stringRedisTemplate,
				AppConsts.PROJECT_CREATE_TEMP_PREFIX + vo.getProjectToken(), ProjectRedisStorageVo.class);
		// 2、将收集到的数据拷贝给bigVo对象
		BeanUtils.copyProperties(vo, bigVo);
		// 3、将修改后的bigVo设置到redis中替换之前的
		String bigVoStr = JSON.toJSONString(bigVo);

		log.info("第一步数据封装之后的bigvo对象：{}", bigVoStr);
		stringRedisTemplate.opsForValue().set(AppConsts.PROJECT_CREATE_TEMP_PREFIX + vo.getProjectToken(), bigVoStr);
		// 4、成功响应
		return AppResponse.ok(bigVo, "项目及发起人信息添加成功");
	}

	// 第二步：收集回报信息
	@ApiOperation("第二步:回报信息")
	@PostMapping("/project/create/stepTwo")
	public AppResponse<Object> stepTwo(@RequestBody List<ProjectReturnVo> rtns) {
		// 判断用户是否登录
		if (CollectionUtils.isEmpty(rtns)) {
			return AppResponse.fail(null, "请上传回报信息!!");
		}
		ProjectReturnVo vo = rtns.get(0);
		if (StringUtils.isEmpty(vo.getAccessToken())) {
			return AppResponse.fail(null, "发布项目必须登录!!!");
		}
		// 验证用户是否登录
		String memberStr = stringRedisTemplate.opsForValue().get(vo.getAccessToken());
		if (StringUtils.isEmpty(memberStr)) {
			return AppResponse.fail(null, "登录超时，请重新登录!!!");
		}
		// 获取reids中bigVo对象
		String projectToken = vo.getProjectToken();
		ProjectRedisStorageVo bigVo = ScwUtils.getBeanFromRedis(stringRedisTemplate,
				AppConsts.PROJECT_CREATE_TEMP_PREFIX + projectToken, ProjectRedisStorageVo.class);
		// 将ProjectReturnVo集合转为Tretuen集合设置给bigVo
		// 将接受到的回报的vo对象集合转为TReturn集合
		ArrayList<TReturn> list = new ArrayList<TReturn>();
		for (ProjectReturnVo projectReturnVo : rtns) {
			TReturn tReturn = new TReturn();
			BeanUtils.copyProperties(projectReturnVo, tReturn);
			list.add(tReturn);
		}
		bigVo.setProjectReturns(list);
		// 将修改后的bigVo重新设置到redis中缓存
		ScwUtils.saveBeanToRedis(bigVo, stringRedisTemplate, bigVo.getProjectToken(),
				AppConsts.PROJECT_CREATE_TEMP_PREFIX);

		return AppResponse.ok(bigVo, "回报信息上传成功");
	}
	
	
	
	
	//2.阅读并同意协议
	// 阅读并同意协议: 创建big vo对象 ，并分配唯一的token，将token返回
		@ApiOperation("阅读并同意协议")
		@PostMapping("/project/create/initProject")
		public AppResponse<Object> initProject(String accessToken) {// 必须提供accessToken
			if (StringUtils.isEmpty(accessToken)) {
				return AppResponse.fail(null, "发布项目必须登录!!!");
			}
			// 验证用户是否登录
			String memberStr = stringRedisTemplate.opsForValue().get(accessToken);
			if (StringUtils.isEmpty(memberStr)) {
				return AppResponse.fail(null, "登录超时，请重新登录!!!");
			}
			// 创建bigvo对象，设置初始化的属性值
			ProjectRedisStorageVo bigVo = new ProjectRedisStorageVo();
			bigVo.setAccessToken(accessToken);
			// 转为TMember对象
			TMember member = JSON.parseObject(memberStr, TMember.class);
			bigVo.setMemberid(member.getId());
			// 将vo信息存到redis中
			// 创建唯一字符串：projectToken
			String projectToken = UUID.randomUUID().toString().replace("-", "");
			bigVo.setProjectToken(projectToken);
			// 将vo对象转为json字符串才可以存入redis: 存之前一定要将相关的属性值设置之后再转换
			String bigVoStr = JSON.toJSONString(bigVo);
			stringRedisTemplate.opsForValue().set(AppConsts.PROJECT_CREATE_TEMP_PREFIX + projectToken, bigVoStr);
			// 返回成功信息+projectToken
			return AppResponse.ok(bigVo, "项目初始化成功");
		}
	
	
	
	//1.上传提交项目的图片
	@PostMapping("/project/uploadImags")
	@ApiOperation("上传项目图片")
	public AppResponse<Object> upLoadImgs(MultipartFile[] imgs){
		
		if(imgs!=null) {
					
		List<String> paths = new ArrayList<String>();//创造一个集合，用来存放需要上传的图片
		
		int i = 0;//用来获取成功的条数
		for(MultipartFile img : imgs) {
			String imgPath;
			try {
				imgPath = ossTemplate.updateImg(img);//调用方法来实现上传图片的请求
				paths.add(imgPath);
				i++;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return AppResponse.ok(paths, "上传成功"+i+"张，失败"+(imgs.length-i)+"张");
		
		}else {
			return AppResponse.fail(null, "数据为空,上传失败");
		}
	
	}

}
