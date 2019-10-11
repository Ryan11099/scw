package com.atguigu.scw.user.service;

import java.util.List;

import com.atguigu.scw.user.bean.TMember;
import com.atguigu.scw.user.bean.TMemberAddress;
import com.atguigu.scw.user.vo.request.MemberRequestVO;

public interface MemberService {

	void saveMember(MemberRequestVO vo);

	TMember getMember(String loginacct, String userpswd);

	List<TMemberAddress> getAllAddress(Integer id);

}
