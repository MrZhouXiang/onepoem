package com.puyun.myshop.ctrl;

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

import com.puyun.myshop.dao.AppDao;
import com.puyun.myshop.dao.OrderDao;
import com.puyun.myshop.dao.SystemConfigDao;
import com.puyun.myshop.entity.LVBean;
import com.puyun.myshop.entity.OrderBean;
import com.puyun.myshop.entity.OrderForApp;
import com.puyun.myshop.entity.PageBean;

/**
 * 衣氏百秀订单相关请求控制类
 * 
 * @author 姓名
 * @version [版本号, 2014-9-10]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping("/order")
public class OrderCtrl
{
    private static final Logger logger = Logger.getLogger(OrderCtrl.class);
    
    @Autowired
    private OrderDao orderDao;
    
    @Autowired
    private AppDao appDao;
    
    @Autowired
    private PushCtrl pushCtrl;
    
    @Autowired
    private SystemConfigDao systemDao;
    /**
     * 获取订单
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
    @RequestMapping("/getOrderList/{p}")
    public Map<String, Object> getOrderList(HttpServletRequest req, PageBean page, String keyword, @PathVariable int p,
        @RequestParam(defaultValue = "10")int num, @RequestParam(defaultValue = "-1") int status, 
        @RequestParam(defaultValue = "-1")int loanStatus)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        String urlPath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort(); // url路径
        try
        {
            int start = (p - 1) * num;
            List<OrderBean> list = orderDao.getList(keyword, start, num, status, loanStatus);
            int count = orderDao.getListCount(keyword, status);
            
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
     * TODO	查询不同状态订单总数.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月8日 下午3:45:36
     * @return
     */
    @ResponseBody
    @RequestMapping("/getOrderCount")
    public Map<String, Object> getBuyerCount()
    {
        Map<String, Object> map = orderDao.getOrderCount();
        logger.debug("查询成功");
        return map;
    }
    
    /**
     * 
     * TODO	获取订单信息.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月10日 上午10:12:22
     * @param id 订单ID
     * @return
     */
    @ResponseBody
    @RequestMapping("/getOrderDetail")
    public Map<String, Object> getOrderDetail(int id)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        OrderBean order = orderDao.getOrderDetail(id);
        if(order != null){
        	map.put("result", order);
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
     * TODO	修改订单状态.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月10日 上午10:13:29
     * @param buyer
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateOrderStatus")
    public Map<String, Object> updateOrderStatus(int id, int status)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        if(orderDao.updateOrderStatus(id, status)){
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
     * TODO	修改订单放款状态.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月10日 上午10:13:29
     * @param buyer
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateLoanStatus")
    public Map<String, Object> updateLoanStatus(int id, int status)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag = orderDao.updateLoanStatus(id, status);
        
        if(flag){
        	if(status == 1){
        		OrderBean order = orderDao.getOrderDetail(id);
            	double platformFee = (Double) systemDao.getSystemParameter().get("platformFee");
            	pushCtrl.pushToSalerForDeliver(order, platformFee);
        	}
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
     * TODO	查询有限订单列表.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年10月30日 上午11:08:11
     * @return
     */
    @ResponseBody
    @RequestMapping("/getUseableOrderList")
    public Map<String, Object> getUseableOrderList()
    {
    	Map<String, Object> result = new HashMap<String, Object>();
    	 Map<String, List<OrderForApp>> map = AppCtrl.orderMap;
         System.out.println(map.size());
         OrderForApp order = null;
         for (String key : map.keySet())
         {
         	//商品ID
             System.out.println("key:" + key);
             //订单列表
             List<OrderForApp> l = map.get(key);
             System.out.println("l:" + l.size());
             
             // System.out.println("key= " + key + " and value= " + l.get(index));
             // 判断订单是否失效
             order = getOrderForApp(l, key);
             //时间戳
             System.out.println("time:" + order.getTime());
             if(l.size() > 0){
            	 result.put("success", true);
            	 result.put("result", l);
             	logger.debug("操作成功");
             }else{
            	 result.put("success", false);
             	logger.debug("操作失败");
             }
//             long time = 0;
//             if (order != null)
//             {
//                 time = order.getTime();
//                 if (time > 0 && isShiXiao(time))
//                 {// 没有失效不用处理
//                 
//                 }
//                 else
//                 {// 失效，删除失效订单
//                     List<OrderForApp> list = map.get(order.getShangpinID());
//                     list.remove(order);
//                 }
//             }
         }
        
        return result;
    }
    
    public OrderForApp getOrderForApp(List<OrderForApp> l, String key)
    {
        OrderForApp order = null;
        String spID = "";
        for (int i = 0; i < l.size(); i++)
        {
            order = l.get(i);
            spID = order.getShangpinID();
            if (spID.equals(key))
            {
                return order;
            }
            else
            {
                
            }
        }
        return order;
        
    }
    
}
