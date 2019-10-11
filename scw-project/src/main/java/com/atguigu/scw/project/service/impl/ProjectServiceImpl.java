package com.atguigu.scw.project.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.scw.project.bean.TProject;
import com.atguigu.scw.project.bean.TProjectImages;
import com.atguigu.scw.project.bean.TProjectImagesExample;
import com.atguigu.scw.project.bean.TProjectInitiator;
import com.atguigu.scw.project.bean.TProjectInitiatorExample;
import com.atguigu.scw.project.bean.TProjectTag;
import com.atguigu.scw.project.bean.TProjectType;
import com.atguigu.scw.project.bean.TReturn;
import com.atguigu.scw.project.bean.TReturnExample;
import com.atguigu.scw.project.mapper.TProjectImagesMapper;
import com.atguigu.scw.project.mapper.TProjectInitiatorMapper;
import com.atguigu.scw.project.mapper.TProjectMapper;
import com.atguigu.scw.project.mapper.TProjectTagMapper;
import com.atguigu.scw.project.mapper.TProjectTypeMapper;
import com.atguigu.scw.project.mapper.TReturnMapper;
import com.atguigu.scw.project.service.ProjectService;
import com.atguigu.scw.project.vo.request.ProjectRedisStorageVo;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {
	//将bigVo拆分存到数据库的表中：
	/**
	 * <table tableName="t_project_images"></table>
		<table tableName="t_project"></table>
		<table tableName="t_project_tag"></table>
		<table tableName="t_project_type"></table>
		<table tableName="t_return"></table>
		<table tableName="t_project_initiator"></table>

	 */
	@Autowired
	TProjectMapper projectMapper;
	@Autowired
	TProjectImagesMapper projectImagesMapper;
	@Autowired
	TProjectTagMapper projectTagMapper;
	@Autowired
	TProjectTypeMapper projectTypeMapper;
	@Autowired
	TProjectInitiatorMapper projectInitiatorMapper;
	@Autowired
	TReturnMapper returnMapper;
	@Override
	public void saveProject(ProjectRedisStorageVo bigVo) {
		//1、先保存project并得到project的id  t_project
		//bigVo的属性名和javabean的属性名都是一样的
		TProject tProject = new TProject();
		BeanUtils.copyProperties(bigVo, tProject);
		//设置需要手动初始化的值
		tProject.setMoney((long)bigVo.getMoney());
		tProject.setStatus("0");
		tProject.setFollower(0);
		tProject.setCreatedate(com.atguigu.scw.common.utils.AppDateUtils.getFormatTime());
		projectMapper.insertSelective(tProject);
		
		System.out.println("11111111111111111");
		
		Integer projectId = tProject.getId();//获取自增的主键id值
		
		System.out.println("自增主键值为："+projectId);
		
		//2、存图片地址t_project_images
		//创建保存图片信息的集合
		List<TProjectImages> imgs = new ArrayList<TProjectImages>();
		TProjectImages headerImage = new TProjectImages();
		//保存头图：
		headerImage.setImgtype((byte) 0);
		headerImage.setProjectid(projectId);
		headerImage.setImgurl(bigVo.getHeaderImage());
		imgs.add(headerImage);
		log.info("projectid:"+projectId);
		//projectImagesMapper.insertSelective(tProjectImages);
		//保存详情图:  批量插入 insert into t_project_images()  values() , () , ();
		for(String imgUrl:bigVo.getDetailsImage()) {
			TProjectImages detailsImage = new TProjectImages();
			detailsImage.setImgtype((byte) 1);
			detailsImage.setProjectid(projectId);
			detailsImage.setImgurl(imgUrl);
			imgs.add(detailsImage);
			
			System.out.println("3232323232323232");
	   }
		//将图片集合存到数据库中
		projectImagesMapper.insertImgs(imgs);
		
		System.out.println("22222222222222222");
		
		//3、存项目tags：t_project_tag
		//将项目的tag信息转为TProjectTag对象的集合
		List<Integer> tagids = bigVo.getTagids();
		for (Integer tagid : tagids) {
			TProjectTag tProjectTag = new TProjectTag();
			tProjectTag.setProjectid(projectId);
			tProjectTag.setTagid(tagid);
			projectTagMapper.insertSelective(tProjectTag);
		}
		
		System.out.println("33333333333333333");
		
		//4、存项目type：t_project_type
		List<Integer> typeids = bigVo.getTypeids();
		for (Integer typeid : typeids) {
			TProjectType tProjectType = new TProjectType();
			tProjectType.setProjectid(projectId);
			tProjectType.setTypeid(typeid);
			projectTypeMapper.insertSelective(tProjectType);
		}
		
		System.out.println("4444444444444444444");
		
		//5、存项目发起人信息：t_project_initiator
		TProjectInitiator projectInitiator = bigVo.getProjectInitiator();
		//！！一定要将项目发起人所属的项目id设置给发起人
		projectInitiator.setProjectid(projectId);
		projectInitiatorMapper.insertSelective(projectInitiator);
		
		System.out.println("55555555555555555555555");
		
		//6、存项目回报：t_return
		List<TReturn> projectReturns = bigVo.getProjectReturns();
		for (TReturn tReturn : projectReturns) {
			tReturn.setProjectid(projectId);
			returnMapper.insertSelective(tReturn);
		}
		
		System.out.println("6666666666666666666666666");
		
	}
	@Override
	public List<TProject> getAllProjects() {
		//。查询所有的项目的具体列表
		
		return projectMapper.selectByExample(null);
	}
	@Override
	public List<TProjectImages> getProjectImages(Integer id) {
		//。获取项目的图片
		TProjectImagesExample example = new TProjectImagesExample();
		example.createCriteria().andProjectidEqualTo(id);
		return projectImagesMapper.selectByExample(example );
	}
	@Override
	public List<TReturn> getProjectReturns(Integer id) {
		TReturnExample example = new TReturnExample();
		example.createCriteria().andProjectidEqualTo(id);
		
		return returnMapper.selectByExample(example );
	}
	@Override
	public TProject getProjectInfo(Integer projectId) {
		
		return projectMapper.selectByPrimaryKey(projectId);
	}
	@Override
	public TReturn getReturnById(Integer returnId) {

		TReturn tReturn = returnMapper.selectByPrimaryKey(returnId);
		return tReturn;
	}
	@Override
	public TProjectInitiator getProjectInitiatorByPId(Integer id) {
		TProjectInitiatorExample example = new TProjectInitiatorExample();
		example.createCriteria().andProjectidEqualTo(id);
		//查询发起人信息
		TProjectInitiator initiator = projectInitiatorMapper.selectByExample(example).get(0);
		return initiator;
	}

}
