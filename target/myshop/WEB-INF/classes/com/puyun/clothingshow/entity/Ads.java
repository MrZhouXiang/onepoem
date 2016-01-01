package com.puyun.clothingshow.entity;

/**
 * 广告
 * 
 * @author 姓名
 * @version [版本号, 2015年6月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Ads
{
    int ID;//主键
    
    String Title;//标题
    
    String Types;//类型 1:图片,2:代码
    
    String Kuan;//宽
    
    String Gao;//高
    
    String Tupian;//图片
    
    String Shuoming;//说明
    
    String Url;//商户ID
    
    String Code;//代码
    
    String IsValue;//图片位置：11：左1，21：右1，22：右2
    
    String Point;//广告位置 如：首页：index    子页：newsinfo
    
    String Target;//目标
    
    private int IsDelete; //删除状态
    
    private String username; //商户名称

    public int getID()
    {
        return ID;
    }

    public void setID(int iD)
    {
        ID = iD;
    }

    public String getTitle()
    {
        return Title;
    }

    public void setTitle(String title)
    {
        Title = title;
    }

    public String getTypes()
    {
        return Types;
    }

    public void setTypes(String types)
    {
        Types = types;
    }

    public String getKuan()
    {
        return Kuan;
    }

    public void setKuan(String kuan)
    {
        Kuan = kuan;
    }

    public String getGao()
    {
        return Gao;
    }

    public void setGao(String gao)
    {
        Gao = gao;
    }

    public String getTupian()
    {
        return Tupian;
    }

    public void setTupian(String tupian)
    {
        Tupian = tupian;
    }

    public String getShuoming()
    {
        return Shuoming;
    }

    public void setShuoming(String shuoming)
    {
        Shuoming = shuoming;
    }

    public String getUrl()
    {
        return Url;
    }

    public void setUrl(String url)
    {
        Url = url;
    }

    public String getCode()
    {
        return Code;
    }

    public void setCode(String code)
    {
        Code = code;
    }

    public String getIsValue()
    {
        return IsValue;
    }

    public void setIsValue(String isValue)
    {
        IsValue = isValue;
    }

    public String getPoint()
    {
        return Point;
    }

    public void setPoint(String point)
    {
        Point = point;
    }

    public String getTarget()
    {
        return Target;
    }

    public void setTarget(String target)
    {
        Target = target;
    }

	public int getIsDelete() {
		return IsDelete;
	}

	public void setIsDelete(int isDelete) {
		IsDelete = isDelete;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
    
    
}
