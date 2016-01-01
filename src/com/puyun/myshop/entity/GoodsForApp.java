package com.puyun.myshop.entity;

import java.util.List;
import java.util.Map;

/**
 * 
 * TODO 商品实体. Created on 2015年6月29日 下午2:32:41
 * 
 * @author 周翔
 */
/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author 姓名
 * @version [版本号, 2015年8月28日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class GoodsForApp
{
    
    // http://172.21.1.162:8080/myshop/goods/addGoods?token=a48daa17ab54487d8a48f87a59749564&Fabuzheid=1&Fabuzhelx=kehu&YanseMc=红色&Leixing=chengyixiu&ZhizuoLx=100&Mingcheng=&Shuliang=1&Jiage=600&Chima=undefined&IsGongKai=1&Shengao=fhj&Tizhong=hjkn&Yaowei=yb
    // &Xiongwei=&Tunwei=hbb&Miaoshu=undefined&ShangpinMs=&YanseCode=rgb(255, 0, 0)
    private String id;
    
    private float X;// 难度系数(根据制作类型获取)
    
    public float getX()
    {
        return X;
    }
    
    public void setX(float x)
    {
        X = x;
    }
    
    private String week;
    
    public String getWeek()
    {
        return week;
    }
    
    public void setWeek(String week)
    {
        this.week = week;
    }
    
    private String Fabuzheid;
    
    private String Fabuzhelx;
    
    private String username; // 发布者用户名
    
    private String YanseMc;
    
    private String YanseCode;
    
    private String ShangpinMs;
    
    private String Leixing;
    
    private int ZhizuoLx;
    
    private String Mingcheng;
    
    private String Shuliang;
    
    private float Jiage;
    
    private float Danjia;
    
    public float getDanjia()
    {
        return Danjia;
    }
    
    public void setDanjia(float danjia)
    {
        Danjia = danjia;
    }
    
    private String Chima;
    
    private String IsGongKai;
    
    private String Miaoshu;
    
    private String Shengao;
    
    private String url; // 第一张图片路径
    
    private String[] url_list; // 商品图片集合
    
    private List<GoodsPhoto> url_list2; // 商品图片集合
    
    public List<GoodsPhoto> getUrl_list2()
    {
        return url_list2;
    }
    
    public void setUrl_list2(List<GoodsPhoto> url_list2)
    {
        this.url_list2 = url_list2;
    }
    
    private String url_list_str; // 商品图片集合
    
    public String getUrl_list_str()
    {
        return url_list_str;
    }
    
    public void setUrl_list_str(String url_list_str)
    {
        this.url_list_str = url_list_str;
    }
    
    private List<Comment> comment_list; // 评论列表
    
    private String ZanCt;// 点赞次数
    
    private String YuanshejishiID;// 原设计师ID
    
    private String RukuSj; // 创建时间
    
    private float baseJiage;// 基础价格
    
    public float getBaseJiage()
    {
        return baseJiage;
    }
    
    public void setBaseJiage(float baseJiage)
    {
        this.baseJiage = baseJiage;
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
    
    private String Tizhong;
    
    private String Xiongwei;
    
    public String getXiongwei()
    {
        return Xiongwei;
    }
    
    public void setXiongwei(String xiongwei)
    {
        Xiongwei = xiongwei;
    }
    
    private String Yaowei;
    
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
    
    public int getZhizuoLx()
    {
        return ZhizuoLx;
    }
    
    public void setZhizuoLx(int zhizuoLx)
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
    
    public float getJiage()
    {
        return Jiage;
    }
    
    public void setJiage(float jiage)
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
