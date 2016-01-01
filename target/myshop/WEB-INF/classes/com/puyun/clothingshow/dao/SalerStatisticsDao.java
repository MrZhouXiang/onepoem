package com.puyun.clothingshow.dao;

/**
 * 
 * TODO 统计查询相关数据访问接口. Created on 2015年7月28日 上午10:46:40
 * 
 * @author 周震
 */
public interface SalerStatisticsDao {

	/**
	 * 1
	 * TODO	查询注册用户数量.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年8月6日 下午4:40:09
	 * 
	 * @param keyword1
	 *            起始时间
	 * @param keyword2
	 *            结束时间
	 * @param keyword3
	 *            筛选条件2, 
	 *            1.身份（学生/教师/手工裁缝/高级定制/时装大师/企业代表） 
	 *            student:学生 
	 *            teacher:教师
	 *            manualtailor:手工裁缝 
	 *            advancedcustomized:高级定制 
	 *            fashionmaster:时装大师
	 *            companyagent:企业代表 
	 *            2.所属（纺织学院/灵桥市场/西门街商业区/和义大道商业区）
	 *            weaveacademy:纺织学院 
	 *            lingqiaomarket:灵桥市场
	 *            ximenjiecommercial:西门街商业区 
	 *            heyidadaocommercial:和义大道商业区 
	 *            3.国家 
	 *            4.省
	 *            5.市
	 * @param keyword4
	 *            筛选条件3,
	 *            企业商户类型（外贸企业/服装厂/品牌服装/国际品牌服装）
	 *            foreigntradeenterprise:外贸企业
	 *            clothingfactory:服装厂
	 *            brandclothing:品牌服装
	 *            internationnalbrandclothing:国际品牌服装
	 * @return
	 */
	int getRegisterUserCount(String keyword1, String keyword2, String keyword3,
			String keyword4);

	/**
	 * 2
	 * TODO	查询上线用户数量.
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 周震
	 * date: 2015年8月12日 上午9:32:19
	 * @return
	 */
	int getOnlineUserCount();
	/**
	 * 3
	 * TODO 查询VIP用户数量.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年8月4日 下午3:15:42
	 * 
	 * @return
	 */
	int getVIPUserCount(String keyword1, String keyword2, String keyword3,
			String keyword4);

	/**
	 * 4
	 * TODO 查询设计师总交易额.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年8月5日 下午3:07:16
	 * 
	 * @return
	 */
	double getDesignerTradeAmount(String keyword1, String keyword2, String keyword3,
			String keyword4);

	/**
	 * 5
	 * TODO 查询设计师总发单数.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年8月5日 下午3:23:23
	 * 
	 * @return
	 */
	int getDesignerBillCount(String keyword1, String keyword2, String keyword3,
			String keyword4);

	/**
	 * 6
	 * TODO 查询用户发单数量.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年8月5日 下午3:30:27
	 * 
	 * @return
	 */
	int getUserBillCount(String keyword1, String keyword2, String keyword3,
			String keyword4);

	/**
	 * 7
	 * TODO 查询用户发单成交数量.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年8月5日 下午3:34:48
	 * 
	 * @return
	 */
	int getUserSuccessBillCount(String keyword1, String keyword2, String keyword3,
			String keyword4);
	
	/**
	 * 8
	 * TODO	查询新增用户数量.
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 周震
	 * date: 2015年8月12日 上午9:36:12
	 * @param keyword1
	 * @param keyword2
	 * @param keyword3
	 * @param keyword4
	 * @return
	 */
	int getNewUserCount(String keyword1, String keyword2, String keyword3,
			String keyword4);
	
	/**
	 * 9
	 * TODO	查询流失用户数量.
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 周震
	 * date: 2015年8月12日 上午9:37:48
	 * @param keyword1
	 * @param keyword2
	 * @param keyword3
	 * @param keyword4
	 * @return
	 */
	int getLoseUserCount(String keyword1, String keyword2, String keyword3,
			String keyword4);
	/**
	 * 10
	 * TODO 查询订单退货数量.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年8月4日 下午4:26:50
	 * 
	 * @return
	 */
	int getCancelOrderCount(String keyword1, String keyword2, String keyword3,
			String keyword4);
	
	/**
	 * 11
	 * TODO 查询订单成功交易数量.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年8月4日 下午4:36:37
	 * 
	 * @return
	 */
	int getSuccessOrderCount(String keyword1, String keyword2, String keyword3,
			String keyword4);
	
	/**
	 * 12
	 * TODO 查询加工单交易额.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年8月4日 下午5:17:58
	 * 
	 * @return
	 */
	double getWorksheetTradeAmount(String keyword1, String keyword2, String keyword3,
			String keyword4);
	
	/**
	 * 13
	 * TODO 查询加工单交易数量.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年8月4日 下午5:15:34
	 * 
	 * @return
	 */
	int getWorksheetOrderCount(String keyword1, String keyword2, String keyword3,
			String keyword4);
	
	/**
	 * 14
	 * TODO 查询设计稿买断额.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年8月4日 下午5:17:58
	 * 
	 * @return
	 */
	double getBuyoutTradeAmount(String keyword1, String keyword2, String keyword3,
			String keyword4);
	
	/**
	 * 15
	 * TODO 查询设计稿买断数量.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年8月4日 下午5:15:34
	 * 
	 * @return
	 */
	int getBuyoutOrderCount(String keyword1, String keyword2, String keyword3,
			String keyword4);
	
	/**
	 * 16
	 * TODO 查询总点赞数.
	 * 无条件查询, 由于未记录点赞时间
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年8月4日 下午3:52:50
	 * 
	 * @return
	 */
	int getTotalPraiseCount();

	/**
	 * 17
	 * TODO 查询总评论数.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年8月4日 下午3:59:02
	 * 
	 * @return
	 */
	int getTotalDiscussCount(String keyword1, String keyword2, String keyword3,
			String keyword4);
	
	/**
	 * 
	 * TODO	判断查询条件是否全部为空.
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 周震
	 * date: 2015年8月13日 下午1:21:12
	 * @param keyword1
	 * @param keyword2
	 * @param keyword3
	 * @param keyword4
	 * @return
	 */
	boolean isAllNull(String keyword1, String keyword2, String keyword3,
			String keyword4);
	
}
