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

import com.puyun.myshop.base.Constants;
import com.puyun.myshop.dao.ModelDao;
import com.puyun.myshop.entity.Model;
import com.puyun.myshop.entity.PageBean;

/**
 * 模板管理相关请求控制类
 * 
 * @author 姓名
 * @version [版本号, 2014-9-10]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping("/model")
public class ModelCtrl
{
    private static final Logger logger = Logger.getLogger(ModelCtrl.class);
    
    @Autowired
    private ModelDao modelDao;
    
    public ModelCtrl()
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
    @RequestMapping("/getModelList/{p}")
    public Map<String, Object> getAddsList(HttpServletRequest req, PageBean page, String keyword, @PathVariable int p,
        @RequestParam(defaultValue = "10")int num)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        String urlPath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort(); // url路径
        try
        {
            int start = (p - 1) * num;
            List<Model> list = modelDao.getModelList(keyword, start, num);
            int count = modelDao.getModelCount(keyword);
            
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
     * TODO	新增模板.
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
    public Map<String,Object> addModel(HttpServletRequest req, Model model, String photoNameStr, String photoTypeStr){
        
        Map<String, Object> map = new HashMap<String, Object>();
        String urlPath = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort() + Constants.DEFAULT_MODEL_PATH; //url路径
        String[] photoNameArray = photoNameStr.split(",");
        String[] photoUrlArray = new String[photoNameArray.length];
        for(int i = 0; i < photoNameArray.length; i ++){
        	photoUrlArray[i] = urlPath + photoNameArray[i];
        }
        String[] photoTypeArray = photoTypeStr.split(",");
        boolean flag = modelDao.addModel(model, photoNameArray, photoUrlArray, photoTypeArray);
        if(flag){
        	map.put("success", true);
        	logger.debug("操作成功");
        }else{
        	map.put("success", false);
        	logger.debug("操作失败");
        }
        return map;
    }
    
}
