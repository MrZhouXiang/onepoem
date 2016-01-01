package com.puyun.myshop.dao;

import java.util.List;
import java.util.Map;

import com.puyun.myshop.entity.Address;
import com.puyun.myshop.entity.Ads;
import com.puyun.myshop.entity.Apk;
import com.puyun.myshop.entity.Buyer;
import com.puyun.myshop.entity.BuyerForOrder;
import com.puyun.myshop.entity.ColorForApp;
import com.puyun.myshop.entity.Comment;
import com.puyun.myshop.entity.Express;
import com.puyun.myshop.entity.GoodsForApp;
import com.puyun.myshop.entity.GoodsPhoto;
import com.puyun.myshop.entity.GoodsType;
import com.puyun.myshop.entity.HistoryShowForApp;
import com.puyun.myshop.entity.LVBean;
import com.puyun.myshop.entity.MouldForApp;
import com.puyun.myshop.entity.OrderForApp;
import com.puyun.myshop.entity.PhotoBean;
import com.puyun.myshop.entity.PoemMod;
import com.puyun.myshop.entity.Saler;
import com.puyun.myshop.entity.StandardSize;
import com.puyun.myshop.entity.User;
import com.puyun.myshop.entity.UserAvatar;
import com.puyun.myshop.entity.UserSize;

/**
 * 衣氏百秀APP接口
 * 
 */
public interface AppDao {

	/**
	 * 用户登录
	 * 
	 * @param account
	 *            登陆账号
	 * @param pwd
	 *            密码（MD5加密后）
	 * @return 登陆成功返回用户对象，登陆失败返回null
	 * @see [类、类#方法、类#成员]
	 */
	User login(String account, String pwd);

	/**
	 * 用户注册
	 * 
	 * @param user
	 *            用户对象
	 * @return 刚新增用户的ID
	 * @see [类、类#方法、类#成员]
	 */
	int register(User user);

	/**
	 * 更新密码
	 * 
	 * @param account
	 *            账号
	 * @param newPwd
	 *            新密码（MD5加密）
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	int updatePwd(String account, String newPwd);

	/**
	 * 根据手机号获取用户，如果手机号尚未注册则返回null
	 * 
	 * @param tel
	 *            手机号
	 * @return 用户信息对象
	 * @see [类、类#方法、类#成员]
	 */
	User getUser(String tel);

	/**
	 * 获取最新版本APK信息
	 * 
	 * @return APK对象
	 * @see [类、类#方法、类#成员]
	 */
	Apk getApk();

	/**
	 * 获取订单下的商品列表
	 * 
	 * @param orderId
	 *            订单号
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<Map<String, Object>> getGoodsList(String orderId);

	/**
	 * 
	 * TODO 根据登录ID获取买家ID.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年6月24日 下午5:00:15
	 * 
	 * @param loginId
	 * @return
	 */
	String getBuyerId(String loginId);

	/**
	 * 
	 * TODO 根据登录ID获取卖家ID.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年6月24日 下午5:03:44
	 * 
	 * @param loginId
	 * @return
	 */
	String getSalerId(String loginId);

	/**
	 * 
	 * TODO 根据买家ID查询个人信息.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年6月25日 下午2:54:57
	 * 
	 * @param id
	 * @return
	 */
	Buyer getBuyerInfo(String id);

	/**
	 * 
	 * TODO 根据卖家ID查询个人信息.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年6月25日 下午2:54:57
	 * 
	 * @param id
	 * @return
	 */
	Saler getSalerInfo(String id);

	/**
	 * 
	 * TODO 根据用户头像ID获取头像地址.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年6月25日 下午3:50:06
	 * 
	 * @param id
	 * @return
	 */
	String getUserAvatarUrl(String id, String types);

	/**
	 * 
	 * TODO 根据用户头像ID获取头像.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年9月17日 下午4:51:50
	 * 
	 * @param id
	 * @param types
	 * @return
	 */
	UserAvatar getUserAvatarUrl2(String id, String types);

	/**
	 * 
	 * TODO 查询所有商品类型.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年6月29日 下午1:32:35
	 * 
	 * @param types
	 * @return
	 */
	List<GoodsType> getAllGoodsTypes();

