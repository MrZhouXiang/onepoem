package com.puyun.myshop.entity;

public class UserAvatar {
	private String id; // 主键
	private String userId; // 用户ID
	private String userType; //用户类型
	private String url; // 图片地址
	private int report_status; // 举报状态
	private int report_count; // 举报次数
	private int delete_status; // 删除状态
	private String create_time; // 创建时间
	private String tupianmc; //图片名称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getReport_status() {
		return report_status;
	}

	public void setReport_status(int report_status) {
		this.report_status = report_status;
	}

	public int getReport_count() {
		return report_count;
	}

	public void setReport_count(int report_count) {
		this.report_count = report_count;
	}

	public int getDelete_status() {
		return delete_status;
	}

	public void setDelete_status(int delete_status) {
		this.delete_status = delete_status;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getTupianmc() {
		return tupianmc;
	}

	public void setTupianmc(String tupianmc) {
		this.tupianmc = tupianmc;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	
}
