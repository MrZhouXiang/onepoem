package com.puyun.myshop.ctrl;

import java.util.Arrays;
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

import com.puyun.myshop.base.Constants;
import com.puyun.myshop.dao.AdsDao;
import com.puyun.myshop.dao.CheckDao;
import com.puyun.myshop.entity.Ads;
import com.puyun.myshop.entity.Goods;
import com.puyun.myshop.entity.MadeType;
import com.puyun.myshop.entity.PageBean;
import com.puyun.myshop.entity.PhotoBean;

/**
 * 广告位管理相关请求控制类
 * 
 * @author 姓名
 * @version [版本号, 2014-9-10]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping("/ads")
public class AdsCtrl
{
    private static final Logger logger = Logger.getLogger(AdsCtrl.class);
    
    @Autowired
    private AdsDao adsDao;
    
    public AdsCtrl()
    {
        super();
        logger.debug("创建对象AdsCtrl");
    }
    /**
     * 获取广告位列表
     * 
     * @param req
     * @param keyword 查询关键字
     * @param p 当前页
     * @param num 取几条数据
     * @param status 订单状态
     * @return author: ldz
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/getAddsList/{p}")
    public Map<String, Object> getAddsList(HttpServletRequest req, PageBean page, String keyword, @PathVariable int p,
        @RequestParam(defaultValue = "10")int num)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        String urlPath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort(); // url路径
        try
        {
            int start = (p - 1) * num;
            List<Ads> list = adsDao.getAdsList(keyword, start, num);
            int count = adsDao.getAdsListCount(keyword);
            
            if (list == null)
            {
                map.put("success", false);
            }
            else
            {
                map.put("success", true);
                map.put("url", urlPath);
                map.put("result", list);
                
                map.put("totalPages", ((count - 1) / num) + 1);
                map.put("total", count);
                map.put("curPage", p);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return map;
    }
    
    /**
     * 
     * TODO	新增广告位.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月23日 下午4:32:04
     * @param req
     * @param telStr
     * @param titleStr
     * @param descStr
     * @param coverStr
     * @param positionStr
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/new")
    public Map<String,Object> addAds(HttpServletRequest req, String telStr, String titleStr, String descStr, 
    		String coverStr, String positionStr){
        
        Map<String, Object> map = new HashMap<String, Object>();
        String urlPath = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort() + Constants.DEFAULT_AD_PATH; //url路径
        String[] telArray = telStr.split(",");
        String[] titleArray = titleStr.split(",");
        String[] descArray = descStr.split(",");
        String[] coverArray = coverStr.split(",");
        String[] positionArray = positionStr.split(",");
        boolean flag = false;
        Ads ad = null;
        for(int i = 0; i < 3; i ++){
        	ad = new Ads();
            ad.setTupian(urlPath + coverArray[i]);
            ad.setTitle(titleArray[i]);
            ad.setShuoming(descArray[i]);
            ad.setUrl(adsDao.getSalerId(telArray[i]));
            ad.setIsValue(positionArray[i]);
            flag = adsDao.addAds(ad);
        }
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
     * TODO	根据手机号判断商户是否存在.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月23日 下午4:35:53
     * @param tel
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/isSalerExist")
    public Map<String,Object> isSalerExist(String tel){
    	Map<String, Object> map = new HashMap<String, Object>();
    	if(adsDao.isExist(tel)){
        	map.put("success", true);
        	logger.debug("操作成功");
    	}else{
        	map.put("success", false);
        	logger.debug("操作失败");
    	}
    	return map;
    }
}
