package com.puyun.myshop.entity;


public class PhotoBean  implements Comparable<PhotoBean>{
	private int id; //不同图片表的主键ID
	private int typeId; // 所属类型ID
	/**
	 * 1.客户用户头像
	 * 2.商户用户头像
	 * 3.商品图片 
	 * 4.模板图片
	 */
	private int type; // 类型
	private String url; // 路径
	/**
	 * 图片类型 
	 * 正面：zhengmian 
	 * 侧面：cemian 
	 * 背面：beimian
	 */
	private String tupianlx;
	/**
	 * 1：是 
	 * 0：否
	 */
	private int isgongkai; // 是否公开
	/**
	 * 1：是 
	 * 0：否
	 */
	private int jubaozt; // 举报状态
	/**
	 * 按照举报次数倒序排序
	 */
	private int jubaocs; // 举报次数
	/**
	 * 1：是 
	 * 0：否
	 */
	private int shanchuzt; // 删除状态
	private String rukusj; // 入库时间
	private String tupianmc; // 图片名称
	private String goodsname; //所属商品名称
	private String publisher; //图片发布人
	private int publisherId; //发布人ID
	private int publisherType; //发布人类型, 0:客户；1：商户

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTupianlx() {
		return tupianlx;
	}

	public void setTupianlx(String tupianlx) {
		this.tupianlx = tupianlx;
	}

	public int getIsgongkai() {
		return isgongkai;
	}

	public void setIsgongkai(int isgongkai) {
		this.isgongkai = isgongkai;
	}

	public int getJubaozt() {
		return jubaozt;
	}

	public void setJubaozt(int jubaozt) {
		this.jubaozt = jubaozt;
	}

	public int getJubaocs() {
		return jubaocs;
	}

	public void setJubaocs(int jubaocs) {
		this.jubaocs = jubaocs;
	}

	public int getShanchuzt() {
		return shanchuzt;
	}

	public void setShanchuzt(int shanchuzt) {
		this.shanchuzt = shanchuzt;
	}

	public String getRukusj() {
		return rukusj;
	}

	public void setRukusj(String rukusj) {
		this.rukusj = rukusj;
	}

	public String getTupianmc() {
		return tupianmc;
	}

	public void setTupianmc(String tupianmc) {
		this.tupianmc = tupianmc;
	}

	
	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	

	public int getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}
	
	public int getPublisherType() {
		return publisherType;
	}

	public void setPublisherType(int publisherType) {
		this.publisherType = publisherType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int compareTo(PhotoBean o) {
		// TODO Auto-generated method stub
		//根据举报次数从大到小顺序
		if(this.getJubaocs() > o.jubaocs){
			return -1;
		}else if(this.getJubaocs() == o.jubaocs){
			return 0;
		}else{
			return 1;
		}
	}
	
	@Override  
    public String toString() {  
        return getTupianmc() + "------------------>" + getJubaocs();  
    }  
	
//	public static void main(String[] args) {
//		PhotoBean cbean;  
//        Random rand = new Random();  
//        List<PhotoBean> beans = new ArrayList<PhotoBean>();  
//        for (int i = 0; i < 100; i++) {  
//            cbean = new PhotoBean();  
//            cbean.setJubaocs(rand.nextInt(10000));  
//            beans.add(cbean);  
//        }  
//          
//        Object[] beanObjects = beans.toArray();  
//        Arrays.sort(beanObjects);  
//  
//        beans.removeAll(beans);  
//        System.out.println(beans.size());  
//          
//        for (int i = 0; i < 100; i++) {  
//            System.out.println(beanObjects[i]);  
//        }  
//  
//        System.out.println("ok");  
//	}
}
