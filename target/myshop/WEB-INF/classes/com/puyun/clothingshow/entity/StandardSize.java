package com.puyun.clothingshow.entity;

/**
 * 
 * TODO 参考尺码类. 
 * Created on 2015年10月9日 下午3:53:35
 * @author 周震
 */
public class StandardSize {
	private int id; // 主键
	private String size; // 尺码
	private int sex; // 性别, 1:男 2：女
	private String height; // 身高
	private String bust; // 胸围
	private String waist; // 腰围
	private String hip; // 臀围
	private String shoulder_breadth; // 肩宽

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getBust() {
		return bust;
	}

	public void setBust(String bust) {
		this.bust = bust;
	}

	public String getWaist() {
		return waist;
	}

	public void setWaist(String waist) {
		this.waist = waist;
	}

	public String getHip() {
		return hip;
	}

	public void setHip(String hip) {
		this.hip = hip;
	}

	public String getShoulder_breadth() {
		return shoulder_breadth;
	}

	public void setShoulder_breadth(String shoulder_breadth) {
		this.shoulder_breadth = shoulder_breadth;
	}

}
