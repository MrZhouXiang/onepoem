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

import com.puyun.myshop.dao.CheckDao;
import com.puyun.myshop.dao.GoodsTypeDao;
import com.puyun.myshop.entity.Goods;
import com.puyun.myshop.entity.GoodsType;
import com.puyun.myshop.entity.MadeType;
import com.puyun.myshop.entity.PageBean;
import com.puyun.myshop.entity.PhotoBean;

/**
 * 审核管理相关请求控制类
 * 
 * @author 姓名
 * @version [版本号, 2014-9-10]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping("/goodsType")
public class GoodsTypeCtrl
{
    private static final Logger logger = Logger.getLogger(GoodsTypeCtrl.class);
    
    @Autowired
    private GoodsTypeDao goodsTypeDao;
    
    public GoodsTypeCtrl()
    {
        super();
        logger.debug("创建对象GoodsTypeCtrl");
    }
    /**
     * 获取商品类型列表
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
    @RequestMapping("/getGoodsTypeList/{p}")
    public Map<String, Object> getGoodsTypeList(HttpServletRequest req, PageBean page, String keyword, @PathVariable int p,
        @RequestParam(defaultValue = "10")int num)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        String urlPath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort(); // url路径
        try
        {
            int start = (p - 1) * num;
            List<GoodsType> list = goodsTypeDao.getGoodsTypeList(keyword, start, num);
            int count = goodsTypeDao.getGoodsTypeListCount(keyword);
            
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
     * TODO	新增商品类型.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月10日 上午10:13:29
     * @param buyer
     * @return
     */
    @ResponseBody
    @RequestMapping("/addGoodsType")
    public Map<String, Object> addGoodsType(GoodsType goodsType)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        if(goodsTypeDao.addGoodsType(goodsType)){
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
     * TODO	根据ID查询商品类型详情.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月24日 上午10:13:21
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/getGoodsTypeDetail")
    public Map<String, Object> getGoodsTypeDetail(int id)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        GoodsType goodsType = goodsTypeDao.getGoodsTypeDetail(id);
        if(goodsType != null){
        	map.put("success", true);
        	map.put("result", goodsType);
        	logger.debug("操作成功");
        }else{
        	map.put("success", false);
        	logger.debug("操作失败");
        }
        return map;
    }
    
    /**
     * 
     * TODO	修改商品类型信息.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月24日 上午10:28:31
     * @param goodsType
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateGoodsTypeDetail")
    public Map<String, Object> updateGoodsTypeDetail(GoodsType goodsType)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        if(goodsTypeDao.updateGoodsType(goodsType)){
        	map.put("success", true);
        	logger.debug("操作成功");
        }else{
        	map.put("success", false);
        	logger.debug("操作失败");
        }
        return map;
    }
}
