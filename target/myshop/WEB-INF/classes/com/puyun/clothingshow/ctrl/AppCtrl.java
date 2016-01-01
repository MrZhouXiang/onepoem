package com.puyun.clothingshow.ctrl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bcloud.msg.http.HttpSender;
import com.puyun.clothingshow.alipay.util.AlipayNotify;
import com.puyun.clothingshow.base.Constants;
import com.puyun.clothingshow.base.util.Utils;
import com.puyun.clothingshow.dao.AppDao;
import com.puyun.clothingshow.entity.Account;
import com.puyun.clothingshow.entity.Address;
import com.puyun.clothingshow.entity.Ads;
import com.puyun.clothingshow.entity.Apk;
import com.puyun.clothingshow.entity.Buyer;
import com.puyun.clothingshow.entity.BuyerForOrder;
import com.puyun.clothingshow.entity.ColorForApp;
import com.puyun.clothingshow.entity.Comment;
import com.puyun.clothingshow.entity.Express;
import com.puyun.clothingshow.entity.FileMeta;
import com.puyun.clothingshow.entity.GoodsForApp;
import com.puyun.clothingshow.entity.GoodsPhoto;
import com.puyun.clothingshow.entity.GoodsType;
import com.puyun.clothingshow.entity.HistoryShowForApp;
import com.puyun.clothingshow.entity.LVBean;
import com.puyun.clothingshow.entity.MouldForApp;
import com.puyun.clothingshow.entity.OrderForApp;
import com.puyun.clothingshow.entity.PhotoBean;
import com.puyun.clothingshow.entity.Result;
import com.puyun.clothingshow.entity.Saler;
import com.puyun.clothingshow.entity.User;
import com.puyun.clothingshow.entity.UserSize;
import com.puyun.clothingshow.entity.UserType;

/**
 * 处理来自App端的请求
 */
@Controller
public class AppCtrl
{
    private static final Logger logger = Logger.getLogger(AppCtrl.class);
    
    public static Map<String, User> tokenMap = new HashMap<String, User>();
    
    // 下单时用于判断下单个数
    public static Map<String, List<OrderForApp>> orderMap = new HashMap<String, List<OrderForApp>>();
    
    @Autowired
    private AppDao appDao;
    
    @Autowired
    private PushCtrl pushCtrl;
    
    public AppCtrl()
    {
        super();
        logger.debug("创建对象AppCtrl");
    }
    
    /**
     * 获取已登录用户的会话token,没取到时返回null
     * 
     * @param map
     * @param id
     * @return author: ldz
     * @see [类、类#方法、类#成员]
     */
    private String findKey(Map<String, User> map, String id)
    {
        String key = null;
        for (Map.Entry<String, User> e : map.entrySet())
        {
            if (id.equals(e.getValue().getId()))
            {
                return e.getKey();
            }
        }
        
        return key;
    }
    
