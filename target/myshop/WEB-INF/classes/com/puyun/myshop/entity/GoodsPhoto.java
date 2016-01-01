package com.puyun.myshop.entity;

public class GoodsPhoto {
	private String id; // 主键
	private String shangpinId; // 商品ID
	private String url; // 图片路径
	private int isGongkai; // 是否公开
	private int jubaoZt; // 举报状态
	private int jubaoCs; // 举报次数
	private int shanChuZt; // 删除状态
	private String ruKuSj; // 入库时间
	private String tupianMc; // 图片名称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShangpinId() {
		return shangpinId;
	}

	public void setShangpinId(String shangpinId) {
		this.shangpinId = shangpinId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getIsGongkai() {
		return isGongkai;
	}

	public void setIsGongkai(int isGongkai) {
		this.isGongkai = isGongkai;
	}

	public int getJubaoZt() {
		return jubaoZt;
	}

	public void setJubaoZt(int jubaoZt) {
		this.jubaoZt = jubaoZt;
	}

	public int getJubaoCs() {
		return jubaoCs;
	}

	public void setJubaoCs(int jubaoCs) {
		this.jubaoCs = jubaoCs;
	}

	public int getShanChuZt() {
		return shanChuZt;
	}

	public void setShanChuZt(int shanChuZt) {
		this.shanChuZt = shanChuZt;
	}

	public String getRuKuSj() {
		return ruKuSj;
	}

	public void setRuKuSj(String ruKuSj) {
		this.ruKuSj = ruKuSj;
	}

	public String getTupianMc() {
		return tupianMc;
	}

	public void setTupianMc(String tupianMc) {
		this.tupianMc = tupianMc;
	}

}
