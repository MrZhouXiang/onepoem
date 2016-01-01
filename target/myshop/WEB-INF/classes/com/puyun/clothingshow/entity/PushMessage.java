package com.puyun.clothingshow.entity;


/**
 * 推送对象
 * 
 * @author ldz 创建时间: 2015-3-09
 */
public class PushMessage
{
    private String type;// 类型
    
    private String data;// 数据
    
    public PushMessage(String type, String data)
    {
        super();
        this.type = type;
        this.data = data;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getData()
    {
        return data;
    }
    
    public void setData(String data)
    {
        this.data = data;
    }
}
