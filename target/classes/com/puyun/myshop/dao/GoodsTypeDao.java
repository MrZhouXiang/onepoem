package com.puyun.myshop.dao;

import java.util.List;
import java.util.Map;

import com.puyun.myshop.entity.Goods;
import com.puyun.myshop.entity.GoodsType;
import com.puyun.myshop.entity.MadeType;
import com.puyun.myshop.entity.PhotoBean;

/**
 *商品类型管理相关数据访问接口
 * 
 */
public interface GoodsTypeDao
{
    /**
     * 获取商品类型列表 
     * @param keyword 查询关键字
     * @param start 跳过多少条
     * @param num 取几条
     * @param status 订单状态
     * @return
     * author: ldz
     * @see [类、类#方法、类#成员]
     */
    List<GoodsType> getGoodsTypeList(String keyword, int start, int num);
    
    /**
     * 获取商品类型个数
     * @param keyword
     * @param status
     * @return
     * author: ldz
     * @see [类、类#方法、类#成员]
     */
    int getGoodsTypeListCount(String keyword);
    
    /**
     * 
     * TODO	新增商品类型.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月22日 上午10:35:58
     * @param goodsType
     * @return
     */
    boolean addGoodsType(GoodsType goodsType);
    
    /**
     * 
     * TODO	根据ID查询商品类型详情.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月24日 上午10:12:34
     * @param id
     * @return
     */
    GoodsType getGoodsTypeDetail(int id);
    
    /**
     * 
     * TODO	更新商品类型信息.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     *   date: 2015年8月24日 上午10:20:37
     * @param goodsType
     * @return
     */
    boolean updateGoodsType(GoodsType goodsType);
}