	/**
	 * 
	 * TODO 根据类型获取商品列表.(非抢单(客户端使用))
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年6月29日 下午2:06:43
	 * 
	 * @param token
	 * @param types
	 * @param page_num
	 * @param page_size
	 * @param orderby
	 * @param hascomment
	 * @param comment_size
	 * @param yonghuid
	 * @return
	 */
	List<GoodsForApp> getGoodsList(String types, int page_num, int page_size,
			String orderby, int hascomment, int comment_size, int yonghuid,
			int zhizuolx);

	/**
	 * (别人查看设计师) 获取历史作品
	 * 
	 * @param types
	 * @param page_num
	 * @param page_size
	 * @param orderby
	 * @param hascomment
	 * @param comment_size
	 * @param yonghuid
	 * @return
	 */
	List<HistoryShowForApp> getHistoryShowForApp(String types, int page_num,
			int page_size, String orderby, int hascomment, int comment_size,
			int yonghuid);

	/**
	 * 获取商户历史作品
	 * 
	 * @param types
	 * @param page_num
	 * @param page_size
	 * @param orderby
	 * @param hascomment
	 * @param comment_size
	 * @param id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<GoodsForApp> getDesignerShowList(String types, int page_num,
			int page_size, String orderby, int hascomment, int comment_size,
			int id);

	/**
	 * 
	 * TODO 发布评论.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年7月1日 下午3:33:23
	 * 
	 * @param commenter_id
	 *            评论人ID
	 * @param to_commenter_id
	 *            评论对象ID
	 * @param types
	 *            类型, 商品：shangpin; 人：ren
	 * @param pid
	 *            父id
	 * @param content
	 *            内容
	 * @param pinglunrenLx
	 *            评论人类型, kehu,shanghu
	 * @return
	 */
	boolean postComment(String commenter_id, String to_commenter_id,
			String types, String content, String pinglunrenLx);

	/**
	 * 
	 * TODO 发表商品评价.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年7月1日 下午4:06:34
	 * 
	 * @param order_id
	 *            订单ID
	 * @param shangpin_id
	 *            商品ID
	 * @param pingfen
	 *            评分
	 * @param content
	 *            内容
	 * @param user_id
	 *            用户ID
	 * @param utypes
	 *            用户类型
	 * @return
	 */
	boolean postGoodsComment(String order_id, String shangpin_id, int pingfen,
			String content, String user_id, String utypes);

	/**
	 * 
	 * TODO 查询商品评论列表.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年7月1日 下午6:02:01
	 * 
	 * @param id
	 *            商品ID
	 * @param page_num
	 *            页码
	 * @param page_size
	 *            页面容量
	 * @return
	 */
	List<Comment> getCommentList(String id, int page_num, int page_size,
			String types);

	/**
	 * 
	 * TODO 查询商品剩余数量.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年7月1日 下午7:23:41
	 * 
	 * @param id
	 *            商品ID
	 * @return
	 */
	int getGoodsRemainNum(String id);

	/**
	 * 
	 * TODO 查询商品详情.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年7月1日 下午7:53:42
	 * 
	 * @param id
	 *            商品ID
	 * @return
	 */
	GoodsForApp getGoodsDetail(String id);

	/**
	 * 
	 * TODO 查询商品图片集合.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年7月1日 下午8:14:16
	 * 
	 * @param id
	 *            商品ID
	 * @return
	 */
	String[] getGoodsPhotoList(String id);

	List<GoodsPhoto> getGoodsPhotoList2(String id);

	/**
	 * 
	 * TODO 提交订单.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年7月2日 下午4:34:28
	 * 
	 * @param order
	 * @return
	 */
	int submitOrder(OrderForApp order);

	/**
	 * 判断买家是否存在
	 * 
	 * @param id
	 *            买家id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	boolean isBuyerExit(String id);

	/**
	 * 修改买家信息<br>
	 * 修改个人信息时候使用
	 * 
	 * @param buyer
	 * @return
	 */
	boolean updateBuyer(Buyer buyer);

	/**
	 * 增加买家信息 <br>
	 * 首次注册时候使用
	 * 
	 * @param buyer
	 * @return
	 */
	int addBuyer(Buyer buyer);

