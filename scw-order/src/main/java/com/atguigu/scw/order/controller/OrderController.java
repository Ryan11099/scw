package com.atguigu.scw.order.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.scw.common.utils.AppResponse;
import com.atguigu.scw.common.utils.ScwUtils;
import com.atguigu.scw.order.bean.TMember;
import com.atguigu.scw.order.bean.TOrder;
import com.atguigu.scw.order.service.OrderService;
import com.atguigu.scw.order.vo.request.OrderConfirmVo;

@RestController
public class OrderController {
	/*
	 * 想要传递对象1
	 * 1.必须实现序列化接口
	 * 2必须使用@RequestBody
	 */
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	@Autowired
	OrderService orderService;
	//。创建更新订单状态的方法
	@PostMapping("/order/updateOrderStatus")
	public AppResponse<String> updateOrderStatus(@RequestParam("ordernum")String ordernum , @RequestParam("status")Integer status ){
		orderService.updateOrderStatus(ordernum , status);
		return AppResponse.ok(null, "状态更新成功");
	}
	
	
	//。创建订单并且保存到数据库中的方法
	@PostMapping("/order/createorder")
	public AppResponse<String> createOrder(@RequestBody OrderConfirmVo vo){
		//。vo中没有设置memberid，所以从member中将memberid获取出来设置给vo
		TMember member = ScwUtils.getBeanFromRedis(stringRedisTemplate, vo.getAccessToken(), TMember.class);
		vo.setMemberid(member.getId());
		
		
		//。将vo转化为TOrder对象。因为在数据库中进行存的话。必须与数据库中的值一致
		TOrder order = new TOrder();
		BeanUtils.copyProperties(vo, order);
		//。因为在存放时vo的属性值与order中有的属性值不同，需要重新设置
		order.setStatus(vo.getStatus()+"");
		order.setInvoice(vo.getInvoice()+"");
		//。调用业务层将数据进行持久化保存
		orderService.createorder(order);
		
		return AppResponse.ok(null, "订单创建成功");
	}

}
