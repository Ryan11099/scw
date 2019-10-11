package com.atguigu.scw.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.atguigu.scw.common.consts.AppConsts;
import com.atguigu.scw.common.templates.SmsTemplates;
import com.atguigu.scw.common.utils.AppResponse;
import com.atguigu.scw.common.utils.ScwUtils;
import com.atguigu.scw.user.bean.TMember;
import com.atguigu.scw.user.bean.TMemberAddress;
import com.atguigu.scw.user.service.MemberService;
import com.atguigu.scw.user.vo.request.MemberRequestVO;
import com.atguigu.scw.user.vo.response.MemberResponseVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
@Api(tags="用于出理用户号登陆的controller")   //。开启在swagger里面的注解
@RestController
@Slf4j
public class UserController {
	
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	@Autowired
	SmsTemplates smsTemplates;
	@Autowired
	MemberService memberService;
	//。处理查询地址信息的请求
	@ApiOperation("处理查询地址信息的请求")
	@PostMapping("/user/info/address")//。这里的@RequestParam用于将Controller的方法参数映射到此处
	public AppResponse<List<TMemberAddress>> addressInfo(@RequestParam("accessToken")String accessToken){
		String memberStr = stringRedisTemplate.opsForValue().get(accessToken);
		if(memberStr==null) {
			return AppResponse.fail(null, "登录超时");
		}
		TMember member = JSON.parseObject(memberStr, TMember.class);
		//。根据memberid进行查询地址信息
		 List<TMemberAddress> address = memberService.getAllAddress(member.getId());
		 return AppResponse.ok(address, "地址信息查询成功");
	}
	
	
	//。处理登录请求
	@ApiOperation("处理登录请求")
	@PostMapping("/user/doLogin")
	public AppResponse<Object> doLogin(@RequestParam("loginacct")String loginacct , @RequestParam("userpswd")String userpswd){
		//。调用Service进行查询
		TMember member = memberService.getMember(loginacct , userpswd);
		
		System.out.println("查询到的结果为："+member+"参数为："+userpswd);
		System.out.println("查询到的结果为："+member+"参数为："+loginacct);
		
		if(member==null) {
			return AppResponse.fail(null, "登录失败");
		}
		//。查询成功，创建储存信息的键。将用户存储到redis中去，方便以后进行查询验证
		String memberKey = UUID.randomUUID().toString().replace("-", "");
		//。将对象转化为json字符串
		String memberJson = JSON.toJSONString(member);
		//。将数据保存到redis中去
		stringRedisTemplate.opsForValue().set(memberKey, memberJson , 7 , TimeUnit.DAYS);
		//。返回token给前台系统
		MemberResponseVo responseVo = new MemberResponseVo();//。在登陆中需要的参数进行封装
		BeanUtils.copyProperties(member, responseVo);
		responseVo.setAccessToken(memberKey);
		
		return AppResponse.ok(responseVo, "登陆成功");
		
	}
	
	
	
	
	
	//2.处理注册的方法
	@PostMapping("/user/doRegist")
	@ApiOperation("描述注册的方法")
	public AppResponse<Object> doRegist(MemberRequestVO vo) {
		//。检验验证码是否正确
		String loginacct = vo.getLoginacct();
		String redisCode = stringRedisTemplate.opsForValue().get(AppConsts.CODE_PREFIX+loginacct+AppConsts.CODE_CODE_SUFFIX);
		if(StringUtils.isEmpty(redisCode)) {
			return AppResponse.fail(null, "验证码过期");
			
			//return "验证码过期";
		}
		if(!redisCode.equals(vo.getCode())) {
			return AppResponse.fail(null,"验证码错误");
			
			//return "验证码错误";
		}
		//。注册
		memberService.saveMember(vo);
		
		//。删除redis中的验证码
		stringRedisTemplate.delete(AppConsts.CODE_PREFIX+loginacct+AppConsts.CODE_CODE_SUFFIX);
		return AppResponse.ok(null, "注册成功");
		
		//return "注册成功";
	
	
	}
	
	
	//1.给手机发送验证码的方法
	@ApiOperation("用于处理发送验证码的请求")
	@ApiImplicitParams(value=@ApiImplicitParam(name="phoneNum" , required=true , value="手机号码"))
	@PostMapping("/user/sendSms")
	
	public Object sendSms(@RequestParam("phoneNum")String phoneNum) {
		//。先要利用正则表达式来判断手机的号码是否合格
		boolean b = ScwUtils.isMobilePhone(phoneNum);
		//。判断获得的手机号码是否为正确格式
		if(!b) {
			
			return AppResponse.fail(null, "手机号码格式不正确");
			
			//return "手机号码格式不正确";
		}
		//。判断完手机号码是否正确以后，检验当前手机号是否已经获取过验证码【如果一次都没有获取过，则就为他进行注册操作】
		//。设定一个手机号一天之内可以获取几次手机验证码
		String countStr = stringRedisTemplate.opsForValue().get(AppConsts.CODE_PREFIX+phoneNum+AppConsts.CODE_COUNT_SUFFIX);
		int count = 0;
		if(!StringUtils.isEmpty(countStr)) {
			//。如果为空，则为其注册...如果不为空，则转为数字，将生成的字符串转化为数字
			count = Integer.parseInt(countStr);//目的是为了在后面判断验证次数	
		}
		//。判断是否验证次数是在验证过是否为空过后再来进行验证
		//。判断验证次数是否超过规定三次
		if(count>=3) {
			return AppResponse.fail(null, "请不要频繁获取验证码");
		}
		//。判断在redis中是否还存在验证码，如果存在则说明验证码未过期
		Boolean hasKey = stringRedisTemplate.hasKey(AppConsts.CODE_PREFIX+phoneNum+AppConsts.CODE_CODE_SUFFIX);
		if(hasKey) {
			return "请勿重复提交验证码";
		}
		
		//。生成验证码
		String code = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
		
		System.out.println(code);
		
		//。验证通过，可以进行发送验证码
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("mobile", phoneNum);
		querys.put("param", AppConsts.CODE_PREFIX+code);
		querys.put("tpl_id", "TP1711063");
		boolean sendSms = smsTemplates.SendSms(querys);
		//。判断验证码是否发送成功
		if(!sendSms) {
			
			return AppResponse.fail(null, "短信验证码发送失败");
			
			//return "短信验证码发送失败";
		}
		
		//修改该手机号码发送验证码的次数
		Long expire = stringRedisTemplate.getExpire(AppConsts.CODE_PREFIX+phoneNum+AppConsts.CODE_COUNT_SUFFIX , TimeUnit.MINUTES);//获取次数的过期时间
		log.info("查詢到的過期時間:{}", expire);// -2代表已過期(未注册)
		if(expire==null  || expire<=0 ) {
			expire = (long) (24*60);
		}
		count++;
		
		//。发送成功的话就将验证码保存到redis中去
		stringRedisTemplate.opsForValue().set(AppConsts.CODE_PREFIX+phoneNum+AppConsts.CODE_CODE_SUFFIX, code, 5,TimeUnit.HOURS );
		return AppResponse.ok(null, "验证码发送成功");
		
		
	}

}
