package com.atguigu.scw.project.vo.request;

import com.atguigu.scw.common.vo.BaseVo;

import lombok.Data;
@Data
public class ProjectStepThreeInfoVo extends BaseVo{
	private String alipayAccount;//收款人账号
	
	private String idcard;//法人身份证号码
	
	private String projectToken;
	
}
