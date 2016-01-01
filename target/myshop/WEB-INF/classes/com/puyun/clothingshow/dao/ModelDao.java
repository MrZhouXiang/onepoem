package com.puyun.clothingshow.dao;

import java.util.List;

import com.puyun.clothingshow.entity.GoodsType;
import com.puyun.clothingshow.entity.Model;
import com.puyun.clothingshow.entity.MouldForApp;

/**
 *模板管理相关数据访问接口
 * 
 */
public interface ModelDao
{
    /**
     * 获取模板列表 
     * @param keyword 查询关键字
     * @param start 跳过多少条
     * @param num 取几条
     * @param status 订单状态
     * @return
     * author: ldz
     * @see [类、类#方法、类#成员]
     */
    List<Model> getModelList(String keyword, int start, int num);
    
    /**
     * 获取模板数量
     * @param keyword
     * @param status
     * @return
     * author: ldz
     * @see [类、类#方法、类#成员]
     */
    int getModelCount(String keyword);
    
    /**
     * 
     * TODO	新增模板.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月24日 上午11:29:09
     * @param model
     * @param photoName 图片名称
     * @param photoUrl 图片URL
     * @param photoType 图片类型
     * @return
     */
    boolean addModel(Model model, String[] photoName, String[] photoUrl, String[] photoType);
    
}
