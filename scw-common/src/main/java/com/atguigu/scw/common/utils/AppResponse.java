package com.atguigu.scw.common.utils;

import java.io.Serializable;

import com.atguigu.scw.common.consts.AppConsts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AppResponse<T> implements Serializable{
	
	
		private int code;
		private String message;
		private T data;
		//。响应成功的码
		public static <T> AppResponse<T> ok(T data , String message){
			return new AppResponse<T>(AppConsts.SUCCSS_CODE , message , data);
		}
		//。响应失败的码
		public static <T> AppResponse<T> fail(T data , String message){
			return new AppResponse<T>(AppConsts.FAIL_CODE , message , data);
		}
				
		
	}

