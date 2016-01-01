package com.puyun.clothingshow.entity;

/**
 * 抢单人员返回对象
 * 
 * @author 周翔
 * @version [版本号, 2015年7月7日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BuyerForOrder
{
    
    private String dingdanID; // 订单Id
    
    private String UserName; // 用户名
    
    private String youhuijia; // 优惠价
    
    private String jiagongSj; // 加工时间
    
    private String kuaidiFs; // 快递方式
    
    private String zhuangTai; // 状态
    
    private int dengji; //等级
    
    private String userId; //卖家用户ID
    
    private String tuikuanzht;// 退款状态
    
    public String getZhuangTai()
    {
        return zhuangTai;
    }
    
    public void setZhuangTai(String zhuangTai)
    {
        this.zhuangTai = zhuangTai;
    }
    
    public String getDingdanID()
    {
        return dingdanID;
    }
    
    public void setDingdanID(String dingdanID)
    {
        this.dingdanID = dingdanID;
    }
    
    public String getUserName()
    {
        return UserName;
    }
    
    public void setUserName(String userName)
    {
        UserName = userName;
    }
    
    public String getYouhuijia()
    {
        return youhuijia;
    }
    
    public void setYouhuijia(String youhuijia)
    {
        this.youhuijia = youhuijia;
    }
    
    public String getJiagongSj()
    {
        return jiagongSj;
    }
    
    public void setJiagongSj(String jiagongSj)
    {
        this.jiagongSj = jiagongSj;
    }
    
    public String getKuaidiFs()
    {
        return kuaidiFs;
    }
    
    public void setKuaidiFs(String kuaidiFs)
    {
        this.kuaidiFs = kuaidiFs;
    }

	public int getDengji() {
		return dengji;
	}

	public void setDengji(int dengji) {
		this.dengji = dengji;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTuikuanzht() {
		return tuikuanzht;
	}

	public void setTuikuanzht(String tuikuanzht) {
		this.tuikuanzht = tuikuanzht;
	}

	
}
