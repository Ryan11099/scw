package com.atguigu.scw;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.atguigu.scw.common.templates.SmsTemplates;
import com.atguigu.scw.user.bean.TMember;
import com.atguigu.scw.user.mapper.TMemberMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScwUserApplicationTests {

	@Autowired
	RedisTemplate<Object, Object> redisTemplate;
	@Autowired
	StringRedisTemplate strigTemplate;
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	SmsTemplates smsTemplates;
	

	@Test
	public void contextLoads() {

		strigTemplate.opsForValue().set("k3", "水泥呢");

		String expire = strigTemplate.opsForValue().get("k3");
		logger.info(expire);
		System.out.println(expire);

		
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("mobile", "15091637210");
		querys.put("param", "code:RuiRui is a beautiful girl , she will be next happy HelloKitty :)");
		querys.put("tpl_id", "TP1711063");
		smsTemplates.SendSms(querys);
		
		
//		String host = "http://dingxin.market.alicloudapi.com";
//		String path = "/dx/sendSms";
//		String method = "POST";
//		String appcode = "c7035f8e9f4646d18f7209f61627997c";
//		Map<String, String> headers = new HashMap<String, String>();
//		// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
//		headers.put("Authorization", "APPCODE " + appcode);
//		Map<String, String> querys = new HashMap<String, String>();
//		querys.put("mobile", "18092032152");
//		querys.put("param", "code:RuiRui is a beautiful girl , she will be next happy HelloKitty :)");
//		querys.put("tpl_id", "TP1711063");
//		Map<String, String> bodys = new HashMap<String, String>();
//
//		try {
//		    	*//**
//		    	* 重要提示如下:
//		    	* HttpUtils请从
//		    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
//		    	* 下载
//		    	*
//		    	* 相应的依赖请参照
//		    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
//		    	*//*
//			HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
//			System.out.println(response.toString());
//			// 获取response的body
//			 System.out.println(EntityUtils.toString(response.getEntity()));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

	}

	@Test
	public void testHttpClient() throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet("http://www.baidu.com");

			System.out.println("Executing request " + httpget.getRequestLine());

			// 响应结果解析工具类
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				@Override
				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}

			};
			String responseBody = httpclient.execute(httpget, responseHandler);
			System.out.println("----------------------------------------");
			System.out.println(responseBody);
		} finally {
			httpclient.close();
		}
	}
	
	@Autowired
	TMemberMapper mapper;
	@Test
	public void testMapper() {
		List<TMember> selectByExample = mapper.selectByExample(null);
		
	}
	

}
