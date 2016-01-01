package com.puyun.clothingshow.dao;

import java.util.List;
import java.util.Map;

import com.puyun.clothingshow.entity.Goods;
import com.puyun.clothingshow.entity.MadeType;
import com.puyun.clothingshow.entity.PhotoBean;

/**
 * 审核管理相关数据访问接口
 * 
 */
public interface CheckDao
{
    /**
     * 获取成衣秀列表 
     * @param keyword 查询关键字
     * @param start 跳过多少条
     * @param num 取几条
     * @param status 订单状态
     * @return
     * author: ldz
     * @see [类、类#方法、类#成员]
     */
    List<Goods> getClothesList(String keyword, int start, int num);
    
    /**
     * 获取成衣秀个数
     * @param keyword
     * @param status
     * @return
     * author: ldz
     * @see [类、类#方法、类#成员]
     */
    int getClothesListCount(String keyword);
    
    /**
     * 
     * TODO	查询不同状态成衣秀总数.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月8日 下午5:46:57
     * @return
     */
    Map<String, Object> getClothesCount();
    
    /**
     * 
     * TODO	根据成衣秀ID查询详情.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     *   date: 2015年7月13日 下午3:01:38
     * @param orderNo
     * @return
     */
    Goods getClothesDetail(int id);
    
    /**
     * 
     * TODO  修改成衣秀状态.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     *   date: 2015年7月14日 上午10:41:22
     * @param order
     * @return
     */
    boolean updateClothesStatus(int id, int status);
    
    /**
     * 
     * TODO	查询全部制作类型.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月15日 下午2:51:21
     * @return
     */
    List<MadeType> getMadeTypeList();
    
    /**
     * 
     * TODO	修改制作类型.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月15日 下午3:04:47
     * @param id
     * @param type
     * @return
     */
    boolean modifyMadeType(int id, int type);
    
    /**
     * 
     * TODO	查询图片列表.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月16日 上午10:20:09
     * @param start
     * @param num
     * @return
     */
    List<PhotoBean> getPhotoList(int start, int num);
    
    /**
     * 
     * TODO	查询图片列表个数.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月16日 上午10:21:01
     * @return
     */
    int getPhotoListCount();
    
    /**
     * 删除照片
     * @param type 类型
     * @param id 主键
     * @return
     */
    public boolean deletePhoto(int type, int id);

    /**
     * 
     * TODO	根据图片ID和类型查询发布者ID和类型.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月4日 上午10:10:31
     * @param type
     * @param id
     * @return
     */
    Map<String, Object> getWarnInfo(int type, int id);
}
