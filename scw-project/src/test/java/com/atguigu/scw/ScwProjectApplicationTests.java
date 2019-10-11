package com.atguigu.scw;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.scw.project.bean.TTag;
import com.atguigu.scw.project.mapper.TTagMapper;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ScwProjectApplicationTests {
	
	@Autowired 
	TTagMapper mapper;
	

	@Test
	public void contextLoads() throws Exception {
		

		mapper.insertSelective(new TTag(1,2,"机器人"));
		

	// Endpoint以杭州为例，其它Region请按实际情况填写。
	//String endpoint = "oss-cn-shanghai.aliyuncs.com";
	// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
//	String accessKeyId = "LTAI4FgokhKHTUEMWZ3UD1pN";
//	String accessKeySecret = "2M7N2JyAePBY9aEmCIpwvWjsLFpP4w";
//	String bucketName = "scw-20190615yjh";
//	String scheme = "http://";
//	String imgsDir = "imgs/";
//	
//	// 创建OSSClient实例。
//	OSS ossClient = new OSSClientBuilder().build(scheme+endpoint, accessKeyId, accessKeySecret);
//	File file = new File("E:\\图片\\4.jpg");
//	// 上传文件流。
//	InputStream inputStream = new FileInputStream(file);
//	String fileName = System.currentTimeMillis()+"_"+UUID.randomUUID().toString().replace("-", "")+"_"+file.getName();
//	ossClient.putObject(bucketName, imgsDir+fileName, inputStream);
//	//https://scw-20190615.oss-cn-shanghai.aliyuncs.com/imgs/1.gif
//	// schemebucketName.endpoint/imgsDirfileName 
//	log.info("图片上传成功的地址：{}" ,scheme+bucketName+"."+endpoint+"/"+imgsDir+fileName );
//	// 关闭OSSClient。
//	ossClient.shutdown();
}


}
