package com.puyun.clothingshow.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.puyun.clothingshow.dao.AppDao;
import com.puyun.clothingshow.dao.UserDao;
import com.puyun.clothingshow.entity.Buyer;
import com.puyun.clothingshow.entity.LVBean;
import com.puyun.clothingshow.entity.PageBean;
import com.puyun.clothingshow.entity.Saler;
import com.puyun.clothingshow.entity.UserSize;

/**
 * 衣氏百秀客户商户相关请求控制类
 * 
 * @author 姓名
 * @version [版本号, 2014-9-10]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping("/user")
public class UserCtrl
{
    private static final Logger logger = Logger.getLogger(UserCtrl.class);
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private PushCtrl pushCtrl;
    
    @Autowired
    private AppDao appDao;
    
    public UserCtrl()
    {
        super();
        logger.debug("创建对象UserCtrl");
    }
    
    /**
     *  查询买家用户列表
     * 
     * @param req
     * @param keyword 查询关键字
     * @param p 当前页
     * @param num 取几条数据
     * @return
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/getBuyerList/{p}")
    public Map<String, Object> getBuyerList(HttpServletRequest req, PageBean page, String keyword,
    		@PathVariable int p, @RequestParam(defaultValue = "10")int num, 
    		@RequestParam(defaultValue="-1")int status)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        String urlPath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort(); // url路径
        try
        {
            int start = (p - 1) * num;
            List<Buyer> list = userDao.getBuyerList(keyword, start, num,status);
            int count = userDao.getBuyerListCount(keyword);
            
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
     * TODO	查询不同状态用户总数.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月8日 下午3:45:36
     * @return
     */
    @ResponseBody
    @RequestMapping("/getBuyerCount")
    public Map<String, Object> getBuyerCount()
    {
        Map<String, Object> map = userDao.getBuyerCount();
        logger.debug("查询成功");
        return map;
    }
    
    /**
     * 
     * TODO	获取买家信息.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月10日 上午10:12:22
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/getBuyerInfo")
    public Map<String, Object> getBuyerInfo(int id)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        Buyer buyer = userDao.getBuyerInfo(id);
        if(buyer != null){
        	map.put("result", buyer);
        	map.put("success", true);
        	logger.debug("查询成功");
        }else{
        	map.put("success", false);
        	logger.debug("查询失败");
        }
        return map;
    }
    
    /**
     * 
     * TODO	更新买家用户信息.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月10日 上午10:13:29
     * @param buyer
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateBuyerInfo")
    public Map<String, Object> updateBuyerInfo(Buyer buyer)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        if(userDao.updateBuyerInfo(buyer)){
        	map.put("success", true);
        	logger.debug("操作成功");
        }else{
        	map.put("success", false);
        	logger.debug("操作失败");
        }
        return map;
    }
    
    /**
     *  查询卖家用户列表
     * 
     * @param req
     * @param keyword 查询关键字
     * @param p 当前页
     * @param num 取几条数据
     * @return
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/getSalerList/{p}")
    public Map<String, Object> getSalerList(HttpServletRequest req, PageBean page, String keyword,@PathVariable int p, @RequestParam(defaultValue = "10")int num, @RequestParam(defaultValue="-1")int status)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        String urlPath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort(); // url路径
        try
        {
            int start = (p - 1) * num;
            List<Saler> list = userDao.getSalerList(keyword, start, num,status);
            int count = userDao.getSalerListCount(keyword);
            
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
     * TODO	查询不同状态用户总数.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月8日 下午3:45:36
     * @return
     */
    @ResponseBody
    @RequestMapping("/getSalerCount")
    public Map<String, Object> getSalerCount()
    {
        Map<String, Object> map = userDao.getSalerCount();
        logger.debug("查询成功");
        return map;
    }
    
    /**
     * 
     * TODO	获取卖家信息.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月10日 上午10:12:22
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/getSalerInfo")
    public Map<String, Object> getSalerInfo(int id)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        Saler saler = userDao.getSalerInfo(id);
        if(saler != null){
        	map.put("result", saler);
        	map.put("success", true);
        	logger.debug("查询成功");
        }else{
        	map.put("success", false);
        	logger.debug("查询失败");
        }
        return map;
    }
    
    /**
     * 
     * TODO	更新卖家用户信息.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月10日 上午10:13:29
     * @param buyer
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateSalerInfo")
    public Map<String, Object> updateSalerInfo(Saler saler)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        if(userDao.updateSalerInfo(saler)){
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
     * TODO	冻结用户指定天数.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月18日 上午11:33:04
     * @param userId
     * @param days
     * @param userType
     * @return
     */
    @ResponseBody
    @RequestMapping("/frozenUserForDays")
    public Map<String, Object> frozenUserForDays(int userId, int days, String userType)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        if(userDao.frozenUserForDays(userId, days, userType)){
        	try {
            	pushCtrl.pushToPulisherForSalerFrozen(userId);
            	pushCtrl.forceOfflineToUser(String.valueOf(userId), userType, days);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
	        	map.put("success", true);
	        	logger.debug("操作成功");
			}
        }else{
        	map.put("success", false);
        	logger.debug("操作失败");
        }
        return map;
    }
    
    /**
     * 
     * TODO	激活用户.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月18日 下午2:50:03
     * @param userId
     * @param userType
     * @return
     */
    @ResponseBody
    @RequestMapping("/unlockUser")
    public Map<String, Object> unlockUser(int userId, String userType)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        if(userDao.unlockUser(userId, userType)){
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
     * TODO	查询用户尺寸列表.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月19日 上午9:32:28
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping("/getUsersizeList")
    public Map<String, Object> getUsersizeList(int userId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        int loginId = userDao.getLoginId(userId);
        List<UserSize> list = appDao.getMySizeList(loginId + "");
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
     * TODO	获取用户尺寸详情.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月19日 上午10:04:15
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping("/getUsersizeDetail")
    public Map<String, Object> getUsersizeDetail(int userId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        UserSize usersize = userDao.getUserSizeDetail(userId);
        if(usersize != null){
        	map.put("success", true);
        	map.put("result", usersize);
        	logger.debug("操作成功");
        }else{
        	map.put("success", false);
        	logger.debug("操作失败");
        }
        return map;
    }
    
    /**
     * 
     * TODO	修改用户尺寸信息.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月19日 上午10:31:02
     * @param usersize
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateUsersize")
    public Map<String, Object> updateUsersize(UserSize usersize)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        if(userDao.updateUserSize(usersize)){
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
     * TODO	查询用户细分等级列表.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月19日 下午2:12:33
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping("/getUserLvList")
    public Map<String, Object> getUserLvList(int userId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        List<LVBean> list = userDao.getUserLvList(userId);
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
     * TODO	获取用户尺寸详情.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月19日 上午10:04:15
     * @param id 制作经验表主键ID
     * @param userId 用户ID
     * @param madeTypeId 制作类型ID
     * @return
     */
    @ResponseBody
    @RequestMapping("/getLvDetail")
    public Map<String, Object> getLvDetail(int id, String userId, String madeTypeId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        LVBean lvBean = null;
        if(id == -1){
        	LVBean lv = new LVBean();
        	lv.setZhizuoLxID(madeTypeId);
        	lv.setYonghuID(userId);
        	int pk = userDao.addLv(lv);
        	lvBean = userDao.getLvDetail(pk);
        }else{
        	lvBean = userDao.getLvDetail(id);
        }
        if(lvBean != null){
        	map.put("success", true);
        	map.put("result", lvBean);
        	logger.debug("操作成功");
        }else{
        	map.put("success", false);
        	logger.debug("操作失败");
        }
        return map;
    }
    
    /**
     * 
     * TODO	修改等级信息.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月19日 上午10:31:02
     * @param usersize
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateLv")
    public Map<String, Object> updateLv(LVBean lv)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        if(userDao.updateLv(lv)){
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
     * TODO	根据经验获取等级.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月19日 上午10:31:02
     * @param usersize
     * @return
     */
    @ResponseBody
    @RequestMapping("/getLvByExp")
    public Map<String, Object> getLvByExp(int exp)
    {
    	
        Map<String, Object> map = new HashMap<String, Object>();
        int result = 0;
        try {
        	result = userDao.getLvByExp(exp);
        	map.put("lv", result);
        	map.put("success", true);
		} catch (Exception e) {
			logger.error(e);
        	map.put("lv", result);
        	map.put("success", false);
		}
        
        return map;
    }
}
