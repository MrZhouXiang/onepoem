package com.puyun.myshop.entity;

import java.util.List;

/**
 * 客户端模板类
 * 
 * @author 姓名
 * @version [版本号, 2015年6月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MouldForApp
{
    
    private String ID;
    
    private String ZhizuoLxID;
    
    private String Name;
    
    private String Jiage;
    
    private String description; //描述
    
    private List<MuBanTupian> url_list; // 图片集合
    private MuBanTupian url; // 第一张图片
    
    public MuBanTupian getUrl()
    {
        return url;
    }

    public void setUrl(MuBanTupian url)
    {
        this.url = url;
    }


    public List<MuBanTupian> getUrl_list()
    {
        return url_list;
    }

    public void setUrl_list(List<MuBanTupian> url_list)
    {
        this.url_list = url_list;
    }

    public String getID()
    {
        return ID;
    }
    
    public void setID(String iD)
    {
        ID = iD;
    }
    
    public String getZhizuoLxID()
    {
        return ZhizuoLxID;
    }
    
    public void setZhizuoLxID(String zhizuoLxID)
    {
        ZhizuoLxID = zhizuoLxID;
    }
    
    public String getName()
    {
        return Name;
    }
    
    public void setName(String name)
    {
        Name = name;
    }
    
    public String getJiage()
    {
        return Jiage;
    }
    
    public void setJiage(String jiage)
    {
        Jiage = jiage;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
    
}
