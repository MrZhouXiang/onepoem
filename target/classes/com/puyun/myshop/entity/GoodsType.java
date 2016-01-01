package com.puyun.myshop.entity;

/**
 * 
 * TODO 商品类型. Created on 2015年6月29日 下午1:25:06
 * 
 * @author 周震
 */
public class GoodsType {
	private String id; //主键ID
	private String name; //名称
	private float X; //经验值

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getX() {
		return X;
	}

	public void setX(float X) {
		this.X = X;
	}

}
