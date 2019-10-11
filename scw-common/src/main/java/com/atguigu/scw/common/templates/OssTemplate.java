package com.atguigu.scw.common.templates;

import java.io.InputStream;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import lombok.Data;

@Data
//提取上传文件的方法
public class OssTemplate {
	
	// Endpoint以杭州为例，其它Region请按实际情况填写。
	String endpoint;
	// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
	String accessKeyId;
	String accessKeySecret;
	String bucketName;
	String scheme;
	String imgsDir;
	//利用MultiPart来使得传递过去的访问地址使用Controller来接受
	
	public String updateImg(MultipartFile multipartFile) throws Exception {


	
	
	// 创建OSSClient实例。
	OSS ossClient = new OSSClientBuilder().build(scheme+endpoint, accessKeyId, accessKeySecret);
	//File file = new File("E:\\图片\\4.jpg");
	// 上传文件流。
	
	InputStream inputStream = multipartFile.getInputStream();
	
	//InputStream inputStream = new FileInputStream(file);
	String fileName = System.currentTimeMillis()+"_"+UUID.randomUUID().toString().replace("-", "")+"_"+multipartFile.getOriginalFilename();
	ossClient.putObject(bucketName, imgsDir+fileName, inputStream);
	//https://scw-20190615.oss-cn-shanghai.aliyuncs.com/imgs/1.gif
	// schemebucketName.endpoint/imgsDirfileName 
	//log.info("图片上传成功的地址：{}" ,scheme+bucketName+"."+endpoint+"/"+imgsDir+fileName );
	// 关闭OSSClient。
	
	String path = scheme+bucketName+"."+endpoint+"/"+imgsDir+fileName;
	
	//将正确的路径返回给Controller处理方法
	ossClient.shutdown();
	return path;
	
	}
}
