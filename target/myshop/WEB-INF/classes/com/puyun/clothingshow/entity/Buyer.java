package com.puyun.clothingshow.entity;

import java.util.List;

/**
 * 
 * TODO	买家类. 
 * Created on 2015年9月17日 下午4:24:00 
 * @author 周震
 */
public class Buyer
{
    private String id; // 主键
    
    private String loginId; // 登录ID
    
    private String name; // 用户名
    
    private String NameCs; // 用户名修改次数
    
    public String getNameCs()
    {
        return NameCs;
    }
    
    public void setNameCs(String nameCs)
    {
        NameCs = nameCs;
    }
    
    private String url; // 头像地址
    
    private UserAvatar url2; // 头像地址
    
    public UserAvatar getUrl2()
    {
        return url2;
    }
    
    public void setUrl2(UserAvatar url2)
    {
        this.url2 = url2;
    }
    
    private int lv; // 等级
    
    private String weibo_name; // 微博号
    
    private String weixin_name; // 微信号
    
    private String xingbie;// 性别
    
    private List<UserSize> size_list; // 尺码列表
    
    private int jingyan; // 经验
    
    private int status; // 状态, 1: 锁定, 0: 未锁定
    
    private String quyu; // 区域
    
    private String zhiye; // 职业
    
    private String unlockTime; // 解锁时间
    
    private String identity; //身份
    
    private String belong; //所属
    
    private String country; //国家
    
    private String province; //省
    
    private String city; //市
    
    private String is_international_academy; //是否为国际分院
    
    private String company_type; //企业商户类型
    
    private String tel; //手机号
    
    public List<UserSize> getSize_list()
    {
        return size_list;
    }
    
    public String getXingbie()
    {
        return xingbie;
    }
    
    public void setXingbie(String xingbie)
    {
        this.xingbie = xingbie;
    }
    
    public void setSize_list(List<UserSize> size_list)
    {
        this.size_list = size_list;
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
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getUrl()
    {
        return url;
    }
    
    public void setUrl(String url)
    {
        this.url = url;
    }
    
    public int getLv()
    {
        return lv;
    }
    
    public void setLv(int lv)
    {
        this.lv = lv;
    }
    
    public String getWeibo_name()
    {
        return weibo_name;
    }
    
    public void setWeibo_name(String weibo_name)
    {
        this.weibo_name = weibo_name;
    }
    
    public String getWeixin_name()
    {
        return weixin_name;
    }
    
    public void setWeixin_name(String weixin_name)
    {
        this.weixin_name = weixin_name;
    }
    
    public int getJingyan()
    {
        return jingyan;
    }
    
    public void setJingyan(int jingyan)
    {
        this.jingyan = jingyan;
    }
    
    public int getStatus()
    {
        return status;
    }
    
    public void setStatus(int status)
    {
        this.status = status;
    }
    
    public String getQuyu()
    {
        return quyu;
    }
    
    public void setQuyu(String quyu)
    {
        this.quyu = quyu;
    }
    
    public String getZhiye()
    {
        return zhiye;
    }
    
    public void setZhiye(String zhiye)
    {
        this.zhiye = zhiye;
    }
    
    public String getUnlockTime()
    {
        return unlockTime;
    }
    
    public void setUnlockTime(String unlockTime)
    {
        this.unlockTime = unlockTime;
    }

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getBelong() {
		return belong;
	}

	public void setBelong(String belong) {
		this.belong = belong;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getIs_international_academy() {
		return is_international_academy;
	}

	public void setIs_international_academy(String is_international_academy) {
		this.is_international_academy = is_international_academy;
	}

	public String getCompany_type() {
		return company_type;
	}

	public void setCompany_type(String company_type) {
		this.company_type = company_type;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
    
    
}
