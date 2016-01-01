package com.puyun.myshop.dao;

import java.util.List;
import java.util.Map;

import com.puyun.myshop.entity.AdminUser;
import com.puyun.myshop.entity.Apk;
import com.puyun.myshop.entity.MadeTypeLvBean;

/**
 * 系统配置相关的方法 
 * 对应后台的“t_system_config”表<br>
 * 注意：表中的key不允许重复<br>
 * value可以为空<br>
 * @author  姓名
 * @version  [版本号, 2014-9-29]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface SystemConfigDao
{
    /**
     * 添加一项系统配置
     * @param key
     * @param value
     * @return
     * @see [类、类#方法、类#成员]
     */
    int add(String key, String value);
    
    /**
     * 删除key指定的记录 
     * @param key 表中的KEY
     * @return 受影响行数
     * @see [类、类#方法、类#成员]
     */
    int delete(String key);
    
    /**
     * 根据key修改value值
     * @param key 表中的KEY
     * @param value key对应的value值
     * @return 受影响行数
     * @see [类、类#方法、类#成员]
     */
    int update(String key, String value);
    
    /**
     * 根据key获取value值
     * @param key 表中的KEY
     * @return value值
     * @see [类、类#方法、类#成员]
     */
    String get(String key);
    
    /**
     * 添加一项系统配置
     * @param key
     * @param value
     * @return
     * @see [类、类#方法、类#成员]
     */
    int addKV(String key, String value);
    
    /**
     * 删除key指定的记录 
     * @param key 表中的KEY
     * @return 受影响行数
     * @see [类、类#方法、类#成员]
     */
    int deleteKV(String key);
    
    /**
     * 根据key修改value值
     * @param key 表中的KEY
     * @param value key对应的value值
     * @return 受影响行数
     * @see [类、类#方法、类#成员]
     */
    int updateKV(String key, String value);
    
    /**
     * 根据key获取value值
     * @param key 表中的KEY
     * @return value值
     * @see [类、类#方法、类#成员]
     */
    String getKV(String key);
    
    /**
     * 获取最新版本APK信息
     * @return APK对象
     * author: ldz
     * @see [类、类#方法、类#成员]
     */
    Apk getApk();
    
    /**
     * 
     * TODO	系统管理员登录.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年7月7日 上午9:47:37
     * @param username
     * @param password
     * @return
     */
    AdminUser doLogin(String username, String password);
    
    /**
     * 
     * TODO	修改系统参数.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月17日 下午2:25:35
     * @param platformFee 平台费用
     * @param buyoutCount 设计图买断数量
     * @param tradeAmount 成衣秀交易额
     * @param discussCount 累计评论数量
     * @return
     */
    boolean updateSystemParameter(String platformFee, String buyoutCount, String tradeAmount, String discussCount);
    
    /**
     * 
     * TODO	查询系统参数.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月17日 下午3:38:45
     * @return
     */
    Map<String, Object> getSystemParameter();
    
    /**
     * 
     * TODO	修改制作类型等级.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月19日 下午5:37:26
     * @param id
     * @return
     */
    boolean modifyMadeTypeLv(int id);
    
    /**
     * 
     * TODO	查询制作类型等级列表.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月20日 上午9:43:31
     * @param start
     * @param num
     * @return
     */
    List<MadeTypeLvBean> getMadeTypeLvBeanList(int start, int num);
    
    /**
     * 
     * TODO	查询列表个数.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月20日 下午1:27:11
     * @return
     */
    int getListCount();
    
    /**
     * 
     * TODO	根据ID查询制作类型等级详情.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月20日 上午10:49:08
     * @param id
     * @return
     */
    MadeTypeLvBean getMadeTypeLvDetail(int id);
    
    /**
     * 
     * TODO	更新制作类型等级.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月20日 上午11:16:31
     * @param lv
     * @return
     */
    boolean updateMadeTypeLv(MadeTypeLvBean lv);
    
    /**
     * 
     * TODO	查询旧密码.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月25日 上午10:12:28
     * @param account
     * @return
     */
    String getOldPwd(String account);
    
    /**
     * 
     * TODO	修改密码.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月25日 上午10:18:12
     * @param account
     * @param newPwd
     * @return
     */
    boolean updatePwd(String account, String newPwd);
    
    /**
     * 
     * TODO	查询管理员列表.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月25日 下午5:03:35
     * @return
     */
    List<AdminUser> getManagerList();
    
    /**
     * 
     * TODO	重置密码.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月25日 下午5:06:11
     * @param loginName
     * @return
     */
    boolean resetPwd(String loginName);
    
    /**
     * 
     * TODO	修改管理员信息.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月25日 下午5:06:26
     * @param user
     * @return
     */
    boolean modifyManager(AdminUser user);
    
    /**
     * 
     * TODO	新增管理员.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月25日 下午5:05:53
     * @param user
     */
    void addManager(AdminUser user);
    
    /**
     * 
     * TODO	删除管理员.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年9月23日 下午1:43:07
     * @param id
     * @return
     */
    boolean deleteManager(int id);
    
    /**
     * 
     * TODO	上传APK包.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年8月25日 下午5:58:36
     * @param apk
     */
    boolean uploadApk(Apk apk);
    
    /**
     * 
     * TODO	查询管理员是否已经存在.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年9月23日 上午10:08:41
     * @param loginName
     * @return
     */
    boolean isManagerExist(String loginName);
    
    /**
     * 
     * TODO	查询所有商户的总经验.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年9月29日 上午9:59:12
     * @return
     */
    List<Map<String,Object>> getSalerIdList();
}
