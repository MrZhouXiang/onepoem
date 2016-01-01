package com.puyun.clothingshow.entity;

/**
 * 订单信息，导出excel时使用
 * 
 * @author ldz 创建时间: 2015-3-20
 */
public class OrderInfo
{
    private String id;// 订单号
    
    private String status;// 状态
    
    private String prop;// 商品信息
    
    private String comment;// 备注
    
    private String contact;// 收件人姓名
    
    private String tel;// 收件人手机号
    
    private String address;// 收货地址（字符串）
    
    public OrderInfo()
    {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public OrderInfo(String id, String status, String prop, String comment, String contact, String tel, String address)
    {
        super();
        this.id = id;
        this.status = status;
        this.prop = prop;
        this.comment = comment;
        this.contact = contact;
        this.tel = tel;
        this.address = address;
    }


    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public String getProp()
    {
        return prop;
    }
    
    public void setProp(String prop)
    {
        this.prop = prop;
    }
    
    public String getComment()
    {
        return comment;
    }
    
    public void setComment(String comment)
    {
        this.comment = comment;
    }
    
    public String getContact()
    {
        return contact;
    }
    
    public void setContact(String contact)
    {
        this.contact = contact;
    }
    
    public String getTel()
    {
        return tel;
    }
    
    public void setTel(String tel)
    {
        this.tel = tel;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    
}
