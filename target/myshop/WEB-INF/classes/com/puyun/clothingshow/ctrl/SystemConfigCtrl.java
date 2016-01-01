package com.puyun.clothingshow.ctrl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.puyun.clothingshow.dao.SystemConfigDao;

/**
 * Created by AI-MASK on 14-8-4.
 */
@Controller
@RequestMapping("/config")
public class SystemConfigCtrl
{
    private static final Logger logger = Logger.getLogger(SystemConfigCtrl.class);
    
    @Autowired
    private SystemConfigDao configDao;
    
    /**
     * 更新活动状态
     * 
     * @param status
     * @param request
     * @return author: ldz
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/activity/updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateActivityStatus(String status, HttpServletRequest request)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            // 1：活动进行中 2：活动已结束
            // 非“1”、“2”不处理，防止非法字符
            if ("1".equals(status) || "2".equals(status))
            {
                configDao.updateKV("aictivity_status", status);
                logger.debug("更新活动状态");
            }
            
            map.put("success", true);
        }
        catch (Exception e)
        {
            map.put("success", false);
            e.printStackTrace();
        }
        
        return map;
    }

    /**
     * 获取当前活动状态
     * @param request
     * @return
     * author: ldz
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/activity/getStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateActivityStatus(HttpServletRequest request)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            String status = configDao.getKV("aictivity_status");
            map.put("success", true);
            map.put("status", status);
        }
        catch (Exception e)
        {
            map.put("success", false);
            e.printStackTrace();
        }
        
        return map;
    }
}