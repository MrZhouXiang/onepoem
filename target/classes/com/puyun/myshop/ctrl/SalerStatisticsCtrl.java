package com.puyun.myshop.ctrl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.puyun.myshop.dao.SalerStatisticsDao;

/**
 * 统计管理相关请求控制类
 * 
 * @author 姓名
 * @version [版本号, 2014-9-10]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping("/salerstatistics")
public class SalerStatisticsCtrl {
	private static final Logger logger = Logger.getLogger(SalerStatisticsCtrl.class);

	@Autowired
	private SalerStatisticsDao salerStatisticsDao;

	public SalerStatisticsCtrl() {
		super();
		logger.debug("创建对象GoodsTypeCtrl");
	}

	/**
	 * 
	 * TODO 查询用户数量相关统计数据.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年7月10日 上午10:13:29
	 * 
	 * @param buyer
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getUserDataCount")
	public Map<String, Object> getUserDataCount(
			@RequestParam(required = false, defaultValue = "") String keyword1,
			@RequestParam(required = false, defaultValue = "") String keyword2,
			@RequestParam(required = false, defaultValue = "") String keyword3,
			@RequestParam(required = false, defaultValue = "") String keyword4) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int registerUserCount = salerStatisticsDao.getRegisterUserCount(
					keyword1, keyword2, keyword3, keyword4);
			int vipUserCount = salerStatisticsDao.getVIPUserCount(keyword1,
					keyword2, keyword3, keyword4);
			int onlineUserCount = salerStatisticsDao.getOnlineUserCount();
			int newUserCount = salerStatisticsDao.getNewUserCount(keyword1, keyword2, keyword3, keyword4);
			int loseUserCount = salerStatisticsDao.getLoseUserCount(keyword1, keyword2, keyword3, keyword4);
			map.put("registerUserCount", registerUserCount);
			map.put("vipUserCount", vipUserCount);
			map.put("onlineUserCount", onlineUserCount);
			map.put("newUserCount", newUserCount);
			map.put("loseUserCount", loseUserCount);
			map.put("success", true);
			logger.debug("操作成功");
		} catch (Exception e) {
			map.put("success", false);
			logger.debug("操作失败");
		}
		return map;
	}

	/**
	 * 
	 * TODO 查询设计师相关统计数据.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年7月10日 上午10:13:29
	 * 
	 * @param buyer
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDesignerDataCount")
	public Map<String, Object> getDesignerDataCount(
			@RequestParam(required = false, defaultValue = "") String keyword1,
			@RequestParam(required = false, defaultValue = "") String keyword2,
			@RequestParam(required = false, defaultValue = "") String keyword3,
			@RequestParam(required = false, defaultValue = "") String keyword4) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			double designerTradeAmount = salerStatisticsDao.getDesignerTradeAmount(
					keyword1, keyword2, keyword3, keyword4);
			int designerBillCount = salerStatisticsDao.getDesignerBillCount(
					keyword1, keyword2, keyword3, keyword4);
			map.put("designerTradeAmount", designerTradeAmount);
			map.put("designerBillCount", designerBillCount);
			map.put("success", true);
			logger.debug("操作成功");
		} catch (Exception e) {
			map.put("success", false);
			logger.debug("操作失败");
		}
		return map;
	}
	
	/**
	 * 
	 * TODO 查询用户发单相关统计数据.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年7月10日 上午10:13:29
	 * 
	 * @param buyer
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getUserBillDataCount")
	public Map<String, Object> getUserBillDataCount(
			@RequestParam(required = false, defaultValue = "") String keyword1,
			@RequestParam(required = false, defaultValue = "") String keyword2,
			@RequestParam(required = false, defaultValue = "") String keyword3,
			@RequestParam(required = false, defaultValue = "") String keyword4) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int userBillCount = salerStatisticsDao.getUserBillCount(
					keyword1, keyword2, keyword3, keyword4);
			int userSuccessBillCount = salerStatisticsDao.getUserSuccessBillCount(
					keyword1, keyword2, keyword3, keyword4);
			map.put("userBillCount", userBillCount);
			map.put("userSuccessBillCount", userSuccessBillCount);
			map.put("success", true);
			logger.debug("操作成功");
		} catch (Exception e) {
			map.put("success", false);
			logger.debug("操作失败");
		}
		return map;
	}
	
	/**
	 * 
	 * TODO 查询订单相关统计数据.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年7月10日 上午10:13:29
	 * 
	 * @param buyer
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getOrderDataCount")
	public Map<String, Object> getOrderDataCount(
			@RequestParam(required = false, defaultValue = "") String keyword1,
			@RequestParam(required = false, defaultValue = "") String keyword2,
			@RequestParam(required = false, defaultValue = "") String keyword3,
			@RequestParam(required = false, defaultValue = "") String keyword4) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int cancelOrderCount = salerStatisticsDao.getCancelOrderCount(
					keyword1, keyword2, keyword3, keyword4);
			int successOrderCount = salerStatisticsDao.getSuccessOrderCount(
					keyword1, keyword2, keyword3, keyword4);
			map.put("cancelOrderCount", cancelOrderCount);
			map.put("successOrderCount", successOrderCount);
			map.put("success", true);
			logger.debug("操作成功");
		} catch (Exception e) {
			map.put("success", false);
			logger.debug("操作失败");
		}
		return map;
	}
	
	/**
	 * 
	 * TODO 查询加工单相关统计数据.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年7月10日 上午10:13:29
	 * 
	 * @param buyer
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getWorksheetDataCount")
	public Map<String, Object> getWorksheetDataCount(
			@RequestParam(required = false, defaultValue = "") String keyword1,
			@RequestParam(required = false, defaultValue = "") String keyword2,
			@RequestParam(required = false, defaultValue = "") String keyword3,
			@RequestParam(required = false, defaultValue = "") String keyword4) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			double worksheetTradeAmount = salerStatisticsDao.getWorksheetTradeAmount(
					keyword1, keyword2, keyword3, keyword4);
			int worksheetOrderCount = salerStatisticsDao.getWorksheetOrderCount(
					keyword1, keyword2, keyword3, keyword4);
			map.put("worksheetTradeAmount", worksheetTradeAmount);
			map.put("worksheetOrderCount", worksheetOrderCount);
			map.put("success", true);
			logger.debug("操作成功");
		} catch (Exception e) {
			map.put("success", false);
			logger.debug("操作失败");
		}
		return map;
	}
	
	/**
	 * 
	 * TODO 查询设计稿买断相关统计数据.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年7月10日 上午10:13:29
	 * 
	 * @param buyer
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBuyoutDataCount")
	public Map<String, Object> getBuyoutDataCount(
			@RequestParam(required = false, defaultValue = "") String keyword1,
			@RequestParam(required = false, defaultValue = "") String keyword2,
			@RequestParam(required = false, defaultValue = "") String keyword3,
			@RequestParam(required = false, defaultValue = "") String keyword4) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			double buyoutTradeAmount = salerStatisticsDao.getBuyoutTradeAmount(
					keyword1, keyword2, keyword3, keyword4);
			int buyoutOrderCount = salerStatisticsDao.getBuyoutOrderCount(
					keyword1, keyword2, keyword3, keyword4);
			map.put("buyoutTradeAmount", buyoutTradeAmount);
			map.put("buyoutOrderCount", buyoutOrderCount);
			map.put("success", true);
			logger.debug("操作成功");
		} catch (Exception e) {
			map.put("success", false);
			logger.debug("操作失败");
		}
		return map;
	}
	
	/**
	 * 
	 * TODO 查询点赞和评论相关统计数据.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年7月10日 上午10:13:29
	 * 
	 * @param buyer
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getPraiseAndDiscussDataCount")
	public Map<String, Object> getPraiseAndDiscussDataCount(
			@RequestParam(required = false, defaultValue = "") String keyword1,
			@RequestParam(required = false, defaultValue = "") String keyword2,
			@RequestParam(required = false, defaultValue = "") String keyword3,
			@RequestParam(required = false, defaultValue = "") String keyword4) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int praiseCount = salerStatisticsDao.getTotalPraiseCount();
			int discussCount = salerStatisticsDao.getTotalDiscussCount(
					keyword1, keyword2, keyword3, keyword4);
			map.put("praiseCount", praiseCount);
			map.put("discussCount", discussCount);
			map.put("success", true);
			logger.debug("操作成功");
		} catch (Exception e) {
			map.put("success", false);
			logger.debug("操作失败");
		}
		return map;
	}
}
