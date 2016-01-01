package com.puyun.myshop.entity;

/**
 * 
 * TODO	管理员用户. 
 * Created on 2015年9月17日 下午4:22:11 
 * @author 周震
 */
public class AdminUser {
	private int id; // 主键
	private String loginName; // 登录名
	private String name;
	private String pwd; // 密码
	private int isLock; // 是否被锁定
	private String addTime; // 添加时间
	private int role; // 角色

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getIsLock() {
		return isLock;
	}

	public void setIsLock(int isLock) {
		this.isLock = isLock;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

}
