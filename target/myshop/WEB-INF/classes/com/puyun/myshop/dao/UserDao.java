package com.puyun.myshop.dao;

import java.util.List;
import java.util.Map;

import com.puyun.myshop.entity.Buyer;
import com.puyun.myshop.entity.LVBean;
import com.puyun.myshop.entity.Saler;
import com.puyun.myshop.entity.UserSize;

/**
 * 用户管理相关数据访问接口
 * 
 * @author  ldz
 * 创建时间:  2015-2-28
 */
public interface UserDao
{
    /**
     * 获取买家列表 
     * @param keyword 查询关键字
     * @param start 跳过多少条
     * @param num 取几条
     * @param status 1: 锁定, 0: 未锁定
     * @return
     * author: ldz
     * @see [类、类#方法、类#成员]
     */
    List<Buyer> getBuyerList(String keyword, int start, int num, int status);
    
    /**
     * 查询不同状态用户总数
     * @param keyword
     * @param status
     * @return
     * author: ldz
     * @see [类、类#方法、类#成员]
     */
    int getBuyerListCount(String keyword);
    
    /**
     * 
     * TODO	查询用户总数.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月8日 下午5:46:57
     * @return
     */
    Map<String, Object> getBuyerCount();
    
    /**
     * 
     * TODO	修改买家个人信息.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月8日 下午5:50:52
     * @param buyer
     * @return
     */
    boolean updateBuyerInfo(Buyer buyer);
    
    /**
     * 
     * TODO	根据ID查询买家个人信息.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月9日 下午4:00:30
     * @param id
     * @return
     */
    Buyer getBuyerInfo(int id);
    
    /**
     * 获取卖家列表 
     * @param keyword 查询关键字
     * @param start 跳过多少条
     * @param num 取几条
     * @param status 1: 锁定, 0: 未锁定
     * @return
     * author: ldz
     * @see [类、类#方法、类#成员]
     */
    List<Saler> getSalerList(String keyword, int start, int num, int status);
    
    /**
     * 获取卖家用户数
     * @param keyword
     * @param status
     * @return
     * author: ldz
     * @see [类、类#方法、类#成员]
     */
    int getSalerListCount(String keyword);
    
    /**
     * 
     * TODO	查询不同状态用户总数.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月8日 下午5:46:57
     * @return
     */
    Map<String, Object> getSalerCount();
    
    /**
     * 
     * TODO	修改卖家个人信息.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月8日 下午5:50:52
     * @param buyer
     * @return
     */
    boolean updateSalerInfo(Saler saler);
    
    /**
     * 
     * TODO	根据ID查询卖家个人信息.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月9日 下午4:00:30
     * @param id
     * @return
     */
    Saler getSalerInfo(int id);
    
    /**
     * 
     * TODO	冻结用户指定天数.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月18日 上午11:17:33
     * @param userId 用户ID
     * @param days 冻结天数
     * @param userType 用户类型
     * @return
     */
    boolean frozenUserForDays(int userId, int days, String userType);
    
    /**
     * 
     * TODO	激活用户.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月18日 下午2:47:57
     * @param userId
     * @param userType
     * @return
     */
    boolean unlockUser(int userId, String userType);
    
    /**
     * 
     * TODO	获取登录ID.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月19日 上午9:15:30
     * @param userId
     * @return
     */
    int getLoginId(int userId);
    
    /**
     * 
     * TODO	获取用户尺寸详情.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月19日 上午9:58:32
     * @param id
     * @return
     */
    UserSize getUserSizeDetail(int id);
    
    /**
     * 
     * TODO	修改用户尺寸信息.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月19日 上午10:22:27
     * @param usersize
     * @return
     */
    boolean updateUserSize(UserSize usersize);
    
    /**
     * 
     * TODO	查询用户细分等级列表.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     *   date: 2015年8月19日 下午2:14:22
     * @param userId
     * @return
     */
    List<LVBean> getUserLvList(int userId);
    
    /**
     * 
     * TODO	查询细分等级详情.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月19日 下午2:33:43
     * @param id
     * @return
     */
    LVBean getLvDetail(int id);
    
    /**
     * 
     * TODO	修改用户等级信息.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月19日 上午10:22:27
     * @param usersize
     * @return
     */
    boolean updateLv(LVBean lvBean);
    
    /**
     * 
     * TODO	插入新的等级数据并返回主键.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月24日 下午5:10:28
     * @param lvBean
     * @return
     */
    int addLv(LVBean lvBean);
    
    /**
     * 
     * TODO	查询主键.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月24日 下午5:20:10
     * @param userId
     * @param madeTypeId
     * @return
     */
    int getPK(String userId, String madeTypeId);
    
    /**
     * 
     * TODO	根据经验获取等级.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年9月28日 下午2:03:08
     * @param exp
     * @return
     */
    int getLvByExp(int exp);
    
    
}
