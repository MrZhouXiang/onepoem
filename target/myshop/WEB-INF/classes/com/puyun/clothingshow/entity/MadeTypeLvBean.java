package com.puyun.clothingshow.entity;

/**
 * 
 * TODO 制作类型等级实体. Created on 2015年8月20日 上午9:30:04
 * 
 * @author 周震
 */
public class MadeTypeLvBean {
	private int id; // 主键ID
	private int jishu; // 级数
	private int jingyanmin; // 升级最小值
	private int jingyanmax; // 升级最大值
	private double jiagongfei; // 加工费

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getJishu() {
		return jishu;
	}

	public void setJishu(int jishu) {
		this.jishu = jishu;
	}

	public int getJingyanmin() {
		return jingyanmin;
	}

	public void setJingyanmin(int jingyanmin) {
		this.jingyanmin = jingyanmin;
	}

	public int getJingyanmax() {
		return jingyanmax;
	}

	public void setJingyanmax(int jingyanmax) {
		this.jingyanmax = jingyanmax;
	}

	public double getJiagongfei() {
		return jiagongfei;
	}

	public void setJiagongfei(double jiagongfei) {
		this.jiagongfei = jiagongfei;
	}

}