	/**
	 * 增加买家尺寸<br>
	 * 
	 * 
	 * @param buyer
	 * @return
	 */
	int addMySize(UserSize size);

	/**
	 * 获取尺寸列表
	 * 
	 * @param loginid
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<UserSize> getMySizeList(String loginid);

	/**
	 * 判断卖家是否存在
	 * 
	 * @param loginId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	boolean isSalerExit(String loginId);

	/**
	 * 增加卖家
	 * 
	 * @param saler
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	int addSaler(Saler saler);

	/**
	 * 获取订单列表
	 * 
	 * @param types
	 * @param userid
	 * @param status
	 * @param page_num
	 * @param page_size
	 * @param orderby
	 * @param shangpinid
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<OrderForApp> getOrderList(String types, int userid, String status,
			int page_num, int page_size, int shangpinid, String leixing);

	/**
	 * 获取商品抢单买家列表
	 * 
	 * @param id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<BuyerForOrder> getBuyerList(String id, int page_num, int page_size,
			String orderby);

	/**
	 * 获取订单详情
	 * 
	 * @param id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	OrderForApp getOrderInfo(String id);

	/**
	 * 修改订单状态
	 * 
	 * 
	 * @param id
	 * @param status
	 *            1：待付款 2：已取消 4：已付款 8：待发货 32：已发货 64:已收货 128：待评价 256：已评价
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	int modifyOrderStatus(String id, int status);

	/**
	 * 发货
	 * 
	 * @param id
	 * @param status
	 * @param kuaidiID
	 * @param kuaididanhao
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	int modifyOrderStatus(String id, int status, int kuaidiID,
			String kuaididanhao);

	/**
	 * 收款
	 * 
	 * @param id
	 * @param status
	 * @param shoukuanma
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	int modifyOrderStatus(String id, int status, String shoukuanma);

	/**
	 * 确认付款
	 * 
	 * @param id
	 * @param pay_id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	int makerSureToReceipt(String id, String pay_id);

	/**
	 * 延时收货
	 * 
	 * @param id
	 * @param reason
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	int delayToReceipt(String id, String reason);

	/**
	 * 催款
	 * 
	 * @param id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	int dunningOrder(String id, String CuiKuanYy);

	/**
	 * 增加收货地址
	 * 
	 * @param yonghuid
	 * @param yonghuleixing
	 * @param shouhuoren
	 * @param shouhuodizhi
	 * @param dianhua
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	int addShouhuodizhi(Address address);

	/**
	 * 修改收货地址
	 * 
	 * @param address
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	int modifyShouhuodizhi(Address address);

	/**
	 * 获取广告
	 * 
	 * @param types
	 * @param point
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<Ads> getAdsList(String types, String point);

	/**
	 * 获取快递列表
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<Express> getExpressList();

	/**
	 * 发布商品
	 * 
	 * @param goods
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	int addGoods(GoodsForApp goods);

	/**
	 * 获取模板列表
	 * 
	 * @param id
	 *            制作类型ID
	 * @param page_num
	 * @param page_size
	 * @param orderby
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<MouldForApp> getMouldList(String id, int page_num, int page_size,
			String orderby);

	/**
	 * 获取模板详情
	 * 
	 * @param id
	 *            模板ID
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	MouldForApp getMouldDetail(String id);

	/**
	 * 根据商品获取抢单个数
	 * 
	 * @param id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	int getOrderNum(String id);

	/**
	 * 获取系统颜色
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<ColorForApp> getColors();

	/**
	 * 获取抢单列表
	 * 
	 * @param types
	 * @param page_num
	 * @param page_size
	 * @param orderby
	 * @param hascomment
	 * @param comment_size
	 * @param yonghuid
	 * @param zhizuolx
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<GoodsForApp> getGoodsList3(String types, int page_num, int page_size,
			String orderby, int hascomment, int comment_size, int yonghuid,
			int zhizuolx);

	/**
	 * 根据商品ID修改抢单订单状态为i
	 * 
	 * @param shangpingid
	 * @param i
	 *            状态
	 * @param dingdanid
	 *            不需要改状态的订单
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	boolean changeOrderStatusBySpID(String shangpingid, int i, String dingdanid);

	/**
	 * 
	 * TODO 修改支付宝交易号.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年9月20日 下午1:14:07
	 * 
	 * @param dingdanid
	 * @param payCode
	 * @return
	 */
	boolean changeOrderPayCode(String dingdanid, String payCode);

