package com.puyun.clothingshow.entity;

/**
 * 
 * 
 * @author ldz 创建时间: 2015-2-14
 */
/**
 * 系统提供颜色
 * 
 * @author 姓名
 * @version [版本号, 2015年6月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ColorForApp
{
    private String id;// 账号ID
    
    private String name;// 名称
    
    private String colorCode;//色值

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getColorCode()
    {
        return colorCode;
    }

    public void setColorCode(String colorCode)
    {
        this.colorCode = colorCode;
    }
    
    
}
