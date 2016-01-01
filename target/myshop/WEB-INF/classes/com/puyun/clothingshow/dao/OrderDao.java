package com.puyun.clothingshow.dao;

import java.util.List;
import java.util.Map;

import com.puyun.clothingshow.entity.OrderBean;

/**
 * 订单管理相关数据访问接口
 * 
 * @author  ldz
 * 创建时间:  2015-2-10
 */
public interface OrderDao
{
    /**
     * 获取订单列表 
     * @param keyword 查询关键字
     * @param start 跳过多少条
     * @param num 取几条
     * @param status 订单状态
     * @return
     * author: ldz
     * @see [类、类#方法、类#成员]
     */
    List<OrderBean> getList(String keyword, int start, int num, int status, int loanStatus);
    
    /**
     * 获取订单个数
     * @param keyword
     * @param status
     * @return
     * author: ldz
     * @see [类、类#方法、类#成员]
     */
    int getListCount(String keyword, int status);
    
    /**
     * 
     * TODO	查询不同状态订单总数.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月8日 下午5:46:57
     * @return
     */
    Map<String, Object> getOrderCount();
    
    /**
     * 
     * TODO	根据订单ID查询订单详情.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     *   date: 2015年7月13日 下午3:01:38
     * @param orderNo
     * @return
     */
    OrderBean getOrderDetail(int id);
    
    /**
     * 
     * TODO  修改订单状态.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     *   date: 2015年7月14日 上午10:41:22
     * @param order
     * @return
     */
    boolean updateOrderStatus(int id, int status);
    
    /**
     * 
     * TODO	修改订单放款状态.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月17日 下午3:03:52
     * @param id
     * @param status
     * @return
     */
    boolean updateLoanStatus(int id, int status);
    
}
