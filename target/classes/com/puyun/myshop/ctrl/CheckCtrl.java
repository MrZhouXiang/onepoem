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
import com.puyun.myshop.entity.Goods;
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
@RequestMapping("/check")
public class CheckCtrl
{
    private static final Logger logger = Logger.getLogger(CheckCtrl.class);
    
    @Autowired
    private CheckDao checkDao;
    
    @Autowired
    private PushCtrl pushCtrl;
    
    public CheckCtrl()
    {
        super();
        logger.debug("创建对象CheckCtrl");
    }
    /**
     * 获取成衣秀列表
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
    @RequestMapping("/getClothesList/{p}")
    public Map<String, Object> getClothesList(HttpServletRequest req, PageBean page, String keyword, @PathVariable int p,
        @RequestParam(defaultValue = "10")int num)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        String urlPath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort(); // url路径
        try
        {
            int start = (p - 1) * num;
            List<Goods> list = checkDao.getClothesList(keyword, start, num);
            int count = checkDao.getClothesListCount(keyword);
            
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
     * TODO	查询不同状态成衣秀数量.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月8日 下午3:45:36
     * @return
     */
    @ResponseBody
    @RequestMapping("/getClothesCount")
    public Map<String, Object> getClothesCount()
    {
        Map<String, Object> map = checkDao.getClothesCount();
        logger.debug("查询成功");
        return map;
    }
    
    
    /**
     * 
     * TODO	获取成衣秀详情.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月10日 上午10:12:22
     * @param id 订单ID
     * @return
     */
    @ResponseBody
    @RequestMapping("/getClothesDetail")
    public Map<String, Object> getClothesDetail(int id)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        Goods goods = checkDao.getClothesDetail(id);
        if(goods != null){
        	map.put("result", goods);
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
     * TODO	修改成衣秀状态.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月10日 上午10:13:29
     * @param buyer
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateClothesStatus")
    public Map<String, Object> updateClothesStatus(int id, int status)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        if(checkDao.updateClothesStatus(id, status)){
            Goods goods = checkDao.getClothesDetail(id);
            //下架操作
            if(status == 0){
            	pushCtrl.pushToPulisherForSoldOut(goods);
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
     * TODO	查询制作类型列表.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     *   date: 2015年7月15日 下午2:54:57
     * @param id
     * @param status
     * @return
     */
    @ResponseBody
    @RequestMapping("/getMadeTypeList")
    public Map<String, Object> getMadeTypeList()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        List<MadeType> madeList = checkDao.getMadeTypeList();
        if(madeList.size() > 0){
        	map.put("success", true);
        	map.put("result", madeList);
        	logger.debug("操作成功");
        }else{
        	map.put("success", false);
        	logger.debug("操作失败");
        }
        return map;
    }
    
    /**
     * 
     * TODO	修改制作类型.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     *   date: 2015年7月15日 下午3:11:18
     * @param id
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping("/modifyMadeType")
    public Map<String, Object> modifyMadeType(int id, int type)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        Goods goods = checkDao.getClothesDetail(id);
      //只有未下架的商品才可以修改制作类型
        if(goods.getIsxiajia() == 0 && checkDao.modifyMadeType(id, type)){
        	map.put("success", true);
        	pushCtrl.pushToPulisher(goods);
        	logger.debug("操作成功");
        }else{
        	map.put("success", false);
        	logger.debug("操作失败");
        }
        return map;
    }
    
    /**
     * 
     * TODO	获取图片列表.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月16日 下午1:21:34
     * @param req
     * @param page
     * @param p
     * @param num
     * @return
     */
    @ResponseBody
    @RequestMapping("/getPhotoList/{p}")
    public Map<String, Object> getPhotoList(HttpServletRequest req, PageBean page, @PathVariable int p,
        @RequestParam(defaultValue = "10")int num)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        String urlPath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort(); // url路径
        try
        {
            int start = (p - 1) * num;
            List<PhotoBean> list = checkDao.getPhotoList(start, num);
            int count = checkDao.getPhotoListCount();
            
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
     * TODO	删除照片.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月3日 下午4:40:21
     * @param type
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/deletePhoto")
    public Map<String, Object> deletePhoto(int type, int id)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        if(checkDao.deletePhoto(type, id)){
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
     * TODO	批量删除照片.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月3日 下午4:40:39
     * @param types
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/batchDeletePhoto")
    public Map<String, Object> batchDeletePhoto(String types, String ids)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag = false;
        String[] tarTypes = types.split(",");
        String[] tarIds = ids.split(",");
        for (int i = 0; i < tarIds.length; i++)
        {
        	flag = checkDao.deletePhoto(Integer.valueOf(tarTypes[i]), Integer.valueOf(tarIds[i]));
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
     * TODO	发送警告.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月3日 下午4:40:21
     * @param type
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/warn")
    public Map<String, Object> warn(int type, int id)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> pushInfoMap = checkDao.getWarnInfo(type, id);
        if(pushInfoMap.size() > 0){
            if(pushCtrl.pushToPulisherForViolation(pushInfoMap)){
            	map.put("success", true);
            	logger.debug("操作成功");
            }else{
            	map.put("success", false);
            	logger.debug("操作失败");
            }
        }else{
        	map.put("success", false);
        	logger.debug("操作失败");
        }
        return map;
    }
}
