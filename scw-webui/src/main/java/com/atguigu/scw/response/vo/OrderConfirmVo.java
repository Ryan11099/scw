package com.atguigu.scw.response.vo;


import java.io.Serializable;

import com.atguigu.scw.common.vo.BaseVo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderConfirmVo implements Serializable{
	private String accessToken;//表示用户登录成功的Token
	private Integer returnid;
	private String ordernum;
	private String createdate;
	private Integer money;
	private Integer rtncount;
	private Integer status;
	private String address;
	private Integer invoice;
	private String invoictitle;
	private String remark;
	
	private Integer memberid;//购买者的id
	private Integer projectid;
}
