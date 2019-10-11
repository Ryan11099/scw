package com.atguigu.scw.common.vo;

import java.io.Serializable;

import lombok.Data;

@Data

public class BaseVo implements Serializable{
	//需要使用AccessToken的信息
	
	private String accessToken;//表示用户登录成功的Token

}
