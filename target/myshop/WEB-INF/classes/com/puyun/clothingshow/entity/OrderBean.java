package com.puyun.clothingshow.entity;

/**
 * 
 * TODO	后台订单实体. 
 * Created on 2015年8月14日 下午4:49:33 
 * @author 周震
 */
public class OrderBean {
	private String id; //主键ID
	private String goods_id; // 商品ID
	private String order_no; // 订单编号
	private String quantity; // 商品数量
	private String buyer_id; // 买家ID
	private String saler_id; // 卖家ID
	private String sizename; // 尺寸名称
	private String height; // 身高
	private String weight; // 体重
	private String waist; // 腰围
	private String bust; // 胸围
	private String hip; // 臀围
	private String description; // 描述
	private String Maijialx;// 买家类型
	private String Mmaijialx;// 卖家类型
	private String shouhuoren;// 收货人
	private String shouhuodianhua;// 收货电话
	private String shouhuodizhi;// 收货地址
	private String Youbian;// 邮编
	/**
	 * 1：待付款 2：已取消 4：已付款 8：待发货 32：已发货 64：已收货 128：待评价 256：已评价 512：延迟收货
	 */
	private int status; // 状态
	private double totalPrice; // 总价
	private String paymentCode; // 收款码
	private String alipayCode; //支付宝交易号
	private String RukuSj; // 入库时间
	private String buyerName; // 买家用户名
	private String salerName; // 卖家用户名
	private String salerPayAccount; //卖家支付宝账户
	/**
	 * 1: 是；0: 否 
	 */
    private String qiangdan;// 是否是抢单
    /**
     * 1：已放款; 0：未放款
     */
    private int fangkuanZht; //放款状态
    
    private String FahuoSj; //发货时间
    
    private String DaoqiSj; //到期时间
    
	public String getQiangdan()
    {
        return qiangdan;
    }

	public String getMaijialx() {
		return Maijialx;
	}
    public void setQiangdan(String qiangdan)
    {
        this.qiangdan = qiangdan;
    }


	public void setMaijialx(String maijialx) {
		Maijialx = maijialx;
	}

	public String getMmaijialx() {
		return Mmaijialx;
	}

	public void setMmaijialx(String mmaijialx) {
		Mmaijialx = mmaijialx;
	}

	public String getShouhuoren() {
		return shouhuoren;
	}

	public void setShouhuoren(String shouhuoren) {
		this.shouhuoren = shouhuoren;
	}

	public String getShouhuodianhua() {
		return shouhuodianhua;
	}

	public void setShouhuodianhua(String shouhuodianhua) {
		this.shouhuodianhua = shouhuodianhua;
	}

	public String getShouhuodizhi() {
		return shouhuodizhi;
	}

	public void setShouhuodizhi(String shouhuodizhi) {
		this.shouhuodizhi = shouhuodizhi;
	}

	public String getYoubian() {
		return Youbian;
	}

	public void setYoubian(String youbian) {
		Youbian = youbian;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}

	public String getSaler_id() {
		return saler_id;
	}

	public void setSaler_id(String saler_id) {
		this.saler_id = saler_id;
	}

	public String getSizename() {
		return sizename;
	}

	public void setSizename(String sizename) {
		this.sizename = sizename;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getWaist() {
		return waist;
	}

	public void setWaist(String waist) {
		this.waist = waist;
	}

	public String getBust() {
		return bust;
	}

	public void setBust(String bust) {
		this.bust = bust;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getHip() {
		return hip;
	}

	public void setHip(String hip) {
		this.hip = hip;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	public String getRukuSj() {
		return RukuSj;
	}

	public void setRukuSj(String rukuSj) {
		RukuSj = rukuSj;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getSalerName() {
		return salerName;
	}

	public void setSalerName(String salerName) {
		this.salerName = salerName;
	}

	public String getSalerPayAccount() {
		return salerPayAccount;
	}

	public void setSalerPayAccount(String salerPayAccount) {
		this.salerPayAccount = salerPayAccount;
	}

	public int getFangkuanZht() {
		return fangkuanZht;
	}

	public void setFangkuanZht(int fangkuanZht) {
		this.fangkuanZht = fangkuanZht;
	}

	public String getAlipayCode() {
		return alipayCode;
	}

	public void setAlipayCode(String alipayCode) {
		this.alipayCode = alipayCode;
	}

	public String getFahuoSj() {
		return FahuoSj;
	}

	public void setFahuoSj(String fahuoSj) {
		FahuoSj = fahuoSj;
	}

	public String getDaoqiSj() {
		return DaoqiSj;
	}

	public void setDaoqiSj(String daoqiSj) {
		DaoqiSj = daoqiSj;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