	/**
	 * 根据商品ID修改抢单订单状态为1
	 * 
	 * @param shangpingid
	 * @param status
	 *            状态
	 * @param salerId
	 *            卖家ID
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	boolean modifyOrderStatusBySpID(String shangpingid, int status,
			String salerId);

	/**
	 * 根据商品ID获取抢单失败的卖家IDList
	 * 
	 * @param id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<String> getMmJiaIDListBySpIDForCustomization(String id);

	/**
	 * 根据商品ID获取抢单成功的卖家ID
	 * 
	 * @param id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	String getSucMmJiaIDBySpIDForCustomization(String id);

	/**
	 * 根据商品ID获取抢单失败的卖家IDList
	 * 
	 * @param id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<String> getMmJiaIDListBySpIDForWorksheet(String id);

	/**
	 * 根据商品ID获取抢单成功的卖家ID
	 * 
	 * @param id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	String getSucMmJiaIDBySpIDForWorksheet(String id);

	/**
	 * 商品数量加1
	 * 
	 * @param id
	 * @see [类、类#方法、类#成员]
	 */
	int addShuliang(OrderForApp order);

	/**
	 * 
	 * TODO 点赞.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年7月26日 下午4:45:47
	 * 
	 * @param id
	 *            商品ID
	 * @return
	 */
	boolean praise(int id);

	/**
	 * 上传头像
	 * 
	 * @param userId
	 * @param type
	 * @param url
	 * @param fileName
	 * @return
	 */
	public boolean uploadPhoto(String userId, String type, String url,
			String fileName);

	/**
	 * 
	 * TODO 修改买家个人信息.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年7月8日 下午5:50:52
	 * 
	 * @param buyer
	 * @return
	 */
	boolean updateBuyerInfo(Buyer buyer);

	/**
	 * 
	 * TODO 修改卖家个人信息.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年7月8日 下午5:50:52
	 * 
	 * @param buyer
	 * @return
	 */
	boolean updateSalerInfo(Saler saler);

	/**
	 * 
	 * TODO 修改用户尺码.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年7月29日 上午10:49:38
	 * 
	 * @param size
	 * @return
	 */
	boolean modifyUserSize(UserSize size);

	/**
	 * 获取收货地址列表
	 * 
	 * @param yonghuId
	 * @param yonghuLx
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<Address> getShouhuodizhi(String yonghuId, String yonghuLx);

	/**
	 * 获取最牛设计师ID
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	String getNBBuyerId(OrderForApp order);

	/**
	 * 
	 * 更改抢单用户
	 * 
	 * @param oldId
	 * @param newId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	boolean changeBuyer(String oldId, String newId);

	/**
	 * 获取没有发货的订单数量
	 * 
	 * @param shangpinID
	 * @param maijiaID
	 * @param maijiaLx
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	int getNotSendOrderNum(String shangpinID, String maijiaID, String maijiaLx);

	/**
	 * 
	 * 
	 * 判断是否已经抢单
	 * 
	 * @param shangpinId
	 * @param maijiaID
	 * @param maijiaLx
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	boolean hasQiangDan(String shangpinId, String maijiaID, String maijiaLx);

	/**
	 * 根据商品ID获取抢单个数
	 * 
	 * @param shangpinID
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	int getHasQiangDanNumByShangpin(String shangpinID);

	/**
	 * 获取用户等级
	 * 
	 * @param maijiaID
	 * @param maijiaLx
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	int getLVByIDandLX(String maijiaID, String maijiaLx);

	/**
	 * 根据等级获取经验
	 * 
	 * @param Jingyan
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	int getLvByJingYan(int Jingyan);

	/**
	 * 
	 * TODO 根据经验获取等级.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年9月9日 下午5:15:04
	 * 
	 * @param Jingyan
	 * @return
	 */
	LVBean getLVBeanByJingYan(int Jingyan);

