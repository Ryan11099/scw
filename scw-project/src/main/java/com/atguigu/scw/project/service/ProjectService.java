package com.atguigu.scw.project.service;

import java.util.List;

import com.atguigu.scw.project.bean.TProject;
import com.atguigu.scw.project.bean.TProjectImages;
import com.atguigu.scw.project.bean.TProjectInitiator;
import com.atguigu.scw.project.bean.TReturn;
import com.atguigu.scw.project.vo.request.ProjectRedisStorageVo;

public interface ProjectService {

	void saveProject(ProjectRedisStorageVo bigVo);

	List<TProject> getAllProjects();

	List<TProjectImages> getProjectImages(Integer id);

	List<TReturn> getProjectReturns(Integer id);

	TProject getProjectInfo(Integer projectId);

	TReturn getReturnById(Integer returnId);

	TProjectInitiator getProjectInitiatorByPId(Integer id);



}
