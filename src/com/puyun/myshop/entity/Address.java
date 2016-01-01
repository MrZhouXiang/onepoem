package com.puyun.myshop.entity;

/**
 * 地址对象
 * 
 * @author 周翔
 * @version [版本号, 2015年7月7日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Address
{
    String id;// 主键
    
    String yonghuid;// 用户id
    
    String yonghulx;// 用户类型
    
    String shouhuoren;// 收货人
    
    String shouhuodizhi;// 收货地址
    
    String dianhua;// 收货电话
    
    String youbian;// 邮编
    
    String shengshiqu;// 省市区
    
    public String getShengshiqu()
    {
        return shengshiqu;
    }
    
    public void setShengshiqu(String shengshiqu)
    {
        this.shengshiqu = shengshiqu;
    }
    
    public String getYoubian()
    {
        return youbian;
    }
    
    public void setYoubian(String youbian)
    {
        this.youbian = youbian;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getYonghuid()
    {
        return yonghuid;
    }
    
    public void setYonghuid(String yonghuid)
    {
        this.yonghuid = yonghuid;
    }
    
    public String getYonghulx()
    {
        return yonghulx;
    }
    
    public void setYonghulx(String yonghulx)
    {
        this.yonghulx = yonghulx;
    }
    
    public String getShouhuoren()
    {
        return shouhuoren;
    }
    
    public void setShouhuoren(String shouhuoren)
    {
        this.shouhuoren = shouhuoren;
    }
    
    public String getShouhuodizhi()
    {
        return shouhuodizhi;
    }
    
    public void setShouhuodizhi(String shouhuodizhi)
    {
        this.shouhuodizhi = shouhuodizhi;
    }
    
    public String getDianhua()
    {
        return dianhua;
    }
    
    public void setDianhua(String dianhua)
    {
        this.dianhua = dianhua;
    }
    
}
