package com.atguigu.scw.project.vo.request;

import com.atguigu.scw.common.vo.BaseVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
public class ProjectReturnVo extends BaseVo{
	@ApiModelProperty(value = "0:虚拟回报  1：实物回报")
	private String type;//回报类型
	@ApiModelProperty(value = "获取发布人信息的令牌")
	private String projectToken;
	@ApiModelProperty(value = "支持金额")
	private Integer supportmoney;
	@ApiModelProperty(value = "回报内容")
	private String content;
	@ApiModelProperty(value = "单笔限购")
	private Integer singlepurchase;
	
	@ApiModelProperty(value = "回报数量")
	private Integer count;
	@ApiModelProperty(value = "每个id的限购数量")
	private Integer purchase;
	@ApiModelProperty(value = "邮费")
	private Integer freight;
	@ApiModelProperty(value = "开不开发票  默认 0 为不开")
	private String invoice;
	@ApiModelProperty(value = "回报时间")
	private Integer rtndate;
	

}
