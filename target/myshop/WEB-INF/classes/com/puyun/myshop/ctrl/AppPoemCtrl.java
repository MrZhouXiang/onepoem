package com.puyun.myshop.ctrl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

//import com.bcloud.msg.http.HttpSender;
import com.puyun.myshop.alipay.util.AlipayNotify;
import com.puyun.myshop.base.Constants;
import com.puyun.myshop.base.util.Utils;
import com.puyun.myshop.dao.AppDao;
import com.puyun.myshop.entity.Account;
import com.puyun.myshop.entity.Address;
import com.puyun.myshop.entity.Ads;
import com.puyun.myshop.entity.Apk;
import com.puyun.myshop.entity.Buyer;
import com.puyun.myshop.entity.BuyerForOrder;
import com.puyun.myshop.entity.ColorForApp;
import com.puyun.myshop.entity.Comment;
import com.puyun.myshop.entity.Express;
import com.puyun.myshop.entity.FileMeta;
import com.puyun.myshop.entity.GoodsForApp;
import com.puyun.myshop.entity.GoodsPhoto;
import com.puyun.myshop.entity.GoodsType;
import com.puyun.myshop.entity.HistoryShowForApp;
import com.puyun.myshop.entity.LVBean;
import com.puyun.myshop.entity.MouldForApp;
import com.puyun.myshop.entity.OrderForApp;
import com.puyun.myshop.entity.PhotoBean;
import com.puyun.myshop.entity.PoemMod;
import com.puyun.myshop.entity.Result;
import com.puyun.myshop.entity.Saler;
import com.puyun.myshop.entity.User;
import com.puyun.myshop.entity.UserSize;
import com.puyun.myshop.entity.UserType;

/**
 * 处理来自App端的请求
 */
@Controller
@RequestMapping("/poem")
public class AppPoemCtrl {
	private static final Logger logger = Logger.getLogger(AppPoemCtrl.class);

	public static Map<String, User> tokenMap = new HashMap<String, User>();

	// 下单时用于判断下单个数
	public static Map<String, List<OrderForApp>> orderMap = new HashMap<String, List<OrderForApp>>();

	@Autowired
	private AppDao appDao;

	@Autowired
	private PushCtrl pushCtrl;

	public AppPoemCtrl() {
		super();
		logger.debug("创建对象AppCtrl");
	}


	/**
	 * 获取诗词列表
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "/getPoemList")
	@ResponseBody
	public Result getPoemList(int id, int size) {
		Map<String, Object> result = new HashMap<String, Object>();
		String code = "";
		try {
			// 判断用户是否封号
			// 登陆
			List<PoemMod> list = appDao.getPoemList(id, size);
			result.put("list", list);
			code = "2000";
			logger.debug("登陆成功, 已登录用户数：" + tokenMap.size());
		} catch (Exception e) {
			code = "5000";
			result.put("reason", "服务器异常");
			logger.error(e.getMessage(), e);
		}

		return new Result(code, result);
		
		
	}
	
	@RequestMapping(value = "/getPoemListTest")
	@ResponseBody
	public List<PoemMod> getPoemListTest(int id, int size) {
		List<PoemMod> list = appDao.getPoemList(id, size);
		return list;
	}

	
}
