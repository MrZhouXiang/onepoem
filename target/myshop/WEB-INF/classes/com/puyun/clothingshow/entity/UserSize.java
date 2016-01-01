package com.puyun.clothingshow.entity;

import java.io.UnsupportedEncodingException;

public class UserSize
{
    String id;
    
    String loginId;
    
    String name; //名称
    
    String height;//身高
    
    String weight;//体重
    
    String normal_size; //尺码
    
    String waist;//腰围
    
    String bust;//胸围
    
    String hip;//臀围
    
    public String getHip()
    {
        return hip;
    }

    public void setHip(String hip)
    {
        this.hip = hip;
    }

    String description;//描述
    
    public String getDescription()
    {
//        byte[] bytes;
//        String str = "";
//        try
//        {
//            if (description != null)
//            {
//                bytes = description.getBytes("ISO-8859-1");
//                str = new String(bytes, "utf-8");
//            }
//        }
//        catch (UnsupportedEncodingException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        return description;
    }
    
    public void setDescription(String description)
    {
        
        this.description = description;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getLoginId()
    {
        return loginId;
    }
    
    public void setLoginId(String loginId)
    {
        this.loginId = loginId;
    }
    
    public String getName()
    {
//        byte[] bytes;
//        String str = "";
//        try
//        {
//            if (name != null)
//            {
//                bytes = name.getBytes("ISO-8859-1");
//                str = new String(bytes, "utf-8");
//            }
//            
//        }
//        catch (UnsupportedEncodingException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getHeight()
    {
        return height;
    }
    
    public void setHeight(String height)
    {
        this.height = height;
    }
    
    public String getWeight()
    {
        return weight;
    }
    
    public void setWeight(String weight)
    {
        this.weight = weight;
    }
    
    public String getNormal_size()
    {
        return normal_size;
    }
    
    public void setNormal_size(String normal_size)
    {
        this.normal_size = normal_size;
    }
    
    public String getWaist()
    {
        return waist;
    }
    
    public void setWaist(String waist)
    {
        this.waist = waist;
    }
    
    public String getBust()
    {
        return bust;
    }
    
    public void setBust(String bust)
    {
        this.bust = bust;
    }
    
}
