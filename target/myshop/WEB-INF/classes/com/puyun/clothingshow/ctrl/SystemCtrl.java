package com.puyun.clothingshow.ctrl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.puyun.clothingshow.base.Constants;
import com.puyun.clothingshow.base.ContextUtil;
import com.puyun.clothingshow.dao.SystemConfigDao;
import com.puyun.clothingshow.entity.AdminUser;
import com.puyun.clothingshow.entity.Apk;
import com.puyun.clothingshow.entity.MadeTypeLvBean;
import com.puyun.clothingshow.entity.PageBean;

/**
 * Created by AI-MASK on 14-8-4.
 */
@Controller
public class SystemCtrl {

	private static final Logger logger = Logger.getLogger(SystemCtrl.class);
	
	@Autowired
	private SystemConfigDao sysDao;
	
	@Autowired
	private PushCtrl pushCtrl;

	/**
	 * 管理员登录 URL: /doLogin
	 * 
	 * @param name
	 * @param password
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doLogin(@RequestParam String name,
			@RequestParam String password, Model model,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 登录后台系统的用户可以是超级管理员、普通管理员，这里把登录用户信息存放在Session中
		HttpSession session = request.getSession();
		AdminUser user = sysDao.doLogin(name, password);
		if (user != null) {
			session.setAttribute(Constants.USER_INFO_SESSION, user);
			map.put("success", true);
		} else {
			map.put("success", false);
			map.put("message", "用户名不存在或密码错误");
		}

		return map;
	}

	/**
	 * 管理员退出 URL: /logout
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "index";
	}


	/**
	 * 管理员密码修改 URL: /updatePwd
	 * 
	 * @param oldPwd
	 * @param newPwd
	 * @param confirmPwd
	 * @return
	 */
	@RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePwd(HttpServletRequest req,@RequestParam String oldPwd,
			@RequestParam String newPwd) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = false;
		AdminUser loginUser = ContextUtil.getLoginInfo(req);
		if(oldPwd.equals(sysDao.getOldPwd(loginUser.getLoginName()))){
			flag = sysDao.updatePwd(loginUser.getLoginName(), newPwd);
		}
		if (flag) {
			map.put("message", "修改成功");
			map.put("success", true);
		} else {
			map.put("message", "修改失败, 请重新输入");
			map.put("success", false);
		}
		return map;
	}

	/**
	 * 手机扫描二维码或电脑直接链接到该方法
	 * 
	 * @param request
	 * @param res
	 * @param pageName
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "/downloadApp")
	@ResponseBody
	public ModelAndView downloadApp(HttpServletRequest request,
			HttpServletResponse res,
			@RequestParam(required = false) String pageName) {
		Map<String, Object> map = new HashMap<String, Object>();
		String userAgent = request.getHeader("User-Agent");
		ModelAndView mav = null;
		// UserAgent keyword detection of Normal devices
		if (userAgent != null) {
			userAgent = userAgent.toLowerCase();
			try {
				if (userAgent.contains("mac os")) {
					// 跳转到IOS下载地址
//					res.sendRedirect(Constants.DOWNLOAD_LINK_PATH_IOS);
				} else if (userAgent.contains("android")) {
					// 从数据库获取Apk下载地址
					Apk apk = sysDao.getApk();
					mav = new ModelAndView("WEB-INF/jsp/app/download_app");
					mav.addObject("url", apk.getUrl());
				} else {
					// 跳转到下载Web页面
					mav = new ModelAndView("/download");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (map.size() > 0) {
			map.put("code", "200");
		} else {
			map.put("code", "401");
		}
		return mav;
	}

	@RequestMapping(value = "/isSuperAdmin")
	@ResponseBody
	public Map<String, Object> isSuperAdmin(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = ContextUtil.isSuperAdmin(request);
		map.put("result", flag);
		return map;
	}
	
	/**
	 * 
	 * TODO	查询系统参数.
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 周震
	 * date: 2015年8月20日 下午2:25:12
	 * @return
	 */
	@RequestMapping(value = "/getSystemParameter")
	@ResponseBody
	public Map<String, Object> getSystemParameter() {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> paraMap = sysDao.getSystemParameter();
		double platformFee = (Double) paraMap.get("platformFee");
		int buyoutCount = (Integer) paraMap.get("buyoutCount");
		double tradeAmount = (Double) paraMap.get("tradeAmount");
		int discussCount = (Integer) paraMap.get("discussCount");
		try {
			map.put("success", true);
			map.put("platformFee", platformFee);
			map.put("buyoutCount", buyoutCount);
			map.put("tradeAmount", tradeAmount);
			map.put("discussCount", discussCount);
        	logger.debug("操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			map.put("success", false);
        	logger.debug("操作失败");
        	e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 
	 * TODO	修改系统参数.
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 周震
	 * date: 2015年8月17日 下午2:35:21
	 * @param platformFee
	 * @return
	 */
	@RequestMapping(value = "/updateSystemParameter")
	@ResponseBody
	public Map<String, Object> updateSystemParameter(@RequestParam(required=false)String platformFee, 
			@RequestParam(required=false)String buyoutCount, 
			@RequestParam(required=false)String tradeAmount, 
			@RequestParam(required=false)String discussCount) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = sysDao.updateSystemParameter(platformFee, buyoutCount, tradeAmount, discussCount);
		if(flag){
			map.put("success", true);
        	logger.debug("操作成功");
		}else{
			map.put("success", false);
        	logger.debug("操作失败");
		}
		return map;
	}
	
	/**
	 * 
	 * TODO	查询制作类型等级列表.
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 周震
	 * date: 2015年8月20日 上午10:08:57
	 * @param req
	 * @param page
	 * @param p
	 * @param num
	 * @return
	 */
	@ResponseBody
    @RequestMapping("/getMadeTypeLvList/{p}")
    public Map<String, Object> getMadeTypeLvList(HttpServletRequest req, PageBean page,
    		@PathVariable int p, @RequestParam(defaultValue = "10")int num)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        String urlPath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort(); // url路径
        try
        {
            int start = (p - 1) * num;
            List<MadeTypeLvBean> list = sysDao.getMadeTypeLvBeanList(start, num);
            int count = sysDao.getListCount();
            if (list == null)
            {
                map.put("success", false);
            }
            else
            {
                map.put("success", true);
                map.put("url", urlPath);
                map.put("result", list);
                
                map.put("totalPage", ((count - 1) / num) + 1);
                map.put("total", count);
                map.put("curPage", p);
                logger.debug("查询成功");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.debug("查询失败");
        }
        return map;
    }
	
	/**
	 * 
	 * TODO	根据ID查询制作类型等级详情.
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 周震
	 * date: 2015年8月20日 上午10:52:17
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getMadeTypeLvDetail")
	@ResponseBody
	public Map<String, Object> getMadeTypeLvDetail(int id) {
		Map<String, Object> map = new HashMap<String, Object>();
		MadeTypeLvBean lv = sysDao.getMadeTypeLvDetail(id);
		if(lv != null){
			map.put("success", true);
			map.put("result", lv);
        	logger.debug("操作成功");
		}else{
			map.put("success", false);
        	logger.debug("操作失败");
		}
		return map;
	}
	
	/**
	 * 
	 * TODO	更新制作类型等级.
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 周震
	 * date: 2015年8月20日 上午11:23:44
	 * @param lv
	 * @return
	 */
	@RequestMapping(value = "/updateMadeTypeLv")
	@ResponseBody
	public Map<String, Object> updateMadeTypeLv(MadeTypeLvBean lv) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(sysDao.updateMadeTypeLv(lv)){
			map.put("success", true);
        	logger.debug("操作成功");
		}else{
			map.put("success", false);
        	logger.debug("操作失败");
		}
		return map;
	}
	
	/**
	 * 
	 * TODO	推送系统消息.
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 周震
	 * date: 2015年8月21日 下午3:30:21
	 * @param title
	 * @param content
	 * @return
	 */
	@RequestMapping(value = "/pushSystemMsg")
	@ResponseBody
	public Map<String, Object> pushSystemMsg(String title, String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(pushCtrl.pushSystemMsg(title, content)){
			map.put("success", true);
        	logger.debug("操作成功");
		}else{
			map.put("success", false);
        	logger.debug("操作失败");
		}
		return map;
	}
	
	/**
	 * 
	 * TODO	查询管理员列表.
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 周震
	 * date: 2015年8月25日 下午5:04:09
	 * @return
	 */
	@RequestMapping(value = "/getManagerList")
	@ResponseBody
	public Map<String, Object> getManagerList() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<AdminUser> list = sysDao.getManagerList();
		if(list.size() > 0){
			map.put("success", true);
			map.put("result", list);
        	logger.debug("操作成功");
		}else{
			map.put("success", false);
        	logger.debug("操作失败");
		}
		return map;
	}
	
	/**
	 * 
	 * TODO	重置密码.
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 周震
	 * date: 2015年8月25日 下午5:04:26
	 * @param loginName
	 * @return
	 */
	@RequestMapping(value = "/resetPwd")
	@ResponseBody
	public Map<String, Object> resetPwd(String loginName) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(sysDao.resetPwd(loginName)){
			map.put("success", true);
        	logger.debug("操作成功");
		}else{
			map.put("success", false);
        	logger.debug("操作失败");
		}
		return map;
	}
	
	/**
	 * 
	 * TODO	修改管理员信息.
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 周震
	 * date: 2015年8月25日 下午5:04:59
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/modifyManager")
	@ResponseBody
	public Map<String, Object> modifyManager(AdminUser user) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(sysDao.modifyManager(user)){
			map.put("success", true);
        	logger.debug("操作成功");
		}else{
			map.put("success", false);
        	logger.debug("操作失败");
		}
		return map;
	}
	
	/**
	 * 
	 * TODO	新增管理员.
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 周震
	 * date: 2015年8月25日 下午5:05:18
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/addManager")
	@ResponseBody
	public Map<String, Object> addManager(AdminUser user) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(sysDao.isManagerExist(user.getLoginName())){
				map.put("code", "4000");
				map.put("msg", "该管理员已存在, 请重新输入");
			}else{
				sysDao.addManager(user);
				map.put("code", "2000");
				map.put("msg", "新增成功, 初始密码为123456");
	        	logger.debug("新增成功");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("code", "5000");
			map.put("msg", "新增失败, 请确认后重新提交");
        	logger.debug("新增失败");
		}

		return map;
	}
	
	/**
	 * 
	 * TODO	上传APK安装包.
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 周震
	 * date: 2015年8月25日 下午5:57:38
	 * @param request
	 * @return
	 */
	@RequestMapping("/uploadAndroidApk")
	@ResponseBody
	public Map<String, Object> uploadApk(Apk apk) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(sysDao.uploadApk(apk)){
			map.put("success", true);
        	logger.debug("操作成功");
		}else{
			map.put("success", false);
        	logger.debug("操作失败");
		}
		return map;
		
	}
	
	/**
	 * 
	 * TODO	删除管理员.
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 周震
	 * date: 2015年8月25日 下午5:04:59
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/deleteManager")
	@ResponseBody
	public Map<String, Object> deleteManager(int id) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(sysDao.deleteManager(id)){
			map.put("success", true);
        	logger.debug("操作成功");
		}else{
			map.put("success", false);
        	logger.debug("操作失败");
		}
		return map;
	}
}