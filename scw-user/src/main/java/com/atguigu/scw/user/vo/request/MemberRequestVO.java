package com.atguigu.scw.user.vo.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString

public class MemberRequestVO {

	private String loginacct;
	private String userpswd;
	private String code;
	private String email;
	private String usertype;
	
}
