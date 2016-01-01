package com.puyun.myshop.dao;

import java.util.List;
import java.util.Map;

import com.puyun.myshop.entity.OrderForApp;

/**
 * 
 * TODO	推送相关数据访问接口. 
 * Created on 2015年7月28日 上午10:46:40 
 * @author 周震
 */
public interface PushDao {
	/**
	 * 
	 * TODO	查询购买被封号设计师的订单.
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 周震
	 * date: 2015年7月28日 上午10:47:51
	 * @param designerId
	 * @return
	 */
	List<OrderForApp> getOrderList(int designerId);
	
	/**
	 * 
	 * TODO	查询当日设计图买断数量, 用户名, 买断总金额.
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 周震
	 * date: 2015年8月20日 下午5:17:47
	 * @param goodsId 商品ID
	 * @return
	 */
	Map<String, Object> getTodayBuyoutOrderCount(int goodsId);
	
	/**
	 * 
	 * TODO	查询当日成衣秀交易额, 用户名.
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 周震
	 * date: 2015年8月20日 下午5:19:13
	 * @param goodsId 商品ID
	 * @return
	 */
	Map<String, Object> getTodayDesignerTradeAmount(int goodsId);
	
	/**
	 * 
	 * TODO	查询成衣秀/设计图/买家秀/定制单的累计评论数量, 作品名称.
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 周震
	 * date: 2015年8月20日 下午5:20:26
	 * @param goodsId 商品ID
	 * @return
	 */
	Map<String, Object> getTodayDiscussCount(int goodsId);
	
	/**
	 * 
	 * TODO	根据用户ID获取手机号.
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 周震
	 * date: 2015年8月28日 上午9:36:56
	 * @param userId
	 * @return
	 */
	String getTelByUserId(String userId, String userType);
	
    /**
     * 
     * TODO	根据用户ID和用户类型获取用户名.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年10月26日 上午10:50:46
     * @param userId
     * @param userType
     * @return
     */
    String getUsername(String userId, String userType);
}
