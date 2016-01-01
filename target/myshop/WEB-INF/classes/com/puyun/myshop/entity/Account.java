package com.puyun.myshop.entity;

/**
 * 登录账户类
 * 
 * @author 姓名
 * @version [版本号, 2015年6月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Account
{
    private String id;// 账号ID
    
    private String tel;// 登录电话
    
    private String buyer_id;// 买家id
    
    private String saler_id;// 卖家id
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTel()
    {
        return tel;
    }
    
    public void setTel(String tel)
    {
        this.tel = tel;
    }
    
    public String getBuyer_id()
    {
        return buyer_id;
    }
    
    public void setBuyer_id(String buyer_id)
    {
        this.buyer_id = buyer_id;
    }
    
    public String getSaler_id()
    {
        return saler_id;
    }
    
    public void setSaler_id(String saler_id)
    {
        this.saler_id = saler_id;
    }
    
}
