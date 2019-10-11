package com.atguigu.scw.response.vo;

import com.atguigu.scw.common.vo.BaseVo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString

public class MemberResponseVo extends BaseVo{
	
	private String loginacct;
	private String username;
	//private String accessToken;//用户访问的令牌
	private String email;
	private String userType;

}
