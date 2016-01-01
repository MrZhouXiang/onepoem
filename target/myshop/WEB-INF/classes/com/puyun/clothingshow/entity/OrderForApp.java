package com.puyun.clothingshow.entity;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author 姓名
 * @version [版本号, 2015年7月10日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class OrderForApp
{
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderForApp other = (OrderForApp)obj;
        if (bianhao == null)
        {
            if (other.bianhao != null)
                return false;
        }
        else if (!bianhao.equals(other.bianhao))
            return false;
        return true;
    }
    
    private long time;// 订单有效时间判断的时间戳, 即订单对象生成时的时间戳
    
    public long getTime()
    {
        return time;
    }
    
    public void setTime(long time)
    {
        this.time = time;
    }
    
    private String Miaoshu; // 描述
    
    private String id;// 订单ID
    
    private String ShangpinID;// 商品ID
    
    private String bianhao;// 订单编号
    
    private String url;// 图片地址
    
    private String shangpinlx;// 商品类型
    
    private String mingcheng;// 标题
    
    private String danjia;// 单价
    
    private int shuliang;// 数量
    
    private String zongjia;// 总价
    
    private String yuanjia;// 原价
    
    private String youhuijia;// 优惠价
    
    private String mjname;// 买家姓名
    
    private String mmjname;// 卖家姓名
    
    private String MaijiaID;// 买家ID
    
    private String MmaijiaID;// 卖家ID
    
    private int IsQiangdan;// 是否是抢单
    
    private String MaijiaLx;// 买家类型
    
    private String MmaijiaLx;// 卖家类型
    
    private String Chima;// 尺码
    
    private String Shengao;// 身高
    
    private String Tizhong;// 体重
    
    private String Yaowei;// 腰围
    
    private String Xiongwei;// 胸围
    
    private String Tunwei;// 臀围
    
    private String shouhuoren;// 收货人
    
    private String shouhuodianhua;// 收货电话
    
    private String shouhuodizhi;// 收货地址
    
    private String Youbian;// 邮编
    
    private String IsYuanSjs;// 是否是原设计师
    
    private String Tuikuanzht;// 退款状态
    
    /**
     * JiagongSj
     */
    private String JiagongSj;// 加工时间
    
    private String KuaidiFs;// 快递方式
    
    private String ZhizuoLx;// 制作类型
    
    private String buyerName; // 买家用户名
    
    private String salerName; // 卖家用户名
    
    private int youhuifudu = 1;// 优惠幅度
    
    private String RukuSj; // 入库时间
    
    private String FahuoSj; // 发货时间
    
    
    public String getFahuoSj()
    {
        return FahuoSj;
    }

    public void setFahuoSj(String fahuoSj)
    {
        FahuoSj = fahuoSj;
    }

    /**
     * 1：待付款 2：已取消 4：已付款 8：待发货 32：已发货 64：已收货 128：待评价 256：已评价 512：延迟收货
     */
    private String zhuangtai;
    
    /**
     * 收款码
     */
    private String Shoukuanma;
    
    /**
     * 付款时间
     */
    private String FukuanSj;
    
    private String payCode;
    
    private int designerListCount; //设计师列表个数
    
    public String getPayCode()
    {
        return payCode;
    }

    public void setPayCode(String payCode)
    {
        this.payCode = payCode;
    }

    public String getYuanjia()
    {
        return yuanjia;
    }
    
    public void setYuanjia(String yuanjia)
    {
        this.yuanjia = yuanjia;
    }
    
    public String getYouhuijia()
    {
        return youhuijia;
    }
    
    public void setYouhuijia(String youhuijia)
    {
        this.youhuijia = youhuijia;
    }
    
    public int getYouhuifudu()
    {
        return youhuifudu;
    }
    
    public void setYouhuifudu(int youhuifudu)
    {
        this.youhuifudu = youhuifudu;
    }
    
    public String getZhizuoLx()
    {
        return ZhizuoLx;
    }
    
    public void setZhizuoLx(String zhizuoLx)
    {
        ZhizuoLx = zhizuoLx;
    }
    
    public String getKuaidiFs()
    {
        return KuaidiFs;
    }
    
    public void setKuaidiFs(String kuaidiFs)
    {
        KuaidiFs = kuaidiFs;
    }
    
    public String getZhuangtai()
    {
        return zhuangtai;
    }
    
    public void setZhuangtai(String zhuangtai)
    {
        this.zhuangtai = zhuangtai;
    }
    
    public String getJiagongSj()
    {
        return JiagongSj;
    }
    
    public void setJiagongSj(String jiagongSj)
    {
        JiagongSj = jiagongSj;
    }
    
    public String getIsYuanSjs()
    {
        return IsYuanSjs;
    }
    
    public void setIsYuanSjs(String isYuanSjs)
    {
        IsYuanSjs = isYuanSjs;
    }
    
    public String getShouhuoren()
    {
        return shouhuoren;
    }
    
    public void setShouhuoren(String shouhuoren)
    {
        this.shouhuoren = shouhuoren;
    }
    
    public String getShouhuodianhua()
    {
        return shouhuodianhua;
    }
    
    public void setShouhuodianhua(String shouhuodianhua)
    {
        this.shouhuodianhua = shouhuodianhua;
    }
    
    public String getShouhuodizhi()
    {
        return shouhuodizhi;
    }
    
    public void setShouhuodizhi(String shouhuodizhi)
    {
        this.shouhuodizhi = shouhuodizhi;
    }
    
    public String getYoubian()
    {
        return Youbian;
    }
    
    public void setYoubian(String youbian)
    {
        Youbian = youbian;
    }
    
    // public String getQiangdan()
    // {
    // return qiangdan;
    // }
    //
    // public void setQiangdan(String qiangdan)
    // {
    // this.qiangdan = qiangdan;
    // }
    
    public String getTunwei()
    {
        return Tunwei;
    }
    
    public void setTunwei(String tunwei)
    {
        Tunwei = tunwei;
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
    
    public String getXiongwei()
    {
        return Xiongwei;
    }
    
    public void setXiongwei(String xiongwei)
    {
        Xiongwei = xiongwei;
    }
    
    public String getChima()
    {
        return Chima;
    }
    
    public void setChima(String chima)
    {
        Chima = chima;
    }
    
    public String getMaijiaLx()
    {
        return MaijiaLx;
    }
    
    public void setMaijiaLx(String maijiaLx)
    {
        MaijiaLx = maijiaLx;
    }
    
    public String getMmaijiaLx()
    {
        return MmaijiaLx;
    }
    
    public void setMmaijiaLx(String mmaijiaLx)
    {
        MmaijiaLx = mmaijiaLx;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getBianhao()
    {
        return bianhao;
    }
    
    public void setBianhao(String bianhao)
    {
        this.bianhao = bianhao;
    }
    
    public String getUrl()
    {
        return url;
    }
    
    public void setUrl(String url)
    {
        this.url = url;
    }
    
    public String getShangpinlx()
    {
        return shangpinlx;
    }
    
    public void setShangpinlx(String shangpinlx)
    {
        this.shangpinlx = shangpinlx;
    }
    
    public String getMingcheng()
    {
        return mingcheng;
    }
    
    public void setMingcheng(String mingcheng)
    {
        this.mingcheng = mingcheng;
    }
    
    public String getDanjia()
    {
        return danjia;
    }
    
    public void setDanjia(String danjia)
    {
        this.danjia = danjia;
    }
    
    public int getShuliang()
    {
        return shuliang;
    }
    
    public void setShuliang(int shuliang)
    {
        this.shuliang = shuliang;
    }
    
    public String getZongjia()
    {
        return zongjia;
    }
    
    public void setZongjia(String zongjia)
    {
        this.zongjia = zongjia;
    }
    
    public String getMjname()
    {
        return mjname;
    }
    
    public void setMjname(String mjname)
    {
        this.mjname = mjname;
    }
    
    public String getMmjname()
    {
        return mmjname;
    }
    
    public void setMmjname(String mmjname)
    {
        this.mmjname = mmjname;
    }
    
    public String getMiaoshu()
    {
        return Miaoshu;
    }
    
    public void setMiaoshu(String miaoshu)
    {
        Miaoshu = miaoshu;
    }
    
    public int getIsQiangdan()
    {
        return IsQiangdan;
    }
    
    public void setIsQiangdan(int isQiangdan)
    {
        IsQiangdan = isQiangdan;
    }
    
    public String getMaijiaID()
    {
        return MaijiaID;
    }
    
    public void setMaijiaID(String maijiaID)
    {
        MaijiaID = maijiaID;
    }
    
    public String getMmaijiaID()
    {
        return MmaijiaID;
    }
    
    public void setMmaijiaID(String mmaijiaID)
    {
        MmaijiaID = mmaijiaID;
    }
    
    public String getShangpinID()
    {
        return ShangpinID;
    }
    
    public void setShangpinID(String shangpinID)
    {
        ShangpinID = shangpinID;
    }
    
    public String getRukuSj()
    {
        return RukuSj;
    }
    
    public void setRukuSj(String rukuSj)
    {
        RukuSj = rukuSj;
    }
    
    public String getShoukuanma()
    {
        return Shoukuanma;
    }
    
    public void setShoukuanma(String shoukuanma)
    {
        Shoukuanma = shoukuanma;
    }
    
    public String getFukuanSj()
    {
        return FukuanSj;
    }
    
    public void setFukuanSj(String fukuanSj)
    {
        FukuanSj = fukuanSj;
    }
    
    public String getBuyerName()
    {
        return buyerName;
    }
    
    public void setBuyerName(String buyerName)
    {
        this.buyerName = buyerName;
    }
    
    public String getSalerName()
    {
        return salerName;
    }
    
    public void setSalerName(String salerName)
    {
        this.salerName = salerName;
    }

	public String getTuikuanzht() {
		return Tuikuanzht;
	}

	public void setTuikuanzht(String tuikuanzht) {
		Tuikuanzht = tuikuanzht;
	}

	public int getDesignerListCount() {
		return designerListCount;
	}

	public void setDesignerListCount(int designerListCount) {
		this.designerListCount = designerListCount;
	}
    
    
}
