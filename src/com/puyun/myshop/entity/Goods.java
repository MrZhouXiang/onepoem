package com.puyun.myshop.entity;

import java.util.List;


/**
 * 
 * TODO 商品实体. Created on 2015年6月29日 下午2:32:41
 * 
 * @author 周震
 */
public class Goods {
	private String id; // 主键
	private String leixing; // 类型
	private int zhizuolx; // 制作类型
	private String zhizuolxname; // 制作类型名称
	private String mingcheng; // 名称
	private double jiage; // 价格
	private String chima; // 尺码
	private String shengao; // 身高
	private String tizhong; // 体重
	private String yaowei; // 腰围
	private String tunwei; // 臀围
	private String miaoshu; // 尺寸描述
	private String fabuzhelx; // 发布者类型
	private String fabuzheid; // 发布者ID
	private int isgongkai; // 是否公开
	private int isxiajia; // 是否下架
	private int zanct; // 赞的数量
	private String rukusj; // 入库时间
	private String yansemc; // 颜色名称
	private String yansecode; // 颜色代码
	private String shangpinms; // 商品描述
	private int yuanshejishiid; // 原设计师ID(买家秀时用到)
	private List<GoodsPhoto> url_list; // 商品图片集合
	private String username; //发布者用户名

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLeixing() {
		return leixing;
	}

	public void setLeixing(String leixing) {
		this.leixing = leixing;
	}

	public int getZhizuolx() {
		return zhizuolx;
	}

	public void setZhizuolx(int zhizuolx) {
		this.zhizuolx = zhizuolx;
	}

	public String getMingcheng() {
		return mingcheng;
	}

	public void setMingcheng(String mingcheng) {
		this.mingcheng = mingcheng;
	}

	public double getJiage() {
		return jiage;
	}

	public void setJiage(double jiage) {
		this.jiage = jiage;
	}

	public String getChima() {
		return chima;
	}

	public void setChima(String chima) {
		this.chima = chima;
	}

	public String getShengao() {
		return shengao;
	}

	public void setShengao(String shengao) {
		this.shengao = shengao;
	}

	public String getTizhong() {
		return tizhong;
	}

	public void setTizhong(String tizhong) {
		this.tizhong = tizhong;
	}

	public String getYaowei() {
		return yaowei;
	}

	public void setYaowei(String yaowei) {
		this.yaowei = yaowei;
	}

	public String getTunwei() {
		return tunwei;
	}

	public void setTunwei(String tunwei) {
		this.tunwei = tunwei;
	}

	public String getMiaoshu() {
		return miaoshu;
	}

	public void setMiaoshu(String miaoshu) {
		this.miaoshu = miaoshu;
	}

	public String getFabuzhelx() {
		return fabuzhelx;
	}

	public void setFabuzhelx(String fabuzhelx) {
		this.fabuzhelx = fabuzhelx;
	}

	public String getFabuzheid() {
		return fabuzheid;
	}

	public void setFabuzheid(String fabuzheid) {
		this.fabuzheid = fabuzheid;
	}

	public int getIsgongkai() {
		return isgongkai;
	}

	public void setIsgongkai(int isgongkai) {
		this.isgongkai = isgongkai;
	}

	public int getIsxiajia() {
		return isxiajia;
	}

	public void setIsxiajia(int isxiajia) {
		this.isxiajia = isxiajia;
	}

	public int getZanct() {
		return zanct;
	}

	public void setZanct(int zanct) {
		this.zanct = zanct;
	}

	public String getRukusj() {
		return rukusj;
	}

	public void setRukusj(String rukusj) {
		this.rukusj = rukusj;
	}

	public String getYansemc() {
		return yansemc;
	}

	public void setYansemc(String yansemc) {
		this.yansemc = yansemc;
	}

	public String getYansecode() {
		return yansecode;
	}

	public void setYansecode(String yansecode) {
		this.yansecode = yansecode;
	}

	public String getShangpinms() {
		return shangpinms;
	}

	public void setShangpinms(String shangpinms) {
		this.shangpinms = shangpinms;
	}

	public int getYuanshejishiid() {
		return yuanshejishiid;
	}

	public void setYuanshejishiid(int yuanshejishiid) {
		this.yuanshejishiid = yuanshejishiid;
	}

	public List<GoodsPhoto> getUrl_list() {
		return url_list;
	}

	public void setUrl_list(List<GoodsPhoto> url_list) {
		this.url_list = url_list;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getZhizuolxname() {
		return zhizuolxname;
	}

	public void setZhizuolxname(String zhizuolxname) {
		this.zhizuolxname = zhizuolxname;
	}

}