    /**
     * 用户登录接口
     * 
     * @param account 账号
     * @param pwd 密码（MD5加密）
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/user/login")
    @ResponseBody
    public Result login(String tel, String password)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        try
        {
            // 判断用户是否封号
            // 登陆
            User user = appDao.login(tel, password);
            if (user != null)// 登陆成功
            {
                Account account = new Account();
                String buyerId = appDao.getBuyerId(user.getId());
                String salerId = appDao.getSalerId(user.getId());
                account.setId(user.getId());
                account.setBuyer_id(buyerId);
                account.setSaler_id(salerId);
                account.setTel(tel);
                // 防止内存溢出，用户重复登陆时，先清除上次的会话
                String oldKey = findKey(tokenMap, user.getId());
                if (oldKey != null)
                {
                    tokenMap.remove(oldKey);
                }
                
                String token = Utils.token();
                // 保存登陆会话
                tokenMap.put(token, user);
                result.put("user", account);
                result.put("pushV", Constants.AppSysConstans.PushV);
                result.put("token", token);
                
                code = "2000";
                logger.debug("登陆成功, 已登录用户数：" + tokenMap.size());
            }
            else
            {
                logger.debug("登陆失败, 账号或密码错误");
                result.put("reason", "账号或密码错误");
                code = "4001";
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        
        return new Result(code, result);
    }
    
    /**
     * 
     * TODO	修改密码.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年9月21日 上午10:55:38
     * @param moblie
     * @param newpwd
     * @return
     */
    @RequestMapping(value = "user/resetPWD")
    @ResponseBody
    public Result modifyPWD(String moblie, String newpwd)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        try
        {
            // 根据手机号修改登录密码
            if (appDao.getUser(moblie) != null)
            {
                if (appDao.updatePwd(moblie, newpwd) > 0)
                {
                    code = "2000";
                    result.put("reason", "修改密码成功");
                    logger.debug("修改密码成功");
                }
                else
                {
                    code = "4102";
                    result.put("reason", "修改密码失败");
                    logger.debug("修改密码失败");
                }
            }
            else
            {
                code = "4102";
                result.put("reason", "此帐号不存在, 请注册");
                logger.debug("此帐号不存在, 请注册");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
    }
    
    /**
     * 增加买家尺寸
     * 
     * @param token
     * @param size
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/user/addMySize")
    @ResponseBody
    public Result addMySize(String token, UserSize size)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        try
        {
            // 是否已登录
            if (tokenMap.containsKey(token))
            {
                // 增加尺寸
                if (appDao.addMySize(size) > 0)
                {
                    
                    code = "2000";
                    result.put("reason", "尺码添加成功");
                    logger.debug("尺码添加成功");
                }
                else
                {
                    code = "4012";
                    result.put("reason", "尺码添加失败");
                    logger.debug("尺码添加失败");
                    
                }
                
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
    }
    
    /**
     * 获取用户尺寸
     * 
     * @param token
     * @param size
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/user/getMySizeList")
    @ResponseBody
    public Result getMySizeList(String token, String loginId)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        List<UserSize> list = null;
        try
        {
            // 是否已登录
            if (tokenMap.containsKey(token))
            {
                list = appDao.getMySizeList(loginId);
                code = "2000";
                result.put("list", list);
                logger.debug("查询成功");
                
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
    }
    
    /**
     * 注册买家信息 <br>
     * 首次注册时候使用
     * 
     * @param buyer
     * @return
     */
    @RequestMapping(value = "/user/registerBuyerInfo")
    @ResponseBody
    public Result registerBuyerInfo(String token, Buyer buyer)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        try
        {
            // 是否已登录
            if (tokenMap.containsKey(token))
            {
                // 判断买家是否存在
                if (appDao.isBuyerExit(buyer.getLoginId()))
                {
                    
                    // 买家存在
                    code = "2001";
                    result.put("reason", "买家已存在");
                    logger.debug("买家已存在");
                }
                else
                {
                    int id = appDao.addBuyer(buyer);
                    // 买家不存在，插入买家
                    if (id != -1)
                    {
                        // 买家存在
                        code = "2000";
                        result.put("reason", "增加买家成功");
                        result.put("id", id);
                        logger.debug("买家不存在, 增加买家成功");
                    }
                    else
                    {
                        code = "4500";
                        result.put("reason", "增加买家失败");
                        logger.debug("增加买家失败");
                    }
                    
                }
                
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
    }
    
    /**
     * 注册卖家信息 <br>
     * 首次注册时候使用
     * 
     * @param buyer
     * @return
     */
    @RequestMapping(value = "/user/registerSalerInfo")
    @ResponseBody
    public Result registerSalerInfo(String token, Saler saler)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        try
        {
            // 是否已登录
            if (tokenMap.containsKey(token))
            {
                // 判断卖家是否存在
                if (appDao.isSalerExit(saler.getLoginId()))
                {
                    
                    // 卖家存在
                    code = "2001";
                    result.put("reason", "卖家已存在");
                    logger.debug("卖家已存在");
                }
                else
                {
                    int id = appDao.addSaler(saler);
                    // 买家不存在, 插入卖家
                    if (id != -1)
                    {
                        result.put("id", id);
                        // 卖家存在
                        code = "2000";
                        result.put("reason", "增加卖家成功");
                        logger.debug("卖家不存在, 增加卖家成功");
                    }
                    else
                    {
                        code = "4500";
                        result.put("reason", "增加卖家失败");
                        logger.debug("增加卖家失败");
                    }
                    
                }
                
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
    }
    
    /**
     * 用户注册接口
     * 
     * @param user 用户对象
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/user/register")
    @ResponseBody
    public Result register(User user)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        try
        {
            if (appDao.getUser(user.getTel()) == null)
            {
                user.setLoginName(user.getTel());
                int userId = appDao.register(user);
                user.setId(String.valueOf(userId));
                result.put("user", user);
                result.put("reason", "注册成功");
                code = "2000";
                logger.debug("注册成功");
            }
            else
            {
                code = "4002";
                result.put("reason", "此帐号已存在, 请尝试使用其他号码注册");
                logger.debug("此帐号已存在, 请尝试使用其他号码注册");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        
        return new Result(code, result);
    }
    
    /**
     * 用户退出
     * 
     * @param token
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/user/logout")
    @ResponseBody
    public Result logout(String token)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        try
        {
            // 是否已登录
            if (tokenMap.containsKey(token))
            {
                // 退出登录
                tokenMap.remove(token);
                code = "2000";
                result.put("reason", "退出成功");
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        
        return new Result(code, result);
    }
    
    /**
     * 
     * TODO 查询用户信息.
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年6月25日 下午4:46:16
     * 
     * @param token
     * @param types
     * @param id
     * @return
     */
    @RequestMapping(value = "/user/getLoginUserInfo")
    @ResponseBody
    public Result getLoginUserInfo(String token, String types, String id)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        try
        {
            // 是否已登录
            if (tokenMap.containsKey(token))
            {
                // 如果是买家
                if (UserType.BUYER.getValue().equals(types))
                {
                    
                    Buyer buyer = appDao.getBuyerInfo(id);
                    if (buyer != null)
                    {
                        // 判断是否封号
                        if (appDao.isFenghao(id, types))
                        {
                            code = "4010";
                            result.put("reason", "您已被冻结");
                            logger.debug("您已被冻结");
                            return new Result(code, result);
                        }
                        appDao.updateLoginTime(id, types);
                        code = "2000";
                        result.put("user", buyer);
                        logger.debug("查询成功");
                    }
                    else
                    {
                        code = "4004";
                        result.put("reason", "查找不到此人信息");
                        logger.debug("查找不到此人");
                    }
                    
                }
                else
                { // 如果是卖家
                  // 判断是否封号
                    Saler saler = appDao.getSalerInfo(id);
                    if (saler != null)
                    {
                        // 判断是否封号
                        if (appDao.isFenghao(id, types))
                        {
                            code = "4010";
                            result.put("reason", "您已被冻结");
                            logger.debug("您已被冻结");
                            return new Result(code, result);
                        }
                        code = "2000";
                        result.put("user", saler);
                        logger.debug("查询成功");
                    }
                    else
                    {
                        code = "4004";
                        result.put("reason", "查找不到此人信息");
                        logger.debug("查找不到此人");
                    }
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        
        return new Result(code, result);
    }
    
    /**
     * 
     * TODO 获取商品类型.
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年6月25日 下午4:46:16
     * 
     * @param token
     * @param types
     * @param id
     * @return
     */
    @RequestMapping(value = "/goods/getGoodsTypes")
    @ResponseBody
    public Result getAllGoodsTypes()
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        List<GoodsType> list = null;
        try
        {
            list = appDao.getAllGoodsTypes();
            code = "2000";
            result.put("list", list);
            logger.debug("查询成功");
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        
        return new Result(code, result);
    }
    
    /**
     * 
     * TODO 发布普通评论.
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年7月1日 下午3:33:23
     * 
     * @param token 登录标记
     * @param commenter_id 评论人ID
     * @param to_commenter_id 评论对象ID
     * @param types 类型, 商品：shangpin; 人：ren
     * @param pid 父id
     * @param content 内容
     * @param pinglunrenLx 评论人类型, kehu,shanghu
     * @return
     */
    @RequestMapping(value = "/comment/postComment")
    @ResponseBody
    public Result postComment(String token, String commenter_id, String to_commenter_id, String types, String content,
        String pinglunrenLx)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        try
        {
            if (tokenMap.containsKey(token))
            {
                if (appDao.postComment(commenter_id, to_commenter_id, types, content, pinglunrenLx))
                {
                    code = "2000";
                    result.put("reason", "评论成功");
                    logger.debug("评论成功");
                    if ("shangpin".equals(types))
                    {
                        try
                        {
                            pushCtrl.pushToAllForDiscussCount(Integer.parseInt(to_commenter_id));
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        
                    }
                }
                else
                {
                    code = "4005";
                    result.put("reason", "评论失败");
                    logger.debug("评论失败");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
            
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        
        return new Result(code, result);
    }
    
    /**
     * 
     * TODO 发表商品评价.
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年7月1日 下午4:18:01
     * 
     * @param token 登录标记
     * @param order_id 订单ID
     * @param shangpin_id 商品ID
     * @param pingfen 评分
     * @param content 内容
     * @param user_id 用户ID
     * @param utypes 用户类型
     * @return
     */
    @RequestMapping(value = "/order/starRating")
    @ResponseBody
    public Result postGoodsComment(String token, String order_id, String shangpin_id, int pingfen, String content,
        String user_id, String utypes)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        try
        {
            if (tokenMap.containsKey(token))
            {
                if (appDao.postGoodsComment(order_id, shangpin_id, pingfen, content, user_id, utypes))
                {
                    
                    // 修改相关商户等级,评分为1，2，3，4，5，大于3分加经验，小于3分减经验
                    if (appDao.changeJingyanByPingfen(order_id, pingfen))
                    {
                        code = "2000";
                        result.put("reason", "评价成功");
                        logger.debug("评价成功, 经验修改成功");
                    }
                    else
                    {
                        code = "2000";
                        result.put("reason", "评价成功");
                        logger.debug("评价成功, 经验修改失败");
                    }
                }
                else
                {
                    code = "4006";
                    result.put("reason", "评价失败");
                    logger.debug("评价失败");
                    
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        
        return new Result(code, result);
    }
    
    /**
     * 
     * TODO 获取商品评论列表.
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年7月1日 下午6:19:45
     * 
     * @param token 登录标记
     * @param id 商品ID
     * @param page_num 页码
     * @param page_size 页面容量
     * @return
     */
    @RequestMapping(value = "/goods/getCommentList")
    @ResponseBody
    public Result getCommentList(String token, String id, @RequestParam(defaultValue = "1") int page_num,
        @RequestParam(defaultValue = "15") int page_size, String types)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        List<Comment> comment_list = null;
        try
        {
            if (tokenMap.containsKey(token))
            {
                comment_list = appDao.getCommentList(id, page_num, page_size, types);
                if (comment_list.size() > 0)
                {
                    code = "2000";
                    result.put("comment_list", comment_list);
                    result.put("reason", "查询成功");
                    logger.debug("查询成功");
                }
                else
                {
                    code = "4007";
                    result.put("reason", "暂无数据");
                    logger.debug("暂无数据");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
            
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        
        return new Result(code, result);
    }
    
    /**
     * 
     * TODO 查询商品剩余数量.
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年7月1日 下午7:34:41
     * 
     * @param token 登录标记
     * @param id 商品ID
     * @return
     */
    @RequestMapping(value = "/goods/getGoodsRemainNum")
    @ResponseBody
    public Result getGoodsRemainNum(String token, String id)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        int remain_num = 0;
        try
        {
            if (tokenMap.containsKey(token))
            {
                remain_num = appDao.getGoodsRemainNum(id);
                code = "2000";
                result.put("remain_num", remain_num + "");
                result.put("reason", "查询成功");
                logger.debug("查询成功");
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        
        return new Result(code, result);
    }
    
    /**
     * 
     * TODO 查询商品详情.
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年7月1日 下午8:23:33
     * 
     * @param token 登陆标记
     * @param id 商品ID
     * @return
     */
    @RequestMapping(value = "/goods/getGoodsDetail")
    @ResponseBody
    public Result getGoodsDetail(String token, String id)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        
        try
        {
            if (tokenMap.containsKey(token))
            {
                GoodsForApp goods = appDao.getGoodsDetail(id);
                if (goods != null)
                {
                    code = "2000";
                    String url_list[] = appDao.getGoodsPhotoList(id);
                    goods.setUrl_list(url_list);
                    List<GoodsPhoto> url_list2 = appDao.getGoodsPhotoList2(id);
                    goods.setUrl_list2(url_list2);
                    result.put("reason", "查询成功");
                    result.put("goods", goods);
                    logger.debug("查询成功");
                }
                else
                {
                    code = "4008";
                    result.put("reason", "找不到该商品");
                    logger.debug("找不到该商品");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        
        return new Result(code, result);
    }
    
    /**
     * 
     * TODO 获取商品列表.
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年7月2日 下午2:57:55
     * 
     * @param token 登录标记
     * @param types 类型 成衣秀：chengyixiu; 买家秀：maijiaxiu; 设计秀：shejixiu; 加工单：jiagongdan; 定制单：dingzhidan
     * @param page_num 页码, 默认为1
     * @param page_size 页码容量, 默认为15
     * @param orderby 排序, 默认倒序, 倒序 时间：sj 价格：jg
     * @param hascomment 是否有评论, 是：1; 否：0
     * @param comment_size 评论数量, 默认为4
     * @param yonghuid 用户ID, 当取设计师历史作品时types类型请用shejixiu
     * @param zhizuolx 默认-1：查询全部
     * @return
     */
    @RequestMapping(value = "/goods/getGoodsList")
    @ResponseBody
    public Result getGoodsList(String token, String types, @RequestParam(defaultValue = "1") int page_num,
        @RequestParam(defaultValue = "15") int page_size, String orderby,
        @RequestParam(defaultValue = "0") int hascomment, @RequestParam(defaultValue = "4") int comment_size,
        @RequestParam(required = false, defaultValue = "-1") int yonghuid,
        @RequestParam(required = false, defaultValue = "-1") int zhizuolx)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        List<GoodsForApp> goodsList = new ArrayList<GoodsForApp>();
        
        try
        {
            if (tokenMap.containsKey(token))
            {
                if ("qiangdan".equals(types))
                {
                    
                    // 获取抢单商品包括：设计秀，定制单，加工单
                    goodsList =
                        appDao.getGoodsList3(types,
                            page_num,
                            page_size,
                            orderby,
                            hascomment,
                            comment_size,
                            yonghuid,
                            zhizuolx);
                }
                else
                {
                    goodsList =
                        appDao.getGoodsList(types,
                            page_num,
                            page_size,
                            orderby,
                            hascomment,
                            comment_size,
                            yonghuid,
                            zhizuolx);
                }
                if (goodsList.size() > 0)
                {
                    code = "2000";
                    result.put("list", goodsList);
                    result.put("reason", "查询成功");
                    logger.debug("查询成功");
                }
                else
                {
                    code = "4009";
                    result.put("reason", "暂无数据");
                    logger.debug("暂无数据");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        
        return new Result(code, result);
    }
    
    /**
     * 获取历史作品
     * 
     * @param token
     * @param types
     * @param page_num
     * @param page_size
     * @param orderby
     * @param hascomment
     * @param comment_size
     * @param yonghuid
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/user/getDesignerShowList")
    @ResponseBody
    public Result getDesignerShowList(String token, String types, @RequestParam(defaultValue = "1") int page_num,
        @RequestParam(defaultValue = "15") int page_size, String orderby,
        @RequestParam(defaultValue = "-1") int hascomment, @RequestParam(defaultValue = "4") int comment_size, int id)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        List<HistoryShowForApp> goodsList = new ArrayList<HistoryShowForApp>();
        goodsList = appDao.getHistoryShowForApp(types, page_num, page_size, orderby, hascomment, comment_size, id);
        try
        {
            if (tokenMap.containsKey(token))
            {
                if (goodsList.size() > 0)
                {
                    code = "2000";
                    // 处理分组
                    
                    result.put("list", goodsList);
                    result.put("reason", "查询成功");
                    logger.debug("查询成功");
                }
                else
                {
                    code = "4009";
                    result.put("reason", "暂无数据");
                    logger.debug("暂无数据");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        
        return new Result(code, result);
    }
    
    @RequestMapping(value = "/user/getDesignerShowList2")
    @ResponseBody
    public Result getDesignerShowList2(String token, String types, @RequestParam(defaultValue = "1") int page_num,
        @RequestParam(defaultValue = "15") int page_size, String orderby,
        @RequestParam(defaultValue = "-1") int hascomment, @RequestParam(defaultValue = "4") int comment_size, int id)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        List<HistoryShowForApp> goodsList = new ArrayList<HistoryShowForApp>();
        goodsList = appDao.getHistoryShowForApp2(types, page_num, page_size, orderby, hascomment, comment_size, id);
        try
        {
            if (tokenMap.containsKey(token))
            {
                if (goodsList.size() > 0)
                {
                    code = "2000";
                    // 处理分组
                    List<Map<String, ?>> l = dealWithGoodListByTime(goodsList);
                    result.put("grouplist", l);
                    result.put("reason", "查询成功");
                    logger.debug("查询成功");
                }
                else
                {
                    code = "4009";
                    result.put("reason", "暂无数据");
                    logger.debug("暂无数据");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        
        return new Result(code, result);
    }
    
    // 按时间分组
    private static List<Map<String, ?>> dealWithGoodListByTime(List<HistoryShowForApp> goodsList)
    {
        Map<String, List<HistoryShowForApp>> mapList = new LinkedHashMap<String, List<HistoryShowForApp>>();
        for (int i = 0; i < goodsList.size(); i++)
        {
            HistoryShowForApp good = goodsList.get(i);
            String time = "";
            int days = good.getDays();
            int week = good.getWeeks();
            int month = good.getMonths();
            // 距离今天大于7,key为day
            if (days < 7)
            {
                if (days == 0)
                {
                    time = "今天";
                }
                else
                {
                    time = days + "天前";
                }
                
            }
            else if (week < 4)
            {
                // time = week+"";
                if (week == 0)
                {
                    time = "本周";
                }
                else
                {
                    time = week + "周前";
                }
                
            }
            else if (month < 12)
            {
                // time = week+"";
                if (month == 0)
                {
                    time = "本月";
                }
                else
                {
                    time = month + "月前";
                }
                
            }
            else
            {
                time = "1年前";
            }
            
            List<HistoryShowForApp> key = mapList.get(time);
            List<HistoryShowForApp> l_t = null;
            // 如果有数据
            if (key == null)
            {
                l_t = new ArrayList<HistoryShowForApp>();
            }
            else
            {
                // 没有数据
                l_t = (ArrayList<HistoryShowForApp>)key;
            }
            l_t.add(good);
            mapList.put(time, l_t);
        }
        List<Map<String, ?>> resultList = new ArrayList<Map<String, ?>>();
        for (Map.Entry<String, List<HistoryShowForApp>> entry : mapList.entrySet())
        {
            System.out.println(entry.getKey() + "--->" + entry.getValue());
            Map<String, Object> m1 = new HashMap<String, Object>();
            
            m1.put("list", entry.getValue());
            m1.put("time", entry.getKey());
            resultList.add(m1);
        }
        return resultList;
        
    }
    
    public static void main(String[] args)
    {
        @SuppressWarnings("resource")
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-*.xml");
        AppDao dao = (AppDao)ctx.getBean("appDao");
        List<HistoryShowForApp> goodsList = new ArrayList<HistoryShowForApp>();
        goodsList = dao.getHistoryShowForApp("1", 1, 100, "sj", 0, 0, 1);
        for (HistoryShowForApp goodsForApp : goodsList)
        {
            System.out.println("days:" + goodsForApp.getDays() + "weeks:" + goodsForApp.getWeeks() + "months:"
                + goodsForApp.getMonths() + goodsForApp.getRukuSj());
        }
        dealWithGoodListByTime(goodsList);
        
    }
    
    /**
     * 
     * TODO 提交订单.
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年7月2日 下午5:33:28
     * 
     * @param token
     * @param order
     * @return
     */
    @RequestMapping(value = "/order/submit")
    @ResponseBody
    public Result submitOrder(String token, OrderForApp order)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        try
        {
            if (tokenMap.containsKey(token))
            {
                // 获取商品
                GoodsForApp goods = appDao.getGoodsDetail(order.getShangpinID());
                
                List<OrderForApp> list = orderMap.get(order.getShangpinID());
                if (order.getIsQiangdan() == 0)
                {// 非抢单需要判断当前有效订单数量
                    if (list != null && list.size() >= Integer.parseInt(goods.getShuliang()))
                    {
                        // 有效订单数量大于商品数量
                        code = "4010";
                        if("chengyixiu".equals(order.getShangpinlx())){
                        	result.put("reason", "该商品已卖光");
                        }
                        if("shejixiu".equals(order.getShangpinlx())){
                        	result.put("reason", "该设计图已被买断");
                        }
                        logger.debug("该设计图已被买断");
                        return new Result(code, result);
                    }
                }
                if (list != null)
                {
                    order.setTime(System.currentTimeMillis());
                    // 加入有效订单
                    list.add(order);
                }
                else
                {
                    order.setTime(System.currentTimeMillis());
                    list = new ArrayList<OrderForApp>();
                    // 加入有效订单
                    list.add(order);
                }
                
                orderMap.put(order.getShangpinID(), list);
                // 设置默认快递方式为0，邮递
                if (null != order && null == order.getKuaidiFs())
                {
                    order.setKuaidiFs("0");
                }
                // 抢单
                if (order.getIsQiangdan() == 1)
                {
                    // 判断商户等级
                    int lv = appDao.getLVByIDandLX(order.getMmaijiaID(), order.getMmaijiaLx());
                    System.out.println("lv:" + lv);
                    if (lv <= 0)
                    {
                        code = "4103";
                        result.put("reason", "您的等级为0, 不能抢单");
                        logger.debug("您的等级为0, 不能抢单");
                        return new Result(code, result);
                    }
                    // 防止抢单人员过多
                    if (appDao.getHasQiangDanNumByShangpin(order.getShangpinID()) >= Constants.AppSysConstans.HAS_QIANGDAN_NUM)
                    {
                        code = "4104";
                        result.put("reason", "太慢了! 已经超过" + Constants.AppSysConstans.HAS_QIANGDAN_NUM + "人抢单了");
                        logger.debug("太慢了! 已经超过" + Constants.AppSysConstans.HAS_QIANGDAN_NUM + "人抢单了");
                        return new Result(code, result);
                        
                    }
                    // 防止重复抢单
                    if (appDao.hasQiangDan(order.getShangpinID(), order.getMmaijiaID(), order.getMmaijiaLx()))
                    {
                        code = "4102";
                        result.put("reason", "下单失败, 请不要重复下单");
                        logger.debug("下单失败, 请不要重复下单");
                        return new Result(code, result);
                        
                    }
                    // 根据抢单用户ID判断待发货个数,>Constants.AppSysConstans.UNSEND_ORDER_NUM不能抢单
                    if (appDao.getNotSendOrderNum(order.getShangpinID(), order.getMmaijiaID(), order.getMmaijiaLx()) >= Constants.AppSysConstans.UNSEND_ORDER_NUM)
                    {
                        code = "4101";
                        result.put("reason", "下单失败，您的未完成订单已经超过" + Constants.AppSysConstans.UNSEND_ORDER_NUM + "个了");
                        logger.debug("下单失败，您的未完成订单已经超过" + Constants.AppSysConstans.UNSEND_ORDER_NUM + "个了");
                        return new Result(code, result);
                    }
                    
                }
                
                if (order.getIsQiangdan() == 1)
                {// 如果是抢单
                 // 获取商品总价
                    getZongjia(order);
                    
                }
                else
                {// 非抢单
                	if("chengyixiu".equals(order.getShangpinlx()) || "shejixiu".equals(order.getShangpinlx())){
	                    order.setZongjia(goods.getDanjia() + "");
	                    order.setYouhuijia(goods.getDanjia() + "");
	                    order.setYuanjia(goods.getDanjia() + "");
	                    order.setDanjia(goods.getDanjia() + "");
                	}else if("maijiaxiu".equals(order.getShangpinlx()) || "jiagongdan".equals(order.getShangpinlx()) || "dingzhidan".equals(order.getShangpinlx())){
	                    order.setZongjia(goods.getJiage() + "");
	                    order.setYouhuijia(goods.getJiage() + "");
	                    order.setYuanjia(goods.getJiage() + "");
	                    order.setDanjia(goods.getJiage() + "");
                	}
                }
                
                int id = appDao.submitOrder(order);
                if (id > 0)
                {
                    code = "2000";
                    result.put("id", id);
                    result.put("reason", "下单成功");
                    logger.debug("下单成功");
                    order.setId(id + "");
                    if (order.getIsQiangdan() == 1){
                    	 //客户端发布定制, 有卖家接单, 发送推送给客户
                        if(UserType.BUYER.getValue().equals(order.getMaijiaLx()) && UserType.SALER.getValue().equals(order.getMmaijiaLx()) && "dingzhidan".equals(order.getShangpinlx())){
                        	pushCtrl.salerOffer(order);
                        }
                        
                        //商户端发布的加工单被人接单, 发送推送到商户
                        if(UserType.SALER.getValue().equals(order.getMaijiaLx()) && UserType.SALER.getValue().equals(order.getMmaijiaLx()) && "jiagongdan".equals(order.getShangpinlx())){
                        	pushCtrl.worksheetForOrder(order);
                        }
                    }
                }
                else
                {
                    code = "4010";
                    result.put("reason", "商品已抢光");
                    logger.debug("商品已抢光");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        
        return new Result(code, result);
    }
    
    // 计算商品最终总价
    private OrderForApp getZongjia(OrderForApp order)
    {
        // 获取商品
        GoodsForApp goods = appDao.getGoodsDetail(order.getShangpinID());
        // 加工费获取
        int zhizuoleixi = goods.getZhizuoLx();
        String fabuzheId = order.getMmaijiaID();
        float X = goods.getX();
        Saler s = new Saler();
        s.setId(fabuzheId);
        appDao.setAllLV(s);
        //商户细分等级列表
        List<LVBean> list = s.getDengji_list();
        
        //0级参考价格
        int Y = 25;
        if (list != null)
        {
            for (int i = 0; i < list.size(); i++)
            {
                
                LVBean lv = list.get(i);
                if (lv.getZhizuoLxID().equals("" + zhizuoleixi))
                {
                    Y = lv.getY();
                    break;
                }
            }
        }
        
        // 计算加工费用
        float cankaojia = (float)(X * Math.pow(Y, Constants.AppSysConstans.A));
        // 计算优惠幅度
        int youhuifudu = order.getYouhuifudu();
        // 获取基础价格, 以10级为参考价
        float baseJage = goods.getBaseJiage();
        // 计算最终价格
        float zongjia = baseJage + cankaojia * youhuifudu / 100;
        order.setYuanjia(baseJage + cankaojia + "");
        order.setZongjia(zongjia + "");
        order.setYouhuijia(zongjia + "");
        order.setDanjia(goods.getDanjia() + "");
        return order;
    }
    
    /**
     * 
     * TODO 支付.
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年7月2日 下午5:33:28
     * 
     * @param token
     * @param order
     * @return
     */
    @RequestMapping(value = "/order/confirmPay")
    @ResponseBody
    public Result confirmPay(String token, String id, String paycode)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        try
        {
            if (tokenMap.containsKey(token))
            {
                OrderForApp order = appDao.getOrderInfo(id);
                // 买断
                if (null != order && null != order.getShangpinlx() && order.getShangpinlx().equals("shejixiu"))
                {
                    if (appDao.confirmPay(id, paycode, "16") > 0)
                    {
                        // 修改商品买断状态
                        if (appDao.changeToMaiduan(order.getShangpinID()))
                        {
                        	logger.debug("修改商品买断状态成功");
                        }
                        // 支付成功之后需要判断是否是买断设计图，推送消息去客户。
                        if (pushCtrl.pushShejituToBuyer(appDao, id, order))
                        {
                            code = "2000";
                            result.put("reason", "设计图买断成功");
                            logger.debug("支付成功, 已推送到系统消息");
                        }
                        else
                        {
                            code = "2000";
                            result.put("reason", "设计图买断成功");
                            logger.debug("支付成功, 推送失败");
                        }
                        //发布的设计被买断, 推送消息到商户
                        pushCtrl.designForBuyOut(order);
                        //推送热门消息
                        pushCtrl.pushToAllForBuyoutOrderCount(Integer.valueOf(order.getShangpinID()));
                    }
                    else
                    {
                        code = "4011";
                        result.put("reason", "支付失败");
                        logger.debug("支付失败");
                        
                    }
                }
                else if (order.getIsQiangdan() == 1)
                {
                	// 抢单
                    if (appDao.confirmPay(id, paycode, "8") > 0)
                    {
                        // 如果是商户抢单，则修改其他此商品的订单的状态为取消【2】，并且推送消息给其他抢单用户，订单已经被抢
                        // 修改其他此商品的订单的状态为取消【2】
                    	appDao.changeOrderStatusBySpID(order.getShangpinID(), 2, id);
                    	code = "2000";
                        result.put("reason", "支付成功");
                        logger.debug("支付成功");
                        
                        //抢客户发布的定制的反馈
                    	if("chengyixiu".equals(order.getShangpinlx())){
                    		 if (pushCtrl.customOrderResult(order))
                             {
                                 code = "2000";
                                 result.put("reason", "支付成功");
                                 logger.debug("支付成功,推送成功");
                             }
                             else
                             {
                                 code = "2000";
                                 result.put("reason", "支付成功");
                                 logger.debug("支付成功,推送失败");
                             }
                    	}
                    	
                    	//抢商户发布的加工单的反馈
                    	if("jiagongdan".equals(order.getShangpinlx())){
                    		 if (pushCtrl.pushFaileOderMsgToBuyer(appDao, order.getShangpinID()))
                             {
                                 code = "2000";
                                 result.put("reason", "支付成功");
                                 logger.debug("支付成功,推送成功");
                             }
                             else
                             {
                                 code = "2000";
                                 result.put("reason", "支付成功");
                                 logger.debug("支付成功,推送失败");
                             }
                    	}
                    	
                    	pushCtrl.pushToAllForDesignerTradeAmount(Integer.valueOf(order.getShangpinID()));
                    }
                    else
                    {
                        code = "4011";
                        result.put("reason", "支付失败");
                        logger.debug("支付失败");
                    }
                }
                else
                {  
                	// 其他
                    if (appDao.confirmPay(id, paycode, "8") > 0)
                    {
                        code = "2000";
                        result.put("reason", "支付成功");
                        logger.debug("支付成功");
                        pushCtrl.pushToAllForDesignerTradeAmount(Integer.valueOf(order.getShangpinID()));
                        //发布的成衣被订购
                        if(UserType.BUYER.getValue().equals(order.getMaijiaLx()) && UserType.SALER.getValue().equals(order.getMmaijiaLx()) && "chengyixiu".equals(order.getShangpinlx())){
                        	pushCtrl.clothesForOrder(order);
                        }
                    }
                    else
                    {
                        code = "4011";
                        result.put("reason", "支付失败,商品数量增加成功");
                        logger.debug("支付失败");
                    }
                }
                // 无论成功失败都需把有效订单移除
                List<OrderForApp> list = orderMap.get(order.getShangpinID());
                if (list != null)
                {
                    list.remove(order);
                }
                
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        
        return new Result(code, result);
    }
    
    /**
     * 
     * TODO	查询订单列表.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年9月11日 下午2:59:27
     * @param token 登录标志
     * @param types 买家：mj;卖家：mmj
     * @param page_num 页码
     * @param page_size 页码容量
     * @param userid 用户ID
     * @param status 订单状态
     * @param shangpinid 商品ID
     * @param leixing 商户：shanghu;客户：kehu
     * @return
     */
    @RequestMapping(value = "order/getList")
    @ResponseBody
    public Result getOrderList(String token, String types, @RequestParam(defaultValue = "1") int page_num,
        @RequestParam(defaultValue = "15") int page_size, int userid,
        @RequestParam(required = false, defaultValue = "-1") String status,
        @RequestParam(required = false, defaultValue = "-1") int shangpinid, String leixing)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        List<OrderForApp> goodsList = new ArrayList<OrderForApp>();
        try
        {
            if (tokenMap.containsKey(token))
            {
                goodsList = appDao.getOrderList(types, userid, status, page_num, page_size, shangpinid, leixing);
                
                if (goodsList.size() > 0)
                {
                    code = "2000";
                    result.put("list", goodsList);
                    result.put("reason", "查询成功");
                    logger.debug("查询成功");
                }
                else
                {
                    code = "4009";
                    result.put("reason", "暂无数据");
                    logger.debug("暂无数据");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        
        return new Result(code, result);
    }
    
    /**
     * 抢购订单的商家列表
     * 
     * @param token
     * @param id
     * @param page_num
     * @param page_size
     * @param orderby
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "order/getBuyerList")
    @ResponseBody
    public Result getBuyerList(String token, String id,
        @RequestParam(required = false, defaultValue = "1") int page_num,
        @RequestParam(required = false, defaultValue = "10") int page_size,
        @RequestParam(required = false, defaultValue = "sj") String orderby)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        List<BuyerForOrder> goodsList = new ArrayList<BuyerForOrder>();
        
        try
        {
            if (tokenMap.containsKey(token))
            {
                goodsList = appDao.getBuyerList(id, page_num, page_size, orderby);
                if (goodsList.size() > 0)
                {
                    code = "2000";
                    result.put("list", goodsList);
                    result.put("reason", "查询成功");
                    logger.debug("查询成功");
                }
                else
                {
                    code = "4009";
                    result.put("reason", "暂无数据");
                    logger.debug("暂无数据");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        
        return new Result(code, result);
    }
    
    /**
     * 获取订单详情
     * 
     * @param token
     * @param id
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "order/getOrderInfo")
    @ResponseBody
    public Result getOrderInfo(String token, String id)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        OrderForApp order = new OrderForApp();
        
        try
        {
            if (tokenMap.containsKey(token))
            {
                order = appDao.getOrderInfo(id);
                if (order != null)
                {
                    code = "2000";
                    result.put("result", order);
                    result.put("reason", "查询成功");
                    logger.debug("查询成功");
                }
                else
                {
                    code = "4009";
                    result.put("reason", "暂无数据");
                    logger.debug("暂无数据");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        
        return new Result(code, result);
    }
    
    /**
     * 修改订单状态
     * 
     * @param token
     * @param id 订单id
     * @param status
     * @param KuaidiID 快递id
     * @param Kuaididanhao 快递单号
     * @param Shoukuanma 收款码
     * @param toGoods 是否是取消商品(只用于取消自己发布的订单的时候，用来下架发布的商品，以及取消所有抢此商品的订单)
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "order/modifyStatus")
    @ResponseBody
    public Result modifyStatus(String token, String id, int status,
        @RequestParam(required = false, defaultValue = "-1") int KuaidiID,
        @RequestParam(required = false, defaultValue = "-1") String Kuaididanhao,
        @RequestParam(required = false, defaultValue = "-1") String Shoukuanma,
        @RequestParam(required = false, defaultValue = "-1") int toGoods)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        try
        {
            if (tokenMap.containsKey(token))
            {
                OrderForApp order = appDao.getOrderInfo(id);
                switch (status)
                {
                    case 2: // 如果是取消订单
                        // 把最牛设计师的订单状态设置为1
                        // 抢单不需要判断数量
                    	if("0".equals(order.getZhuangtai()) || "1".equals(order.getZhuangtai())){
                    		if (appDao.modifyOrderStatus(id, status) > 0)
                            {
                                // 修改状态之后，重新查出一个时间最短，报价最高的订单修改其状态为1
                                if (appDao.updateNBBuyerOrder(order))
                                {
                                    code = "2000";
                                    result.put("reason", "修改成功, 其他订单状态修改成功");
                                    logger.debug("修改成功, 其他订单状态修改成功");
                                }
                                else
                                {
                                    code = "2000";
                                    result.put("reason", "修改成功, 其他订单状态修改失败");
                                    logger.debug("修改成功, 其他订单状态修改失败");
                                }
                            }
                            else
                            {
                                code = "4017";
                                result.put("reason", "修改失败");
                                logger.debug("修改失败");
                            }
                            
                            // 把有效订单移除
                            List<OrderForApp> list = orderMap.get(order.getShangpinID());
                            if (list != null)
                            {
                                list.remove(order);
                            }
                    	}
                        break;
                    // 发货
                    case 32:
                        // 判断是否有快递ID和快递单号,有得话，更新快递ID和单号,需要发货推送
                        if (KuaidiID != -1 && !"-1".equals(Kuaididanhao))
                        {
                            if (appDao.modifyOrderStatus(id, status, KuaidiID, Kuaididanhao) > 0)
                            {
                            	OrderForApp neworder = appDao.getOrderInfo(id);
                                // 发货,推送给买家
                            	if(!"jiagongdan".equals(neworder.getShangpinlx())){
                            		if (pushCtrl.pushToMaijia(neworder))
                                    {
                                        code = "2000";
                                        result.put("reason", "修改成功, 推送成功");
                                        logger.debug("修改成功");
                                    }
                                    else
                                    {
                                        code = "2000";
                                        result.put("reason", "修改成功, 推送失败");
                                        logger.debug("修改成功");
                                    }
                            	}
                                
                                if(UserType.SALER.getValue().equals(neworder.getMaijiaLx()) && UserType.SALER.getValue().equals(neworder.getMmaijiaLx()) && "jiagongdan".equals(neworder.getShangpinlx())){
                                	//发布的加工单发货, 推送消息给商户
                                	pushCtrl.worksheetForDeliver(neworder);
                                }
                            }
                            else
                            {
                                code = "4017";
                                result.put("reason", "修改失败");
                                logger.debug("暂无数据");
                            }
                            
                        }
                        else
                        {
                            if (appDao.modifyOrderStatus(id, status) > 0)
                            {
                                code = "2000";
                                result.put("reason", "修改成功");
                                logger.debug("修改成功");
                            }
                            else
                            {
                                code = "4017";
                                result.put("reason", "修改失败");
                                logger.debug("暂无数据");
                            }
                        }
                        break;
                    // 收货
                    case 64:
                        if (!"-1".equals(Shoukuanma))
                        {
                            if (appDao.modifyOrderStatus(id, status, Shoukuanma) > 0)
                            {
                                code = "2000";
                                result.put("reason", "修改成功");
                                logger.debug("修改成功");
                                if(UserType.SALER.getValue().equals(order.getMaijiaLx()) && UserType.SALER.getValue().equals(order.getMmaijiaLx()) && "jiagongdan".equals(order.getShangpinlx())){
                                	//接受的加工单被收货, 推送消息给商户
                                	pushCtrl.worksheetForMakeSure(order);
                                }
                            }
                            else
                            {
                                code = "4017";
                                result.put("reason", "修改失败");
                                logger.debug("暂无数据");
                            }
                        }
                        else
                        {
                            if (appDao.modifyOrderStatus(id, status) > 0)
                            {
                                code = "2000";
                                result.put("reason", "修改成功");
                                logger.debug("修改成功");
                            }
                            else
                            {
                                code = "4017";
                                result.put("reason", "修改失败");
                                logger.debug("暂无数据");
                            }
                        }
                        break;
                    default:
                        if (appDao.modifyOrderStatus(id, status) > 0)
                        {
                            code = "2000";
                            result.put("reason", "修改成功");
                            logger.debug("修改成功");
                        }
                        else
                        {
                            code = "4017";
                            result.put("reason", "修改失败");
                            logger.debug("暂无数据");
                        }
                        break;
                
                }
                
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
    }
    
    /**
     * 确认收货
     * 
     * @param token
     * @param id
     * @param pay_id
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "order/makerSureToReceipt")
    @ResponseBody
    public Result makerSureToReceipt(String token, String id, String pay_id)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        try
        {
            if (tokenMap.containsKey(token))
            {
                if (appDao.makerSureToReceipt(id, pay_id) > 0)
                {
                    code = "2000";
                    result.put("reason", "确认收货成功");
                    OrderForApp order = appDao.getOrderInfo(id);
                    if("jiagongdan".equals(order.getShangpinlx())){
                    	//接受的加工单被收货, 推送消息到商户
                    	pushCtrl.worksheetForMakeSure(order);
                    }else{
                    	//用户确认收货, 推送消息到商户
                        pushCtrl.buyerMakeSure(order);
                    }
                    logger.debug("查询成功");
                }
                else
                {
                    code = "4013";
                    result.put("reason", "确认收货失败");
                    logger.debug("确认收货失败");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
    }
    
    /**
     * 延时收货
     * 
     * @param token
     * @param id
     * @param reason
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "order/delayToReceipt")
    @ResponseBody
    public Result delayToReceipt(String token, String id, String reason)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        try
        {
            if (tokenMap.containsKey(token))
            {
                if (appDao.delayToReceipt(id, reason) > 0)
                {
                    code = "2000";
                    result.put("reason", "延时收货成功");
                    logger.debug("延时收货成功");
                }
                else
                {
                    code = "4016";
                    result.put("reason", "延时收货失败");
                    logger.debug("延时收货失败");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
    }
    
    /**
     * 催款
     * 
     * @param token
     * @param id 订单id
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "order/dunning")
    @ResponseBody
    public Result dunning(String token, String id, String CuiKuanYy)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        try
        {
            if (tokenMap.containsKey(token))
            {
                if (appDao.dunningOrder(id, CuiKuanYy) > 0)
                {
                    if (pushCtrl.pushCuiKuanToBuyer(appDao, id, CuiKuanYy))
                    {
                        code = "2000";
                        result.put("reason", "催款成功, 已提醒买家");
                        logger.debug("催款成功");
                    }
                    else
                    {
                        code = "2000";
                        result.put("reason", "催款成功, 提醒买家失败");
                        logger.debug("催款成功");
                    }
                    
                }
                else
                {
                    code = "4015";
                    result.put("reason", "催款失败");
                    logger.debug("催款失败");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
    }
    
    /**
     * 增加收货地址
     * 
     * @param token
     * @param address
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "user/addShouhuodizhi")
    @ResponseBody
    public Result addShouhuodizhi(String token, Address address)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        try
        {
            if (tokenMap.containsKey(token))
            {
                if (appDao.addShouhuodizhi(address) > 0)
                {
                    code = "2000";
                    result.put("reason", "新增成功");
                    logger.debug("增加收货地址成功");
                }
                else
                {
                    code = "4014";
                    result.put("reason", "新增失败");
                    logger.debug("增加收货地址失败");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
    }
    
    /**
     * 修改收货地址
     * 
     * @param token
     * @param address
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "user/modifyShouhuodizhi")
    @ResponseBody
    public Result modifyShouhuodizhi(String token, Address address)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        try
        {
            if (tokenMap.containsKey(token))
            {
                if (appDao.modifyShouhuodizhi(address) > 0)
                {
                    code = "2000";
                    result.put("reason", "修改成功");
                    logger.debug("修改收货地址成功");
                }
                else
                {
                    code = "4014";
                    result.put("reason", "修改失败");
                    logger.debug("修改收货地址失败");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
    }
    
    
    /**
     * 删除收货地址
     * 
     * @param token
     * @param address
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "user/deleteAddress")
    @ResponseBody
    public Result deleteAddress(String token, int id)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        try
        {
            if (tokenMap.containsKey(token))
            {
                if (appDao.deleteAddress(id))
                {
                    code = "2000";
                    result.put("reason", "删除成功");
                    logger.debug("删除收货地址成功");
                }
                else
                {
                    code = "4014";
                    result.put("reason", "删除失败");
                    logger.debug("删除收货地址失败");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
    }
    /**
     * 获取广告
     * 
     * @param token
     * @param address
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "user/getAds")
    @ResponseBody
    public Result getAds(String token, String types, String point)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        List<Ads> adsList = new ArrayList<Ads>();
        try
        {
            if (tokenMap.containsKey(token))
            {
                adsList = appDao.getAdsList(types, point);
                if (adsList.size() > 0)
                {
                    code = "2000";
                    result.put("list", adsList);
                    result.put("reason", "查询成功");
                    logger.debug("查询成功");
                }
                else
                {
                    code = "4009";
                    result.put("reason", "暂无数据");
                    logger.debug("暂无数据");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
    }
    
    /**
     * 获取快递方式
     * 
     * @param token
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "order/getExpressList")
    @ResponseBody
    public Result getExpressList(String token)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        List<Express> expressList = new ArrayList<Express>();
        try
        {
            if (tokenMap.containsKey(token))
            {
                expressList = appDao.getExpressList();
                if (expressList.size() > 0)
                {
                    code = "2000";
                    result.put("list", expressList);
                    result.put("reason", "查询成功");
                    logger.debug("查询成功");
                }
                else
                {
                    code = "4009";
                    result.put("reason", "暂无数据");
                    logger.debug("暂无数据");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
    }
    
    /**
     * 上传照片
     * 
     * @param req
     * @param orderCode 订单ID
     * @param goodsId 商品ID
     * @return author: ldz
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/goods/uploadPhotoForgood")
    @ResponseBody
    public String uploadPhotoForgood(HttpServletRequest req, String token, @RequestParam(required=false,defaultValue="1") int type)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        String severPath = "";
        try
        {
            if (tokenMap.containsKey(token))
            {
                if (req instanceof MultipartHttpServletRequest)
                {
                    MultipartHttpServletRequest request = (MultipartHttpServletRequest)req;
                    // 1. build an iterator
                    Iterator<String> itr = request.getFileNames();
                    // 2. get each file
                    if (itr.hasNext())
                    {
                        String md5 = Utils.md5Encode(String.valueOf(UUID.randomUUID()));// Utils.md5Encode(String.valueOf(UUID.randomUUID()));
                        String tmpName = null;
                        // 2.1 get next MultipartFile
                        MultipartFile mpf = request.getFile(itr.next());
                        String subfix = "";
                        
                        if (mpf.getOriginalFilename().indexOf(".") != -1)
                        {
                            subfix = mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf(".")); // 获取图片后缀
                        }
                        
                        tmpName = md5 + subfix;
                        
                        // 2.3 create new fileMeta
                        String path = "";
                        if(type == 1){
                        	path = Constants.DEFAULT_GOODS_PATH;// 构建商品图片保存的目录
                        }else{
                        	path = Constants.DEFAULT_CHAT_PATH;// 构建聊天图片保存的目录
                        }
                        
                        File dirFile = new File(request.getSession().getServletContext().getRealPath(path));
                        
                        // 创建目录
                        if (!dirFile.exists())
                        {
                            dirFile.mkdirs();
                        }
                        
                        // 得到图片保存目录的真实路径
                        String realpath = request.getSession().getServletContext().getRealPath(path + tmpName);
                        
                        FileOutputStream fps = new FileOutputStream(realpath);
                        FileCopyUtils.copy(mpf.getBytes(), fps);
                        // 得到图片保存目录的真实路径
                        
                        String urlPath =
                            request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort(); // url路径
                        severPath = urlPath + path + tmpName;
                        code = "2000";
                        result.put("url", severPath);
                        result.put("reason", "上传成功");
                        logger.debug("上传成功");
                    }
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
        return severPath;
    }
    
    /**
     * 发布商品
     * 
     * @param token
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "goods/addGoods")
    @ResponseBody
    public Result addGoods(HttpServletRequest req, String token, GoodsForApp goods_add)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        
        try
        {
            if (tokenMap.containsKey(token))
            {
                String[] url_list = null;
                String list_str = "";
                
                if (goods_add.getUrl_list_str() != null && !goods_add.getUrl_list_str().equals(""))
                {
                    list_str = goods_add.getUrl_list_str().replaceAll("\"", "");
                    
                    url_list = list_str.split(",,,,,");
                }
                if (url_list != null)
                {
                    goods_add.setUrl_list(url_list);
                }
                // 计算总价
                float danjia = goods_add.getDanjia();
                int shuliang = 1;
                if (goods_add.getShuliang() != null)
                {
                    try
                    {
                        shuliang = Integer.parseInt(goods_add.getShuliang());
                    }
                    catch (Exception e)
                    {
                        // TODO: handle exception
                        shuliang = 1;
                    }
                    
                }
                float zongjia = danjia * shuliang;
                goods_add.setJiage(zongjia);
                if (goods_add.getYuanshejishiID() == null || "".equals(goods_add.getYuanshejishiID()) || goods_add.getYuanshejishiID().length() == 0 || "undefined".equals(goods_add.getYuanshejishiID()))
                {
                    goods_add.setYuanshejishiID("-1");
                }
                if (appDao.addGoods(goods_add) > 0)
                {
                    if (goods_add.getLeixing().equals("maijiaxiu"))
                    {
                    	// 发布买家秀,推送给制作此商品的原设计师
                        pushCtrl.pushToYuanShejishi(goods_add);
                    }
                    code = "2000";
                    result.put("reason", "发布成功");
                    logger.debug("发布成功");
                }
                else
                {
                    code = "4018";
                    result.put("reason", "发布失败");
                    logger.debug("发布失败");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
        
    }
    
    /**
     * 发布商品{照片已经上传}
     * 
     * @param token
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "goods/addGoods2")
    @ResponseBody
    public Result addGoods2(HttpServletRequest req, String token, GoodsForApp goods)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        
        try
        {
            if (tokenMap.containsKey(token))
            {
                if (appDao.addGoods(goods) > 0)
                {
                    if (goods.getLeixing().equals("maijiaxiu"))
                    {
                    	// 发布买家秀,推送给制作此商品的原设计师
                        pushCtrl.pushToYuanShejishi(goods);
                    }
                    code = "2000";
                    result.put("reason", "发布成功");
                    logger.debug("发布成功");
                }
                else
                {
                    code = "4018";
                    result.put("reason", "发布失败");
                    logger.debug("发布失败");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效，请重新登录");
                logger.debug("登录失效，请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
        
    }
    
    /**
     * 根据制作类型获取模板列表
     * 
     * @param token
     * @param id
     * @param page_num
     * @param orderby
     * @param page_size
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "user/getMouldList")
    @ResponseBody
    public Result getMouldList(String token, String id, @RequestParam(defaultValue = "1") int page_num,
        @RequestParam(required = false, defaultValue = "sj") String orderby,
        @RequestParam(defaultValue = "15") int page_size)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        List<MouldForApp> mouldList = new ArrayList<MouldForApp>();
        
        try
        {
            if (tokenMap.containsKey(token))
            {
                mouldList = appDao.getMouldList(id, page_num, page_size, orderby);
                if (mouldList.size() > 0)
                {
                    code = "2000";
                    result.put("list", mouldList);
                    result.put("reason", "查询成功");
                    logger.debug("查询成功");
                }
                else
                {
                    code = "4009";
                    result.put("reason", "暂无数据");
                    logger.debug("暂无数据");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
    }
    
    /**
     * 根据模板id获取模板详情
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "user/getMouldDetail")
    @ResponseBody
    public Result getMouldDetail(String token, String id)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        MouldForApp mould = new MouldForApp();
        
        try
        {
            if (tokenMap.containsKey(token))
            {
                mould = appDao.getMouldDetail(id);
                if (mould != null)
                {
                    code = "2000";
                    result.put("result", mould);
                    result.put("reason", "查询成功");
                    logger.debug("查询成功");
                }
                else
                {
                    code = "4009";
                    result.put("reason", "暂无数据");
                    logger.debug("暂无数据");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
    }
    
    /**
     * 根据商品获取抢单个数
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "order/getOrderNum")
    @ResponseBody
    public Result getOrderNum(String token, String id)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        int num = 0;
        
        try
        {
            if (tokenMap.containsKey(token))
            {
                num = appDao.getOrderNum(id);
                code = "2000";
                result.put("result", num);
                result.put("reason", "查询成功");
                logger.debug("查询成功");
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
        
    }
    
    /**
     * 获取颜色
     * 
     * @param token
     * @param id
     * @param page_num
     * @param orderby
     * @param page_size
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "user/getColors")
    @ResponseBody
    public Result getColors(String token)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        List<ColorForApp> mouldList = new ArrayList<ColorForApp>();
        
        try
        {
            if (tokenMap.containsKey(token))
            {
                mouldList = appDao.getColors();
                if (mouldList.size() > 0)
                {
                    code = "2000";
                    result.put("list", mouldList);
                    result.put("reason", "查询成功");
                    logger.debug("查询成功");
                }
                else
                {
                    code = "4009";
                    result.put("reason", "暂无数据");
                    logger.debug("暂无数据");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
    }
    
    
    /**
     * 
     * TODO 商品点赞.
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年7月26日 下午5:04:50
     * 
     * @param token
     * @param id
     * @return
     */
    @RequestMapping(value = "/goods/praise")
    @ResponseBody
    public Result praise(String token, int id)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        try
        {
            if (tokenMap.containsKey(token))
            {
                if (appDao.praise(id))
                {
                    // 增加额外(自由定制)经验
                    if (appDao.changeJingyanByDianZan(id + "", 1))
                    {
                        code = "2000";
                        result.put("reason", "点赞成功");
                        logger.debug("点赞成功, 经验修改成功");
                    }
                    else
                    {
                        code = "2000";
                        result.put("reason", "点赞成功");
                        logger.debug("点赞成功, 经验修改失败");
                    }
                    
                }
                else
                {
                    code = "4010";
                    result.put("reason", "点赞失败");
                    logger.debug("点赞失败");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
    }
    
    /**
     * 用户头像上传 文件重命名(md5编码)
     * 
     * @param token
     * @param userId
     * @param type
     * @return
     */
    @RequestMapping(value = "/user/avatarUpload", method = RequestMethod.POST)
    @ResponseBody
    public Result avatarUpload(MultipartHttpServletRequest request, String token, String userId, String type)
    {
        
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        String urlPath = "";
        if (tokenMap.containsKey(token))
        {
            String fileName = null; // 头像保存名称
            FileMeta fileMeta = null;
            String md5 = Utils.md5Encode(String.valueOf(UUID.randomUUID()));
            // 1. build an iterator
            Iterator<String> itr = request.getFileNames();
            // 2. get each file
            while (itr.hasNext())
            {
                // 2.1 get next MultipartFile
                MultipartFile mpf = request.getFile(itr.next());
                String originalFileName = mpf.getOriginalFilename(); // 用户上传图片名称
                String subfix = originalFileName.substring(originalFileName.lastIndexOf(".")); // 获取图片后缀
                fileName = md5 + subfix;
                // 2.3 create new fileMeta
                fileMeta = new FileMeta();
                fileMeta.setFileName(fileName);
                fileMeta.setFileSize(mpf.getSize() / 1024 + " Kb");
                fileMeta.setFileType(mpf.getContentType());
                try
                {
                    fileMeta.setBytes(mpf.getBytes());
                    String path = Constants.DEFAULT_AVATAR_PATH + fileName;// 构建头像保存的目录
                    
                    File dirFile =
                        new File(request.getSession().getServletContext().getRealPath(Constants.DEFAULT_AVATAR_PATH));
                    
                    // 创建目录
                    if (!dirFile.exists())
                    {
                        dirFile.mkdirs();
                    }
                    
                    // 得到图片保存目录的真实路径
                    String realpath = request.getSession().getServletContext().getRealPath(path);
                    urlPath =
                        request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
                    FileOutputStream fps = new FileOutputStream(realpath);
                    FileCopyUtils.copy(mpf.getBytes(), fps);
                    // 旧头像路径
                    String filePath = appDao.getUserAvatarUrl(userId, type);
                    File file = new File(filePath);
                    
                    if (file.exists())
                    { // 删除旧头像
                        file.delete();
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (appDao.uploadPhoto(userId, type, urlPath, fileName))
            {
                code = "2000";
                result.put("reason", "头像上传成功! ");
                result.put("url", urlPath);
                result.put("fileName", fileName);
                // result.put("file", fileMeta);
                logger.debug("头像上传成功! ");
            }
            else
            {
                code = "4019";
                result.put("reason", "头像上传失败! ");
                logger.debug("头像上传失败! ");
            }
        }
        else
        {
            code = "4003";
            result.put("reason", "登录失效, 请重新登录");
            logger.debug("登录失效, 请重新登录");
        }
        return new Result(code, result);
    }
    
    /**
     * 
     * TODO 更新买家用户信息.
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年7月10日 上午10:13:29
     * 
     * @param token
     * @param buyer
     * @return
     */
    @ResponseBody
    @RequestMapping("/user/updateBuyerInfor")
    public Result updateBuyerInfor(String token, String loginId, @RequestParam(required = false) String username,
        @RequestParam(required = false) String quyu, @RequestParam(required = false) String weiboName,
        @RequestParam(required = false) String weixinName)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        
        try
        {
            
            if (tokenMap.containsKey(token))
            {
                Buyer buyer = new Buyer();
                buyer.setLoginId(loginId);
                buyer.setName(username);
                buyer.setQuyu(quyu);
                buyer.setWeibo_name(weiboName);
                buyer.setWeixin_name(weixinName);
                // 判断修改姓名次数
                if (username != null && appDao.getChangeCs(loginId, UserType.BUYER.getValue()) >= 1)
                {
                    code = "4020";
                    result.put("reason", "用户名只能修改1次");
                    return new Result(code, result);
                }
                if (appDao.updateBuyerInfo(buyer))
                {
                    code = "2000";
                    result.put("reason", "修改买家个人信息成功");
                    logger.debug("修改买家个人信息成功");
                }
                else
                {
                    code = "4020";
                    result.put("reason", "修改买家个人信息失败");
                    logger.debug("修改买家个人信息失败");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
    }
    
    /**
     * 
     * TODO 更新卖家用户信息.
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年7月10日 上午10:13:29
     * 
     * @param buyer
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping("/user/updateSalerInfor")
    public Result updateSalerInfor(String token, String loginId, @RequestParam(required = false) String username,
        @RequestParam(required = false) String quyu, @RequestParam(required = false) String weiboName,
        @RequestParam(required = false) String weixinName, @RequestParam(required = false) String zhifuZhanghu,
        @RequestParam(required = false) String bank_card_no, @RequestParam(required = false) String issuing_bank)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        
        try
        {
            if (tokenMap.containsKey(token))
            {
                Saler saler = new Saler();
                saler.setLoginId(loginId);
                saler.setName(username);
                saler.setQuyu(quyu);
                saler.setWeibo_name(weiboName);
                saler.setWeixin_name(weixinName);
                saler.setZhifuzhanghu(zhifuZhanghu);
                saler.setBank_card_no(bank_card_no);
                saler.setIssuing_bank(issuing_bank);
                // 判断修改姓名次数
                if (username != null && appDao.getChangeCs(loginId, UserType.SALER.getValue()) >= 1)
                {
                    code = "4020";
                    result.put("reason", "用户名只能修改1次");
                    return new Result(code, result);
                }
                if (appDao.updateSalerInfo(saler))
                {
                    code = "2000";
                    result.put("reason", "修改卖家个人信息成功");
                    logger.debug("修改卖家个人信息成功");
                }
                else
                {
                    code = "4021";
                    result.put("reason", "修改卖家个人信息失败");
                    logger.debug("修改卖家个人信息失败");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
    }
    
    @ResponseBody
    @RequestMapping("/user/getShouhuodizhi")
    public Result getShouhuodizhi(String token, String yonghuid, String yonghulx)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        try
        {
            if (tokenMap.containsKey(token))
            {
                List<Address> list = appDao.getShouhuodizhi(yonghuid, yonghulx);
                if (list.size() > 0)
                {
                    code = "2000";
                    result.put("list", list);
                    result.put("reason", "查询成功");
                    logger.debug("查询成功");
                }
                else
                {
                    code = "4007";
                    result.put("reason", "暂无数据");
                    logger.debug("暂无数据");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
    }
    
    /**
     * 
     * TODO 修改用户尺码.
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年7月10日 上午10:13:29
     * 
     * @param buyer
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping("/user/modifyUserSize")
    public Result modifyUserSize(String token, String loginId, String name, String chima, String shengao,
        String tizhong, String yaowei, String tunwei, String xiongwei, String miaoshu, String id)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        UserSize size = new UserSize();
        size.setLoginId(loginId);
        size.setName(name);
        size.setNormal_size(chima);
        size.setHeight(shengao);
        size.setWeight(tizhong);
        size.setWaist(yaowei);
        size.setBust(xiongwei);
        size.setHip(tunwei);
        size.setId(id);
        size.setDescription(miaoshu);
        try
        {
            if (tokenMap.containsKey(token))
            {
                if (appDao.modifyUserSize(size))
                {
                    code = "2000";
                    result.put("reason", "修改尺寸信息成功");
                    logger.debug("修改尺寸信息成功");
                }
                else
                {
                    code = "4021";
                    result.put("reason", "修改尺寸信息失败");
                    logger.debug("修改尺寸信息失败");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
    }
    
    /**
     * 更改抢单设计师
     * 
     * @param token
     * @param oldId
     * @param newId
     * @return
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/order/changeBuyer")
    public Result changeBuyer(String token, String oldId, String newId)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        try
        {
            if (tokenMap.containsKey(token))
            {
                if (appDao.changeBuyer(oldId, newId))
                {
                    code = "2000";
                    result.put("reason", "修改抢单设计师成功");
                    logger.debug("修改抢单信息成功");
                }
                else
                {
                    code = "4023";
                    result.put("reason", "修改抢单信息失败");
                    logger.debug("修改抢单信息失败");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
    }
    
    /**
     * 
     * TODO	支付宝回调接口.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年9月10日 下午2:54:46
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/pay/notify")
    @ResponseBody
    public String notify(HttpServletRequest request, HttpServletResponse response)
    {
        String result = "";
        try
        {
            // 获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<String, String>();
            Map<?, ?> requestParams = request.getParameterMap();
            for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext();)
            {
                String name = (String)iter.next();
                String[] values = (String[])requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++)
                {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
                params.put(name, valueStr);
            }
            
            // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
            // 商户订单号
            
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            logger.debug("out_trade_no=" + out_trade_no);
            // 支付宝交易号
            
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            logger.debug("trade_no=" + trade_no);
            // 交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
            logger.debug("trade_status=" + trade_status);
            // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
            
            // 交易金额
            String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"), "UTF-8");
            System.out.println(total_fee);
            logger.debug("total_fee=" + total_fee);
            
            logger.debug("支付接口====== param======" + params);
            OrderForApp order = appDao.getOrderInfo(out_trade_no);
            logger.debug("订单状态= " + order.getZhuangtai());
            if (AlipayNotify.verify(params))
            {// 验证成功\
            
                // ////////////////////////////////////////////////////////////////////////////////////////
                // 请在这里加上商户的业务逻辑程序代码
                
                // ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
                
                if (trade_status.equals("TRADE_FINISHED"))
                {
                    // 判断该笔订单是否在商户网站中已经做过处理
                    // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    // 如果有做过处理，不执行商户的业务程序
                    
                    // 注意：
                    // 该种交易状态只在两种情况下出现
                    // 1、开通了普通即时到账，买家付款成功后。
                    // 2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
                    // 只需要修改支付宝交易号
                    if (appDao.changeOrderPayCode(out_trade_no, trade_no))
                    {
                        
                        logger.debug("买家付款成功。修改支付宝交易号成功");
                    }
                    else
                    {
                        logger.debug("买家付款成功。修改支付宝交易号失败");
                    }
                    
                }
                else if (trade_status.equals("TRADE_SUCCESS"))
                {
                    // 判断该笔订单是否在商户网站中已经做过处理
                    // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    // 如果有做过处理，不执行商户的业务程序
                    
                    // 注意：
                    // 该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
                    // 只需要修改支付宝交易号
                    if (appDao.changeOrderPayCode(out_trade_no, trade_no))
                    {
                        logger.debug("买家付款成功。修改支付宝交易号成功");
                    }
                    else
                    {
                        logger.debug("买家付款成功。修改支付宝交易号失败");
                    }
                }
                
                // ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
                
                result = "success"; // 请不要修改或删除
                
                // ////////////////////////////////////////////////////////////////////////////////////////
            }
            else
            {// 验证失败
                appDao.addShuliang(order);
                logger.debug("验证失败");
                result = "fail";
            }
        }
        catch (Exception e)
        {
            result = "fail";
            logger.debug("----------支付通知出现异常：----------" + e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }
    
    /**
     * 获取一条短信验证码
     * 
     * @param moblie
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/SMS/getSMSCode")
    @ResponseBody
    public Result getSMSCode(String moblie)
    {
        // 移除
        // 防止内存溢出，用户重复登陆时，先清除上次的会话
        if (SMStokenMap.containsKey(moblie))
        {
            SMStokenMap.remove(moblie);
        }
        // 发送短信
        String codeSMS = getOneSMSCODE();
        Result result = sendSMS(moblie, codeSMS);
        return result;
    }
    
    /**
     * 修改订单地址
     * 
     * @param moblie
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/order/xiugaidizhi")
    @ResponseBody
    public Result xiugaiDizhi(String token, OrderForApp order)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        try
        {
            if (tokenMap.containsKey(token))
            {
                if (appDao.xiugaiDizhi(order))
                {
                    code = "2000";
                    result.put("reason", "修改地址成功");
                    logger.debug("修改地址成功");
                }
                else
                {
                    code = "4023";
                    result.put("reason", "修改地址失败");
                    logger.debug("修改地址失败");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
    }
    
    /**
     * 验证短信验证码
     * 
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/SMS/validateSMS")
    @ResponseBody
    public Result validateSMS(String moblie, String codeSMS)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        // 首先判断号码是否已注册
        if (appDao.getUser(moblie) == null)
        {
            Map<String, Long> temp = SMStokenMap.get(moblie);
            System.out.println("moblie:" + moblie);
            System.out.println("codeSMS:" + codeSMS);
            
            if (null == temp)
            {
                System.out.println("验证码无效, 请重新获取");
                code = "4004";
                result.put("reason", "验证码无效, 请重新获取");
                return new Result(code, result);
            }
            System.out.println("temp:" + temp);
            Long time = temp.get(codeSMS);
            System.out.println("time:" + time);
            if (null == time)
            {
                code = "4004";
                result.put("reason", "验证码错误");
                System.out.println("验证码错误");
                return new Result(code, result);
            }
            if (isAbled(time))
            {
                
                System.out.println("验证码没有失效");
                code = "2000";
                result.put("reason", "验证成功");
                // 移除
                // 防止内存溢出，用户重复登陆时，先清除上次的会话
                if (SMStokenMap.containsKey(moblie))
                {
                    SMStokenMap.remove(moblie);
                }
                return new Result(code, result);
            }
            else
            {
                code = "4004";
                result.put("reason", "验证码失效");
                System.out.println("验证码失效");
                return new Result(code, result);
            }
        }
        else
        {
            code = "4004";
            result.put("reason", "电话号码已被注册");
            System.out.println("电话号码已被注册");
            return new Result(code, result);
        }
    }
    
    /**
     * 
     * TODO	举报接口.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年9月10日 下午2:56:10
     * @param token
     * @param photo
     * @return
     */
    @RequestMapping(value = "/tupian/jubao")
    @ResponseBody
    public Result jubao(String token, PhotoBean photo)
    {
        /**
         * 1.客户用户头像 2.商户用户头像 3.商品图片 4.模板图片
         */
        // 需要用到图片表类型type，不同图片表的主键ID
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        try
        {
            if (tokenMap.containsKey(token))
            {
                // 举报
                if (appDao.jubao(photo))
                {
                    code = "2000";
                    result.put("reason", "举报成功");
                    logger.debug("举报成功");
                }
                else
                {
                    code = "4300";
                    result.put("reason", "举报失败");
                    logger.debug("举报失败");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
    }
    
    /**
     * 验证
     * 
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/SMS/validateSMSForRestPWD")
    @ResponseBody
    public Result validateSMSForRestPWD(String moblie, String codeSMS)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        // 首先判断号码是否已注册
        if (appDao.getUser(moblie) != null)
        {
            Map<String, Long> temp = SMStokenMap.get(moblie);
            System.out.println("moblie:" + moblie);
            System.out.println("codeSMS:" + codeSMS);
            
            if (null == temp)
            {
                System.out.println("电话号码无效");
                code = "4004";
                result.put("reason", "电话号码无效");
                return new Result(code, result);
            }
            System.out.println("temp:" + temp);
            Long time = temp.get(codeSMS);
            System.out.println("time:" + time);
            if (null == time)
            {
                code = "4004";
                result.put("reason", "验证码错误");
                System.out.println("验证码错误");
                return new Result(code, result);
            }
            if (isAbled(time))
            {
                
                System.out.println("验证码没有失效");
                code = "2000";
                result.put("reason", "验证成功");
                // 移除
                // 防止内存溢出，用户重复登陆时，先清除上次的会话
                if (SMStokenMap.containsKey(moblie))
                {
                    SMStokenMap.remove(moblie);
                }
                return new Result(code, result);
            }
            else
            {
                code = "4004";
                result.put("reason", "验证码失效");
                System.out.println("验证码失效");
                return new Result(code, result);
            }
        }
        else
        {
            code = "4004";
            result.put("reason", "此账号不存在, 请去注册");
            System.out.println("此账号不存在, 请去注册");
            return new Result(code, result);
        }
    }
    
    /**
     * 短信验证码有效时间
     */
    public static int time = 10;
    
    /**
     * 
     * TODO	发送短信验证码.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年9月20日 上午9:27:04
     * @param mobiles
     * @param codeSMS
     * @return
     */
    public Result sendSMS(String mobiles, String codeSMS)
    {
        String uri = "http://222.73.117.158/msg/";// 应用地址
        String account = "nb_ylkj";// 账号
        String pswd = "Ylkj16888";// 密码
        // String mobiles = mobilesParam;// 手机号码，多个号码使用","分割
        //【衣氏百秀】尊敬的衣氏百秀用户，您的验证码为XXXX,即刻输入，开启专属定制时代！（为保障账户安全，请勿泄露验证码）
        String content = "尊敬的衣氏百秀用户, 您的验证码为" + codeSMS + ", " + time + "分钟内有效。 " + "即刻输入, 开启专属定制时代!	(为保障账户安全, 请勿泄露验证码)";// 短信内容
        boolean needstatus = true;// 是否需要状态报告，需要true，不需要false
        String product = null;// 产品ID
        String extno = null;// 扩展码
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        String reason = "";
        try
        {
            String returnString =
                HttpSender.batchSend(uri, account, pswd, mobiles, content, needstatus, product, extno);
            // System.out.println("returnString:" + returnString);
            // TODO 处理返回值,参见HTTP协议文档
            // 代码 说明
            // 0 提交成功
            // 101 无此用户
            // 102 密码错
            // 103 提交过快（提交速度超过流速限制）
            // 104 系统忙（因平台侧原因，暂时无法处理提交的短信）
            // 105 敏感短信（短信内容包含敏感词）
            // 106 消息长度错（>536或<=0）
            // 107 包含错误的手机号码
            // 108 手机号码个数错（群发>50000或<=0;单发>200或<=0）
            // 109 无发送额度（该用户可用短信数已使用完）
            // 110 不在发送时间内
            // 111 超出该账户当月发送额度限制
            // 112 无此产品，用户没有订购该产品
            // 113 extno格式错（非数字或者长度不对）
            // 115 自动审核驳回
            // 116 签名不合法，未带签名（用户必须带签名的前提下）
            // 117 IP地址认证错,请求调用的IP地址不是系统登记的IP地址
            // 118 用户没有相应的发送权限
            // 119 用户已过期
            String id = "-1";
            try
            {
                id = returnString.split("\n")[0].split(",")[1];
                System.out.println(id);
                
            }
            catch (Exception e)
            {
                // TODO: handle exception
                System.out.println("解析错误");
            }
            
            switch (Integer.parseInt(id))
            {
                case -1:
                    reason = "解析错误";
                    System.out.println("解析错误");
                    break;
                case 0:
                    code = "2000";
                    reason = "提交成功";
                    // 保存短信
                    Map<String, Long> temp = new HashMap<String, Long>();
                    temp.put(codeSMS, System.currentTimeMillis());
                    SMStokenMap.put(mobiles, temp);
                    System.out.println("提交成功");
                    break;
                case 101:
                    code = id;
                    reason = "无此用户";
                    System.out.println("无此用户");
                    break;
                case 102:
                    code = id;
                    reason = "密码错";
                    System.out.println("密码错");
                    break;
                case 103:
                    code = id;
                    reason = "提交过快（提交速度超过流速限制）";
                    System.out.println("提交过快（提交速度超过流速限制）");
                    break;
                case 104:
                    code = id;
                    reason = "系统忙（因平台侧原因，暂时无法处理提交的短信）";
                    System.out.println("系统忙（因平台侧原因，暂时无法处理提交的短信）");
                    break;
                case 105:
                    code = id;
                    reason = "敏感短信（短信内容包含敏感词）";
                    System.out.println("敏感短信（短信内容包含敏感词）");
                    break;
                case 106:
                    code = id;
                    reason = "消息长度错（>536或<=0）";
                    System.out.println("消息长度错（>536或<=0）");
                    break;
                case 107:
                    code = id;
                    reason = "包含错误的手机号码";
                    System.out.println("包含错误的手机号码");
                    break;
                case 108:
                    code = id;
                    reason = "手机号码个数错（群发>50000或<=0;单发>200或<=0）";
                    System.out.println("手机号码个数错（群发>50000或<=0;单发>200或<=0）");
                    break;
                case 109:
                    code = id;
                    reason = "无发送额度（该用户可用短信数已使用完）";
                    System.out.println("无发送额度（该用户可用短信数已使用完）");
                    break;
                case 110:
                    code = id;
                    reason = "不在发送时间内";
                    System.out.println("不在发送时间内");
                    break;
                case 111:
                    code = id;
                    reason = "超出该账户当月发送额度限制";
                    System.out.println("超出该账户当月发送额度限制");
                    break;
                case 112:
                    code = id;
                    reason = "无此产品，用户没有订购该产品";
                    System.out.println("无此产品，用户没有订购该产品");
                    break;
                case 113:
                    code = id;
                    reason = "extno格式错（非数字或者长度不对）";
                    System.out.println("extno格式错（非数字或者长度不对）");
                    break;
                case 114:
                    code = id;
                    reason = "自动审核驳回";
                    System.out.println("自动审核驳回");
                    break;
                case 115:
                    code = id;
                    reason = "签名不合法，未带签名（用户必须带签名的前提下）";
                    System.out.println("签名不合法，未带签名（用户必须带签名的前提下）");
                    break;
                case 116:
                    code = id;
                    reason = "签名不合法，未带签名（用户必须带签名的前提下）";
                    System.out.println("签名不合法，未带签名（用户必须带签名的前提下）");
                    break;
                case 117:
                    code = id;
                    reason = "IP地址认证错,请求调用的IP地址不是系统登记的IP地址";
                    System.out.println("IP地址认证错,请求调用的IP地址不是系统登记的IP地址");
                    break;
                case 118:
                    code = id;
                    reason = "用户没有相应的发送权限";
                    System.out.println("用户没有相应的发送权限");
                    break;
                case 119:
                    code = id;
                    reason = "用户已过期";
                    System.out.println("用户已过期");
                    break;
                default:
                    code = id;
                    reason = "未知错误:" + id;
                    System.out.println("未知错误:" + id);
                    break;
            }
        }
        catch (Exception e)
        {
            // TODO 处理异常
            e.printStackTrace();
        }
        result.put("reason", reason);
        return new Result(code, result);
    }
    
    /**
     * 存放所有生成的验证码
     * s1: 手机号
     * s2: 验证码
     * Long: 验证码生成时的时间戳
     */
    public static Map<String, Map<String, Long>> SMStokenMap = new HashMap<String, Map<String, Long>>();
    
    /**
     * 
     * 判断短信验证码是否失效
     * true: 未失效
     * false: 失效
     * @param hqtime
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isAbled(long hqtime)
    {
        Long s = (System.currentTimeMillis() - hqtime) / (1000 * 60);
        System.out.println("s:" + s);
        return s < time;
    }
    
    /**
     * 
     * TODO	生成一条短信验证码.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年9月20日 上午9:30:23
     * @return
     */
    public static String getOneSMSCODE()
    {
        // 产生4位随机数
        Random rand = new Random();
        StringBuffer sb = new StringBuffer();
        
        for (int j = 0; j < 4; j++)
        {
            int m = rand.nextInt(9);
            sb.append(m);
        }
        return sb.toString();
    }
    
    /**
     * 获取服务器端最新APK信息
     * 
     * @param request
     * @param pageName
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/getApkInfo")
    @ResponseBody
    public Result getApkInfo(HttpServletRequest request)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        try
        {
            // 获取最新版本APK
            Apk apk = appDao.getApk();
            result.put("apk", apk);
            result.put("reason", "查询成功");
            code = "2000";
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        
        return new Result(code, result);
    }
    
    /**
     * 
     * TODO 清除商品新评论个数.
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年8月26日 下午5:32:23
     * 
     * @param token
     * @param goodId
     * @return
     */
    @RequestMapping(value = "/goods/clearDiscussCount")
    @ResponseBody
    public Result clearDiscussCount(String token, int goodId)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        
        try
        {
            if (tokenMap.containsKey(token))
            {
                if (appDao.clearDiscussCount(goodId))
                {
                    code = "2000";
                    result.put("reason", "清除成功");
                    logger.debug("清除成功");
                }
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
    }
    
    /**
     * 
     * TODO 查询设计师历史作品新评论个数.
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年8月26日 下午5:32:23
     * 
     * @param token
     * @param goodId
     * @return
     */
    @RequestMapping(value = "/goods/getNewDiscussCount")
    @ResponseBody
    public Result getNewDiscussCount(String token, int designerId)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        int count = 0;
        try
        {
            if (tokenMap.containsKey(token))
            {
                count = appDao.getNewDiscussCount(designerId);
                code = "2000";
                result.put("reason", "查询成功");
                result.put("count", count);
                logger.debug("查询成功");
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
    }
    
    /**
     * 
     * TODO	查询签约支付宝账户信息.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年9月10日 下午2:59:21
     * @param token
     * @return
     */
    @RequestMapping(value = "/alipay/getAlipayInfo")
    @ResponseBody
    public Result getAlipayInfo(String token)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String code = "";
        try
        {
            if (tokenMap.containsKey(token))
            {
                code = "2000";
                // partner String类型 必选 合作者身份IDs
                // seller String类型 必选 卖家支付宝账号或对应的支付宝唯一用户号
                // rsaPrivate String类型 必选 合作者私钥
                // rsaPublic String类型 必选 支付宝公钥
                // notifyUrl String类型 必选 服务器通知路径
                String partner = Constants.PayInfo.PARTNER;
                String seller = Constants.PayInfo.SELLER;
                String rsaPrivate = Constants.PayInfo.RSA_PRIVATE;
                String rsaPublic = Constants.PayInfo.RSA_PUBLIC;
                
                result.put("reason", "查询成功");
                result.put("partner", partner);
                result.put("seller", seller);
                result.put("rsaPrivate", rsaPrivate);
                result.put("rsaPublic", rsaPublic);
                logger.debug("查询成功");
            }
            else
            {
                code = "4003";
                result.put("reason", "登录失效, 请重新登录");
                logger.debug("登录失效, 请重新登录");
            }
        }
        catch (Exception e)
        {
            code = "5000";
            result.put("reason", "服务器异常");
            logger.error(e.getMessage(), e);
        }
        return new Result(code, result);
    }
  
    /**
     * 
     * TODO	查询账户状态.
     * <p>方法详细说明,如果要换行请使用<br>标签</p>
     * <br>
     * author: 周震
     * date: 2015年9月8日 下午2:32:43
     * @param token
     * @param userId
     * @param userType
     * @return
     */
	@RequestMapping(value = "/user/getUserStatus")
	@ResponseBody
	public Result getUserStatus(String token, String userId, String userType) {
		Map<String, Object> result = new HashMap<String, Object>();
		String code = "";
		try {
			if (tokenMap.containsKey(token)) {
				// 判断是否封号
				if (appDao.isFenghao(userId, userType)) {
					code = "4010";
					result.put("reason", "该卖家账号已被冻结");
					logger.debug("该卖家账号已被冻结");
				} else {
					code = "2000";
					result.put("reason", "该卖家账号正常");
					logger.debug("该卖家账号正常");
				}
			} else {
				code = "4003";
				result.put("reason", "登录失效, 请重新登录");
				logger.debug("登录失效, 请重新登录");
			}
		} catch (Exception e) {
			code = "5000";
			result.put("reason", "服务器异常");
			logger.error(e.getMessage(), e);
		}
		return new Result(code, result);
	}
	
	/**
     * 获取token列表
     * 
     * @param user 用户对象
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/user/getTokenList")
    @ResponseBody
    public Result getTokenList()
    {
        Map<String, User> result = new HashMap<String, User>();
        String code = "2000";
        result = tokenMap;
        return new Result(code, result);
    }
}
