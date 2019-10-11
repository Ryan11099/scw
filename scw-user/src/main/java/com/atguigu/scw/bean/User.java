package com.atguigu.scw.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="用户实体类")

@Data

public class User {
	
	@ApiModelProperty(value="用户姓名")
	private String username;
	@ApiModelProperty(value="用户密码")
	private String password;
	@ApiModelProperty("用户ID")
	private Integer id;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public User(String username, String password, Integer id) {
		super();
		this.username = username;
		this.password = password;
		this.id = id;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", id=" + id + "]";
	}
	

}
