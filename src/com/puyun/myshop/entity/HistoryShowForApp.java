package com.puyun.myshop.entity;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 
 * TODO 商品实体. Created on 2015年6月29日 下午2:32:41
 * 
 * @author 周翔
 */
public class HistoryShowForApp
{
    private String id;
    
    private int weeks;
    
    private int months;
    
    public int getMonths()
    {
        return months;
    }
    
    public void setMonths(int months)
    {
        this.months = months;
    }
    
    public int getWeeks()
    {
        return weeks;
    }
    
    public void setWeeks(int weeks)
    {
        this.weeks = weeks;
    }
    
    private int days;
    
    public int getDays()
    {
        return days;
    }
    
    public void setDays(int days)
    {
        this.days = days;
    }
    
    private String Fabuzheid;
    
    private String Fabuzhelx;
    
    private String username; // 发布者用户名
    
    @JsonIgnore
    private String YanseMc;
    
    @JsonIgnore
    private String YanseCode;
    
    @JsonIgnore
    private String ShangpinMs;
    
    @JsonIgnore
    private String Leixing;
    
    @JsonIgnore
    private String ZhizuoLx;
    
    @JsonIgnore
    private String Mingcheng;
    
    @JsonIgnore
    private String Shuliang;
    
    @JsonIgnore
    private String Jiage;
    
    @JsonIgnore
    private String Chima;
    
    @JsonIgnore
    private String IsGongKai;
    
    @JsonIgnore
    private String Miaoshu;
    
    @JsonIgnore
    private String Shengao;
    
    private String url; // 第一张图片路径
    
    @JsonIgnore
    private String[] url_list; // 商品图片集合
    
    @JsonIgnore
    private List<Comment> comment_list; // 评论列表
    
    @JsonIgnore
    private String ZanCt;// 点赞次数
    
    @JsonIgnore
    private String YuanshejishiID;// 原设计师ID
    
    @JsonIgnore
    private String RukuSj; // 创建时间
    
    private String new_discuss_count;//新评论个数
    
    
    public String getNew_discuss_count()
    {
        return new_discuss_count;
    }

    public void setNew_discuss_count(String new_discuss_count)
    {
        this.new_discuss_count = new_discuss_count;
    }

    public String getYuanshejishiID()
    {
        return YuanshejishiID;
    }
    
    public void setYuanshejishiID(String yuanshejishiID)
    {
        YuanshejishiID = yuanshejishiID;
    }
    
    public String getZanCt()
    {
        return ZanCt;
    }
    
    public void setZanCt(String zanCt)
    {
        ZanCt = zanCt;
    }
    
    public String getUrl()
    {
        return url;
    }
    
    public void setUrl(String url)
    {
        this.url = url;
    }
    
    public String[] getUrl_list()
    {
        return url_list;
    }
    
    public void setUrl_list(String[] url_list)
    {
        this.url_list = url_list;
    }
    
    public List<Comment> getComment_list()
    {
        return comment_list;
    }
    
    public void setComment_list(List<Comment> comment_list)
    {
        this.comment_list = comment_list;
    }
    
    public String getShengao()
    {
        return Shengao;
    }
    
    public void setShengao(String shengao)
    {
        Shengao = shengao;
    }
    
    public String getTizhong()
    {
        return Tizhong;
    }
    
    public void setTizhong(String tizhong)
    {
        Tizhong = tizhong;
    }
    
    public String getYaowei()
    {
        return Yaowei;
    }
    
    public void setYaowei(String yaowei)
    {
        Yaowei = yaowei;
    }
    
    public String getTunwei()
    {
        return Tunwei;
    }
    
    public void setTunwei(String tunwei)
    {
        Tunwei = tunwei;
    }
    
    @JsonIgnore
    private String Tizhong;
    
    @JsonIgnore
    private String Xiongwei;
    
    public String getXiongwei()
    {
        return Xiongwei;
    }
    
    public void setXiongwei(String xiongwei)
    {
        Xiongwei = xiongwei;
    }
    
    @JsonIgnore
    private String Yaowei;
    
    @JsonIgnore
    private String Tunwei;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getFabuzheid()
    {
        return Fabuzheid;
    }
    
    public void setFabuzheid(String fabuzheid)
    {
        Fabuzheid = fabuzheid;
    }
    
    public String getFabuzhelx()
    {
        return Fabuzhelx;
    }
    
    public void setFabuzhelx(String fabuzhelx)
    {
        Fabuzhelx = fabuzhelx;
    }
    
    public String getLeixing()
    {
        return Leixing;
    }
    
    public void setLeixing(String leixing)
    {
        Leixing = leixing;
    }
    
    public String getZhizuoLx()
    {
        return ZhizuoLx;
    }
    
    public void setZhizuoLx(String zhizuoLx)
    {
        ZhizuoLx = zhizuoLx;
    }
    
    public String getMingcheng()
    {
        return Mingcheng;
    }
    
    public void setMingcheng(String mingcheng)
    {
        Mingcheng = mingcheng;
    }
    
    public String getShuliang()
    {
        return Shuliang;
    }
    
    public void setShuliang(String shuliang)
    {
        Shuliang = shuliang;
    }
    
    public String getJiage()
    {
        return Jiage;
    }
    
    public void setJiage(String jiage)
    {
        Jiage = jiage;
    }
    
    public String getChima()
    {
        return Chima;
    }
    
    public void setChima(String chima)
    {
        Chima = chima;
    }
    
    public String getIsGongKai()
    {
        return IsGongKai;
    }
    
    public void setIsGongKai(String isGongKai)
    {
        IsGongKai = isGongKai;
    }
    
    public String getMiaoshu()
    {
        return Miaoshu;
    }
    
    public void setMiaoshu(String miaoshu)
    {
        Miaoshu = miaoshu;
    }
    
    public String getYanseMc()
    {
        return YanseMc;
    }
    
    public void setYanseMc(String yanseMc)
    {
        YanseMc = yanseMc;
    }
    
    public String getYanseCode()
    {
        return YanseCode;
    }
    
    public void setYanseCode(String yanseCode)
    {
        YanseCode = yanseCode;
    }
    
    public String getShangpinMs()
    {
        return ShangpinMs;
    }
    
    public void setShangpinMs(String shangpinMs)
    {
        ShangpinMs = shangpinMs;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public String getRukuSj()
    {
        return RukuSj;
    }
    
    public void setRukuSj(String rukuSj)
    {
        RukuSj = rukuSj;
    }
    
}
