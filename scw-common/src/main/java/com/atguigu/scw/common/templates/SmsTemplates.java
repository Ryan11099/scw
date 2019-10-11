package com.atguigu.scw.common.templates;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.atguigu.scw.common.utils.HttpUtils;


@Component

public class SmsTemplates {
	
	@Value("${sms.host}")
	private String host;
	@Value("${sms.path}")
	private String path;
	@Value("${sms.method}")
	private String method;
	@Value("${sms.appcode}")
	private String appcode;
	
	
	//。发送手机验证码的方法
	public boolean SendSms(Map<String, String> querys) {
		
		//String host = "http://dingxin.market.alicloudapi.com";
		//String path = "/dx/sendSms";
		//String method = "POST";
		//String appcode = "c7035f8e9f4646d18f7209f61627997c";
		Map<String, String> headers = new HashMap<String, String>();
		// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
//		Map<String, String> querys = new HashMap<String, String>();
//		querys.put("mobile", "15091637210");
//		querys.put("param", "code:heheheheheheheheh");
//		querys.put("tpl_id", "TP1711063");
		Map<String, String> bodys = new HashMap<String, String>();

		try {
//		    	*//**
//		    	* 重要提示如下:
//		    	* HttpUtils请从
//		    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
//		    	* 下载
//		    	*
//		    	* 相应的依赖请参照
//		    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
//		    	*//*
			HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
			System.out.println(response.toString());
			
			return true;
			
			// 获取response的body
			// System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
			
		}

		
	}

}
