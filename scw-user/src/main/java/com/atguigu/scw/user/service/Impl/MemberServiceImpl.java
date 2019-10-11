package com.atguigu.scw.user.service.Impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.atguigu.scw.user.bean.TMember;
import com.atguigu.scw.user.bean.TMemberAddress;
import com.atguigu.scw.user.bean.TMemberAddressExample;
import com.atguigu.scw.user.bean.TMemberExample;
import com.atguigu.scw.user.mapper.TMemberAddressMapper;
import com.atguigu.scw.user.mapper.TMemberMapper;
import com.atguigu.scw.user.service.MemberService;
import com.atguigu.scw.user.vo.request.MemberRequestVO;
@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	TMemberMapper memberMapper;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	TMemberAddressMapper memberAddressMapper;
	@Override
	public void saveMember(MemberRequestVO vo) {
		//先将vo和member一样的属性的值设置给member
		TMember member = new TMember();
		BeanUtils.copyProperties(vo, member);//将vo和member一样的属性(属性名和数据类型一样)的值拷贝给member
		//密码加密
		member.setUserpswd(passwordEncoder.encode(member.getUserpswd()));
		//设置其他的默认属性值
		member.setUsername(member.getLoginacct());
		member.setAuthstatus("0");// 0 未认证 ， 1认证中 ， 2已认证
		
		memberMapper.insertSelective(member);
	}

	@Override
	public TMember getMember(String loginacct, String userpswd) {
		
		//。判断用户名是否正确
		
		TMemberExample example = new TMemberExample();
		example.createCriteria().andLoginacctEqualTo(loginacct);
		List<TMember> list = memberMapper.selectByExample(example);
		if(CollectionUtils.isEmpty(list)) {
			return null;
		}
		TMember member = list.get(0);
		//。判断用户密码是否正确
		boolean b = passwordEncoder.matches(userpswd, member.getUserpswd());
		if(!b) {
			return null;
		}
	
		member.setUserpswd("[PROTECTED]");
		
		
		return member;
	}

	@Override
	public List<TMemberAddress> getAllAddress(Integer id) {

		TMemberAddressExample example = new TMemberAddressExample();
		example.createCriteria().andMemberidEqualTo(id);
		return memberAddressMapper.selectByExample(example );
	}

	

	

}