	/**
	 * 根据制作类型和等级获取价格
	 * 
	 * @param zhizuoLx
	 * @param lv
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	float getJiageByLv(int zhizuoLx, int lv);

	/**
	 * 获取难度系数
	 * 
	 * @param zhizuoLx
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	float getNanduxishu(int zhizuoLx);

	/**
	 * 获取加工费
	 * 
	 * @param lv
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	int getJiagongfei(int lv);

	/**
	 * 根据评分修改用户经验
	 * 
	 * @param order_id
	 * @param i
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	boolean changeJingyanByPingfen(String order_id, int i);

	/**
	 * 根据点赞修改用户经验
	 * 
	 * @param order_id
	 * @param i
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	boolean changeJingyanByDianZan(String order_id, int i);

	/**
	 * 
	 * TODO 修改订单收货地址.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年9月9日 下午5:13:56
	 * 
	 * @param order
	 * @return
	 */
	boolean xiugaiDizhi(OrderForApp order);

	/**
	 * 
	 * TODO 更新订单状态.
	 * <p>
	 * 将30分钟内未支付的订单取消<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年8月14日 下午5:25:15
	 * 
	 * @return
	 */
	boolean updateOrderStatus();

	Saler setAllLV(Saler saler);

	boolean jubao(PhotoBean photo);

	/**
	 * 修改商品买断状态
	 * 
	 * @param shangpinID
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	boolean changeToMaiduan(String shangpinID);

	/**
	 * 
	 * TODO 清除新评论个数.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年8月26日 下午5:24:51
	 * 
	 * @param goodId
	 * @return
	 */
	boolean clearDiscussCount(int goodId);

	/**
	 * 
	 * TODO 查询新评论个数.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年8月27日 上午9:57:36
	 * 
	 * @param designerId
	 * @return
	 */
	int getNewDiscussCount(int designerId);

	int getChangeCs(String loginId, String types);

	/**
	 * 
	 * TODO 更新登录时间.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年9月8日 下午2:02:45
	 * 
	 * @param userId
	 * @param userType
	 */
	void updateLoginTime(String userId, String userType);

	/**
	 * 判断是否已经封号
	 * 
	 * @param id
	 *            用户ID
	 * @param types
	 *            用户类型
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	boolean isFenghao(String id, String types);

	boolean updateNBBuyerOrder(OrderForApp order);

	/**
	 * (设计师查看自己)历史作品
	 * 
	 * @param types
	 * @param page_num
	 * @param page_size
	 * @param orderby
	 * @param hascomment
	 * @param comment_size
	 * @param yonghuid
	 * @return
	 */
	List<HistoryShowForApp> getHistoryShowForApp2(String types, int page_num,
			int page_size, String orderby, int hascomment, int comment_size,
			int id);

	/**
	 * 
	 * TODO 设置用户等级.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年9月9日 下午5:56:58
	 * 
	 * @param lv
	 *            等级
	 * @param userId
	 *            用户ID
	 * @param userType
	 *            用户类型
	 * @return
	 */
	boolean setLv(int lv, String userId, String userType);

	/**
	 * 
	 * TODO 查询总经验.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年9月9日 下午6:08:28
	 * 
	 * @param userId
	 * @param userType
	 * @return
	 */
	int getExp(String userId, String userType);

	/**
	 * 确认支付
	 * 
	 * @param id
	 *            订单号
	 * @param payCode
	 *            交易号
	 * @return author: ldz
	 * @see [类、类#方法、类#成员]
	 */
	int confirmPay(String id, String payCode, String zhuangtai);

	/**
	 * 
	 * TODO 删除收货地址.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年9月20日 上午10:21:09
	 * 
	 * @param id
	 * @return
	 */
	boolean deleteAddress(int id);

	/**
	 * 
	 * TODO 根据性别和用户选择的尺码获取参考尺码信息.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年10月9日 下午4:01:25
	 * 
	 * @param sex
	 * @param normalSize
	 * @return
	 */
	StandardSize getStandardSize(String sex, String normalSize);

	List<PoemMod> getPoemList(int id, int size);

}
