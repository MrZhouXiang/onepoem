package com.puyun.myshop.entity;

import org.codehaus.jackson.annotate.JsonIgnore;


/**
 * 等级类
 * 
 * @author  姓名
 * @version  [版本号, 2015年8月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class LVBean
{
    @JsonIgnore
    private String ID;//等级ID
    private String ZhizuoLxName;//制作类型名称
    @JsonIgnore
    private String YonghuID;//用户ID
    @JsonIgnore
    private String YonghuLX;//用户类型
    private String ZhizuoLxID;//制作类型ID
    private int Jingyan;//经验
    private int Dengji;//等级    
    private int Y = 1;//加工费系数
    private int JingyanMax;//升级经验
    
    public String getZhizuoLxName()
    {
        return ZhizuoLxName;
    }
    public void setZhizuoLxName(String zhizuoLxName)
    {
        ZhizuoLxName = zhizuoLxName;
    }
    public int getJingyanMax()
    {
        return JingyanMax;
    }
    public void setJingyanMax(int jingyanMax)
    {
        JingyanMax = jingyanMax;
    }
    public int getY()
    {
        return Y;
    }
    public void setY(int y)
    {
        Y = y;
    }
    public String getID()
    {
        return ID;
    }
    public void setID(String iD)
    {
        ID = iD;
    }
    public String getYonghuID()
    {
        return YonghuID;
    }
    public void setYonghuID(String yonghuID)
    {
        YonghuID = yonghuID;
    }
    public String getYonghuLX()
    {
        return YonghuLX;
    }
    public void setYonghuLX(String yonghuLX)
    {
        YonghuLX = yonghuLX;
    }
    public String getZhizuoLxID()
    {
        return ZhizuoLxID;
    }
    public void setZhizuoLxID(String zhizuoLxID)
    {
        ZhizuoLxID = zhizuoLxID;
    }
    public int getJingyan()
    {
        return Jingyan;
    }
    public void setJingyan(int jingyan)
    {
        Jingyan = jingyan;
    }
    public int getDengji()
    {
        return Dengji;
    }
    public void setDengji(int dengji)
    {
        Dengji = dengji;
    }
    
    

    
}
