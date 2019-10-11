package com.atguigu.scw.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.scw.order.bean.TOrder;
import com.atguigu.scw.order.bean.TOrderExample;
import com.atguigu.scw.order.mapper.TOrderMapper;
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	TOrderMapper orderMapper;
	
	@Override
	public void createorder(TOrder order) {

		orderMapper.insert(order);
		
	}

	@Override
	public void updateOrderStatus(String ordernum, Integer status) {

		TOrderExample example = new TOrderExample();
		example.createCriteria().andOrdernumEqualTo(ordernum);//。查询条件的封装
		TOrder record = new TOrder();
		record.setStatus(status+"");//。修改条件的封装
		orderMapper.updateByExampleSelective(record , example );
	}

}
