package com.puyun.clothingshow.entity;

import java.util.List;

/**
 * 
 * 照片冲印，订单对象 
 * @author  ldz
 * 创建时间:  2015-2-5
 */
public class Order
{
    private String id;//订单号
    private String payCode;//支付宝订单号
    private String status;//订单状态
    private String userId;//用户ID
//    private String prop;//属性
    private float transFee;//运费
    private float lastFee;//实付款(包括运费)
    private float realFee;//应付款
    private float totalFee;//总价（打折或享受活动前）
    private String create_date;//创建时间
    private String pay_date;//支付时间
    private String address;//收货地址（字符串）
    private String comment;//备注
    private String photoCount;//照片数量
    private String contact;//收件人姓名
    private String tel;//收件人手机号
//    private float price;//照片单价
    private String trans;//快递方式
    private int addressId;//地址ID
    private String city;//城市
    private String uploadCount;//已上传照片
    
//    private List<Goods> goods;//商品
    
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public String getPayCode()
    {
        return payCode;
    }
    public void setPayCode(String payCode)
    {
        this.payCode = payCode;
    }
    public String getStatus()
    {
        return status;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
    public String getUserId()
    {
        return userId;
    }
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    public String getCreate_date()
    {
        return create_date;
    }
    public void setCreate_date(String create_date)
    {
        this.create_date = create_date;
    }
    public String getAddress()
    {
        return address;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }
    public String getPay_date()
    {
        return pay_date;
    }
    public void setPay_date(String pay_date)
    {
        this.pay_date = pay_date;
    }
    public String getComment()
    {
        return comment;
    }
    public void setComment(String comment)
    {
        this.comment = comment;
    }
    public String getPhotoCount()
    {
        return photoCount;
    }
    public void setPhotoCount(String photoCount)
    {
        this.photoCount = photoCount;
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
    public float getTotalFee()
    {
        return totalFee;
    }
    public void setTotalFee(float totalFee)
    {
        this.totalFee = totalFee;
    }
    public float getTransFee()
    {
        return transFee;
    }
    public void setTransFee(float transFee)
    {
        this.transFee = transFee;
    }
    public String getTrans()
    {
        return trans;
    }
    public void setTrans(String trans)
    {
        this.trans = trans;
    }
    public int getAddressId()
    {
        return addressId;
    }
    public void setAddressId(int addressId)
    {
        this.addressId = addressId;
    }
    public String getCity()
    {
        return city;
    }
    public void setCity(String city)
    {
        this.city = city;
    }
//    public List<Goods> getGoods()
//    {
//        return goods;
//    }
//    public void setGoods(List<Goods> goods)
//    {
//        this.goods = goods;
//    }
    public float getRealFee()
    {
        return realFee;
    }
    public void setRealFee(float realFee)
    {
        this.realFee = realFee;
    }
    public String getUploadCount()
    {
        return uploadCount;
    }
    public void setUploadCount(String uploadCount)
    {
        this.uploadCount = uploadCount;
    }
	public float getLastFee() {
		return lastFee;
	}
	public void setLastFee(float lastFee) {
		this.lastFee = lastFee;
	}
    
}
