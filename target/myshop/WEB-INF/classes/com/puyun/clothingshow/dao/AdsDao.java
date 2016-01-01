package com.puyun.clothingshow.dao;

import java.util.List;
import java.util.Map;

import com.puyun.clothingshow.entity.Ads;
import com.puyun.clothingshow.entity.Goods;
import com.puyun.clothingshow.entity.GoodsType;
import com.puyun.clothingshow.entity.MadeType;
import com.puyun.clothingshow.entity.PhotoBean;

/**
 *广告位管理相关数据访问接口
 * 
 */
public interface AdsDao
{
    /**
     * 获取广告位列表 
     * @param keyword 查询关键字
     * @param start 跳过多少条
     * @param num 取几条
     * @param status 订单状态
     * @return
     * author: ldz
     * @see [类、类#方法、类#成员]
     */
    List<Ads> getAdsList(String keyword, int start, int num);
    
    /**
     * 获取广告位数量
     * @param keyword
     * @param status
     * @return
     * author: ldz
     * @see [类、类#方法、类#成员]
     */
    int getAdsListCount(String keyword);
    
    /**
     * 
     * TODO	新增广告位.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月22日 上午10:35:58
     * @param 
     * @return
     */
    boolean addAds(Ads ad);
    
    /**
     * 
     * TODO	根据手机号查询商户是否存在.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月22日 下午2:39:05
     * @param tel
     * @return
     */
    boolean isExist(String tel);
    
    /**
     * 
     * TODO	获取商户ID.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月23日 下午1:42:22
     * @param tel
     * @return
     */
    String getSalerId(String tel);
}
