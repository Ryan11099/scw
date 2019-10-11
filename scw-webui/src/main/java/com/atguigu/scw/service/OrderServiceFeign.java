package com.atguigu.scw.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.scw.common.utils.AppResponse;
import com.atguigu.scw.response.vo.OrderConfirmVo;
@FeignClient("SCW-ORDER")
public interface OrderServiceFeign {

	//。创建订单并且保存到数据库中的方法
		@PostMapping("/order/createorder")
		public AppResponse<String> createOrder(@RequestBody OrderConfirmVo vo);
		//。更新订单状态
		@PostMapping("/order/updateOrderStatus")
		public AppResponse<String> updateOrderStatus(@RequestParam("ordernum")String ordernum , @RequestParam("status")Integer status );
}
