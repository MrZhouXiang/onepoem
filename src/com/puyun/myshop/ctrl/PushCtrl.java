package com.puyun.myshop.ctrl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Message.Builder;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

import com.puyun.myshop.base.Constants;
import com.puyun.myshop.base.DateUtil;
import com.puyun.myshop.base.MyPushClient;
import com.puyun.myshop.dao.AppDao;
import com.puyun.myshop.dao.PushDao;
import com.puyun.myshop.dao.SystemConfigDao;
import com.puyun.myshop.entity.Goods;
import com.puyun.myshop.entity.GoodsForApp;
import com.puyun.myshop.entity.OrderBean;
import com.puyun.myshop.entity.OrderForApp;
import com.puyun.myshop.entity.UserType;

/**
 * push控制
 */
@Controller
@RequestMapping("/push")
public class PushCtrl
{
    private static final Logger logger = Logger.getLogger(PushCtrl.class);
    
    // 推送版本号
    private int v = Constants.AppSysConstans.PushV;
    
    @Autowired
    private PushDao pushDao;
    
    @Autowired
    private AppDao appDao;
    
    @Autowired
    private SystemConfigDao systemConfigDao;
    
    public PushCtrl()
    {
        super();
        logger.debug("创建对象PushCtrl");
        
    }
    
    public static void main(String[] args)
    {
//        @SuppressWarnings("resource")
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-*.xml");
//        PushDao dao = (PushDao)ctx.getBean("pushDao");
//        Map<String, String> data = new HashMap<String, String>();
//        data.put("id", "");
//        data.put("reason", "test");
//        data.put("result", "test");
//        // 消息类型为“1”表示买断设计秀
//        //push(Audience.alias("1_shanghu_" + dao.getTelByUserId("63", "shanghu")), "测试类容", "测试标题", "3", data);
//        pushToOne(Audience.alias("1_kehu_" + dao.getTelByUserId("61", "kehu")), "测试类容", "测试标题", "3", data);
    		String date = "2015-10-27 10:44:51.0";
    		String result = date.split("\\.")[0];
    		System.out.println("result= " + result);
    }
    
    /**
     * 推送广播公共方法
     * 
     * @param audience
     * @param msgCont
     * @param title
     * @param type
     * @param data
     * @return
     * @see [类、类#方法、类#成员]
     */
    private static boolean pushToAll(Audience audience, String msgCont, String title, String type, Map<String, String> data)
    {
        boolean flag = false;
        Builder b = Message.newBuilder().setMsgContent(msgCont).setTitle(title).addExtra("type", type).addExtras(data);
        PushPayload payload = PushPayload.newBuilder()
            .setPlatform(Platform.android_ios())
            .setAudience(audience)
            .setMessage(b.build())
            .setOptions(Options.newBuilder().setApnsProduction(true).build())
            .build();
        PushResult result;
        try
        {
            result = com.puyun.myshop.base.MyPushClient.push(payload);
            flag = result.isResultOK();
            logger.debug("============推送结果==========" + result.isResultOK());
        }
        catch (APIConnectionException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (APIRequestException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flag;
    }
    
    /**
     * 推送到指定设备公共方法
     * 
     * @param audience
     * @param msgCont
     * @param title
     * @param type
     * @param data
     * @return
     * @see [类、类#方法、类#成员]
     */
    private static boolean pushToOne(Audience audience, String msgCont, String title, String type, Map<String, String> data)
    {
        boolean flag = false;
        Builder b = Message.newBuilder().setMsgContent(msgCont).setTitle(title).addExtra("type", type).addExtras(data);
        PushPayload payload = PushPayload.newBuilder()
        	.setPlatform(Platform.android_ios())
//        	.setNotification(Notification.newBuilder().addPlatformNotification(IosNotification.newBuilder().setAlert(title)
//        			.addExtras(data)
//        			.build())
//        			.addPlatformNotification(AndroidNotification.newBuilder()
//        			.setTitle("一首小诗")
//        			.setAlert(title)
//        			.setBuilderId(0)
//        			.addExtras(data)
//        			.build())
//        			.build())
            .setAudience(audience)
            .setMessage(b.build())
            .setOptions(Options.newBuilder().setApnsProduction(true).build())
            .build();
        PushResult result;
        try
        {
            result = MyPushClient.push(payload);
            flag = result.isResultOK();
            logger.debug("============推送结果==========" + result.isResultOK());
        }
        catch (APIConnectionException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (APIRequestException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flag;
    }
    
    
    private static boolean test(String msgCont, String title, String type, Map<String, String> data,String... alias)
    {
        boolean flag = false;
        PushResult result;
        try
        {
            //result = MyPushClient.push(payload);
        	result = MyPushClient.getJpushclient().sendAndroidNotificationWithAlias(title, title, data, alias);
            flag = result.isResultOK();
            logger.debug("============推送结果==========" + result.isResultOK());
        }
        catch (APIConnectionException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (APIRequestException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * 推送设计图买断成功消息给买家
     * 标题：买断成功
     * 内容：您成功以【具体金额】的价格买断了【设计师名称】的设计秀，请查收邮件
     * 类型：1
     * @param id
     * @param order
     * @return
     * @see [类、类#方法、类#成员]
     */
    public boolean pushShejituToBuyer(AppDao appDao, String id, OrderForApp order)
    {
        boolean flag = false;
        Map<String, String> data = new HashMap<String, String>();
        String title = "买断成功";
        String content = "您成功以【" + order.getZongjia() + "】的价格买断了【" + order.getSalerName() + "】的设计秀, 请查收邮件";
        data.put("id", order.getId());
        data.put("reason", content);
        data.put("result", title);
        data.put("date", DateUtil.getDate(DateUtil.YYYY_MM_DD_HHMMSS));
        // 消息类型为“1”表示买断设计秀
        if(UserType.BUYER.getValue().equals(order.getMaijiaLx())){
        	data.put("userType", UserType.BUYER.getValue());
            flag =
            		pushToOne(Audience.alias(v + "_kehu_" + pushDao.getTelByUserId(order.getMaijiaID(), "kehu")),
                        content,
                        title,
                        "1",
                        data);
        }else{
        	data.put("userType", UserType.SALER.getValue());
            flag =
            		pushToOne(Audience.alias(v + "_shanghu_" + pushDao.getTelByUserId(order.getMaijiaID(), "shanghu")),
                        content,
                        title,
                        "1",
                        data);
        }

        return flag;
    }
    
    /**
     * 抢商户发布的加工单的反馈
     * 标题：抢单成功/抢单失败
     * 内容：加工单【商品名】被您抢单成功，请尽快进行加工/抢单失败【失败原因/订单被取消或者用户选择了其他人】
     * 类型：2
     * @param id 商品ID
     * @return
     * @see [类、类#方法、类#成员]
     */
    public boolean pushFaileOderMsgToBuyer(AppDao appDao, String id)
    {
        boolean flag = false;
        Map<String, String> data = new HashMap<String, String>();
        String title = "";
        String content = "";
        List<String> failList = new ArrayList<String>();
        failList = appDao.getMmJiaIDListBySpIDForWorksheet(id);
        List<String> l = new ArrayList<String>();
        for (int i = 0; i < failList.size(); i++)
        {
            l.add(v + "_shanghu_" + pushDao.getTelByUserId(failList.get(i), "shanghu"));
        }
        if (failList != null && l.size() > 0)
        {
        	title = "抢单失败";
        	content = "抢单失败【订单被取消或者用户选择了其他人】";
        	data.put("id", id);
        	data.put("reason", content);
        	data.put("result", title);
        	data.put("date", DateUtil.getDate(DateUtil.YYYY_MM_DD_HHMMSS));
        	data.put("userType", UserType.SALER.getValue());
            // 消息类型为“2”推送抢单成功或失败消息给抢单用户
            flag = pushToOne(Audience.alias(l), content, title, "2", data);
        }
        String sucMmJiaIDBySpID = appDao.getSucMmJiaIDBySpIDForWorksheet(id);
        if(sucMmJiaIDBySpID.length() > 0){
        	GoodsForApp goods = appDao.getGoodsDetail(id);
        	title = "抢单成功";
        	content = "加工单【" + goods.getMingcheng() + "】被您抢单成功";
        	data.put("id", id);
        	data.put("reason", content);
        	data.put("result", title);
        	data.put("date", DateUtil.getDate(DateUtil.YYYY_MM_DD_HHMMSS));
        	data.put("userType", UserType.SALER.getValue());
        	// 消息类型为“2”推送抢单成功或失败消息给抢单用户
            flag = pushToOne(Audience.alias(v + "_shanghu_" + pushDao.getTelByUserId(sucMmJiaIDBySpID,"shanghu")), content, title, "2", data);
        }
        
        return flag;
    }
    
    /**
     * 发货, 推送给买家
     * 标题：卖家已发货
     * 内容：您的商品【商品名】于【发货时间】发货成功
     * 类型：3
     * @param id
     * @return
     * @see [类、类#方法、类#成员]
     */
    public boolean pushToMaijia(OrderForApp order)
    {
        boolean flag = false;
        Map<String, String> data = new HashMap<String, String>();
        GoodsForApp goods = appDao.getGoodsDetail(order.getShangpinID());
        data.put("id", order.getMaijiaID());
        String title = "卖家已发货";
        String content = "您的商品【" + goods.getMingcheng() + "】于【" + order.getFahuoSj().split("\\.")[0] + "】发货成功";
        data.put("reason", content);
        data.put("result", title);
        data.put("date", DateUtil.getDate(DateUtil.YYYY_MM_DD_HHMMSS));
        data.put("userType", UserType.BUYER.getValue());
        // 消息类型为“3”表示商户发货，对购买此订单的客户发送推送
        if(UserType.BUYER.getValue().equals(order.getMaijiaLx())){
            flag =
            		pushToOne(Audience.alias(v + "_kehu_" + pushDao.getTelByUserId(order.getMaijiaID(), "kehu")),
            		content,
            		title,
                    "3",
                    data);
        }else{
            flag =
            		pushToOne(Audience.alias(v + "_shanghu_" + pushDao.getTelByUserId(order.getMaijiaID(), "shanghu")),
            		content,
            		title,
                    "3",
                    data);
        }

        return flag;
    }
    
    /**
     * 商户进行催款操作
     * 标题：卖家催款
     * 内容：尊敬的用户是否收到【卖家名】为您定制的【商品名】, 如已收货请尽快确认收货
     * 类型：4
     * @param id：订单ID
     * @param cuiKuanYy：催款原因
     * @param mjID：买家ID
     * @return
     * @see [类、类#方法、类#成员]
     */
    public boolean pushCuiKuanToBuyer(AppDao appDao, String id, String cuiKuanYy)
    {
        // TODO Auto-generated method stub
        boolean flag = false;
        Map<String, String> data = new HashMap<String, String>();
        OrderForApp order = appDao.getOrderInfo(id);
        GoodsForApp goods = appDao.getGoodsDetail(order.getShangpinID());
        data.put("id", id);
        String title = "卖家催款";
        String content = "尊敬的用户是否收到【" + order.getSalerName() + "】为您定制的【" + goods.getMingcheng() + "】, 如已收货请尽快确认收货";
        data.put("reason", content);
        data.put("result", title);
        data.put("date", DateUtil.getDate(DateUtil.YYYY_MM_DD_HHMMSS));
        String mjID = "";
        if (appDao.getOrderInfo(id) != null && appDao.getOrderInfo(id).getMaijiaID() != null)
        {
            mjID = appDao.getOrderInfo(id).getMaijiaID();
            data.put("userType", UserType.BUYER.getValue());
            // 消息类型为“4”商户催款，推送消息给客户
            flag =
            		pushToOne(Audience.alias(v + "_kehu_" + pushDao.getTelByUserId(mjID, "kehu")), content, title, "4", data);
        }
        else
        {
            flag = false;
        }
        
        return flag;
    }
    
    /**
     * 发布买家秀,推送给制作此商品的原设计师
     * @param appDao
     * @param goods
     * @see [类、类#方法、类#成员]
     */
    public boolean pushToYuanShejishi(GoodsForApp goods)
    {
        // TODO Auto-generated method stub
        boolean flag = false;
        Map<String, String> data = new HashMap<String, String>();
        data.put("id", goods.getYuanshejishiID());
        data.put("reason", "您的作品被生成买家秀");
        data.put("result", "您的作品被生成买家秀");
        data.put("date", DateUtil.getDate(DateUtil.YYYY_MM_DD_HHMMSS));
        data.put("userType", UserType.SALER.getValue());
        // 消息类型为“5”表示买家秀/设计师界面被再次生成成衣秀订单，发送消息给制作此此前买家秀的商户
        flag =
        		pushToOne(Audience.alias(v + "_shanghu_" + pushDao.getTelByUserId(goods.getYuanshejishiID(), "shanghu")),
                "您的作品被生成买家秀",
                "您的作品被生成买家秀",
                "5",
                data);
        return flag;
    }
    
    /**
     * 成衣秀被修改制作类型,推送消息给发布者
     * 
     * @param goods
     * @see [类、类#方法、类#成员]
     */
    public boolean pushToPulisher(Goods goods)
    {
        // TODO Auto-generated method stub
        boolean flag = false;
        Map<String, String> data = new HashMap<String, String>();
        data.put("id", goods.getFabuzheid());
        data.put("reason", "您的成衣秀被修改制作类型");
        data.put("result", "您的成衣秀被修改制作类型");
        data.put("date", DateUtil.getDate(DateUtil.YYYY_MM_DD_HHMMSS));
        // 消息类型为“6”表示成衣秀被修改制作类型,推送消息给发布者
        if (UserType.BUYER.getValue().equals(goods.getFabuzhelx()))
        {
        	data.put("userType", UserType.BUYER.getValue());
            flag =
            		pushToOne(Audience.alias(v + "_kehu_" + pushDao.getTelByUserId(goods.getFabuzheid(), "kehu")),
                    "您的成衣秀被修改制作类型",
                    "您的成衣秀被修改制作类型",
                    "6",
                    data);
        }
        else
        {
        	data.put("userType", UserType.SALER.getValue());
            flag =
            		pushToOne(Audience.alias(v + "_shanghu_" + pushDao.getTelByUserId(goods.getFabuzheid(), "shanghu")),
                    "您的成衣秀被修改制作类型",
                    "您的成衣秀被修改制作类型",
                    "6",
                    data);
        }
        
        return flag;
    }
    
    /**
     * 图片或文字违章，提示警告
     * 
     * @param appDao
     * @param goods
     * @see [类、类#方法、类#成员]
     */
    public boolean pushToPulisherForViolation(Map<String, Object> map)
    {
        // TODO Auto-generated method stub
        boolean flag = false;
        Map<String, String> data = new HashMap<String, String>();
        data.put("id", map.get("publisherId").toString());
        data.put("reason", "您的图片涉嫌违章, 请尽快删除");
        data.put("result", "您的图片涉嫌违章, 请尽快删除");
        data.put("date", DateUtil.getDate(DateUtil.YYYY_MM_DD_HHMMSS));
        // 消息类型为“7”表示图片或文字违章，提示警告,推送消息给发布者
        if (UserType.BUYER.getValue().equals(map.get("publisherType")))
        {
        	data.put("userType", UserType.BUYER.getValue());
            flag =
            		pushToOne(Audience.alias(v + "_kehu_" + pushDao.getTelByUserId(map.get("publisherId").toString(), "kehu")),
                    "您的图片涉嫌违章, 请尽快删除",
                    "您的图片涉嫌违章, 请尽快删除",
                    "7",
                    data);
        }
        else
        {
        	data.put("userType", UserType.SALER.getValue());
            flag =
            		pushToOne(Audience.alias(v + "_shanghu_" + pushDao.getTelByUserId(map.get("publisherId").toString(), "shanghu")),
                    "您的图片涉嫌违章, 请尽快删除",
                    "您的图片涉嫌违章, 请尽快删除",
                    "7",
                    data);
        }
        return flag;
    }
    
    /**
     * 作品被下架
     * 标题：商品被下架
     * 内容：因您发布的商品【商品名】违反平台规定被强制下架, 对您造成不便请谅解
     * 类型：8
     * @param appDao
     * @param goods
     * @see [类、类#方法、类#成员]
     */
    public boolean pushToPulisherForSoldOut(Goods goods)
    {
        // TODO Auto-generated method stub
        boolean flag = false;
        Map<String, String> data = new HashMap<String, String>();
        data.put("id", goods.getFabuzheid());
        String title = "商品被下架";
        String content = "因您发布的商品【" + goods.getMingcheng() + "】违反平台规定被强制下架, 对您造成不便请谅解";
        data.put("reason", content);
        data.put("result", title);
        data.put("date", DateUtil.getDate(DateUtil.YYYY_MM_DD_HHMMSS));
        // 消息类型为“8”表示作品被下架,推送消息给发布者
        if (UserType.BUYER.getValue().equals(goods.getFabuzhelx()))
        {
        	data.put("userType", UserType.BUYER.getValue());
            flag =
            		pushToOne(Audience.alias(v + "_kehu_" + pushDao.getTelByUserId(goods.getFabuzheid(), "kehu")),
            		content,
            		title,
                    "8",
                    data);
        }
        else
        {
        	data.put("userType", UserType.SALER.getValue());
            flag =
            		pushToOne(Audience.alias(v + "_shanghu_" + pushDao.getTelByUserId(goods.getFabuzheid(), "shanghu")),
                    content,
                    title,
                    "8",
                    data);
        }
        
        return flag;
    }
    
    /**
     * 购买了被封号的设计师的作品需要推送, 提示设计师被封号, 订单取消, 将退款
     * 标题：订单被取消
     * 内容：因设计师【卖家名】违反规定被封号, 您的订单被取消, 官方会尽快退款请查收账户
     * 类型：9
     * @param designerId 被封号的商户ID
     * @see [类、类#方法、类#成员]
     */
    public boolean pushToPulisherForSalerFrozen(int designerId)
    {
        // TODO Auto-generated method stub
        boolean flag = false;
        List<OrderForApp> list = pushDao.getOrderList(designerId);
        for (OrderForApp order : list)
        {
            Map<String, String> data = new HashMap<String, String>();
            String title = "订单被取消";
        	String content = "因设计师【" + order.getSalerName() + "】违反规定被封号, 您的订单被取消, 官方会尽快退款请查收账户";
            data.put("id", order.getId());
            data.put("reason", content);
            data.put("result", title);
            data.put("date", DateUtil.getDate(DateUtil.YYYY_MM_DD_HHMMSS));
            // 消息类型为“9”表示购买了被封号的设计师的作品需要推送，提示设计师被封号，订单取消，将退款
            if (UserType.BUYER.getValue().equals(order.getMaijiaLx()))
            {
            	data.put("userType", UserType.BUYER.getValue());
                flag =
                		pushToOne(Audience.alias(v + "_kehu_" + pushDao.getTelByUserId(order.getMaijiaID(), "kehu")),
                        content,
                        title,
                        "9",
                        data);
            }
            else
            {
            	data.put("userType", UserType.SALER.getValue());
                flag =
                		pushToOne(Audience.alias(v + "_shanghu_" + pushDao.getTelByUserId(order.getMaijiaID(), "shanghu")),
                        content,
                        title,
                        "9",
                        data);
            }
        }
        return flag;
    }
    
    /**
     * 当订单放款状态改为已放款时，推送信息，您的订单XXXXX已放款，款项XXXX已打入您的账户，其实扣除平台费用XXX元
     * 标题：官方已放款
     * 内容：您的订单【商品名】确定放款成功, 敬请验收
     * 类型：10
     * @param order 修改放款状态的订单
     * @param platformFee 平台费用
     * 
     * @see [类、类#方法、类#成员]
     */
    public boolean pushToSalerForDeliver(OrderBean order, double platformFee)
    {
        // TODO Auto-generated method stub
        boolean flag = false;
        Map<String, String> data = new HashMap<String, String>();
        String salerId = order.getSaler_id();
//        String title = "您的订单" + order.getOrder_no() + "已放款";
//        String content =
//            "您的订单" + order.getOrder_no() + "已放款, 款项" + (order.getTotalPrice() - platformFee) + "元已打入您的账户, 其实扣除平台费用"
//                + platformFee + "元";
        GoodsForApp goods = appDao.getGoodsDetail(order.getGoods_id());
        String title = "官方已放款";
        String content = "您的订单【" + goods.getMingcheng() + "】确定放款成功, 敬请验收";
        data.put("id", salerId);
        data.put("reason", content);
        data.put("result", title);
        data.put("date", DateUtil.getDate(DateUtil.YYYY_MM_DD_HHMMSS));
        data.put("userType", UserType.SALER.getValue());
        // 消息类型为“10”表示当订单放款状态改为已放款时，推送信息，您的订单XXXXX已放款，款项XXXX已打入您的账户，其实扣除平台费用0元
        flag =
        		pushToOne(Audience.alias(v + "_shanghu_" + pushDao.getTelByUserId(salerId, "shanghu")),
                content,
                title,
                "10",
                data);
        return flag;
    }
    
    /**
     * 
     * TODO 商户当日设计图买断数量大于等于X时，发送广播，广播格式为：“【用户名】设计师的设计图被土豪买断，净赚【当日设计图买断总金额】.
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年8月20日 下午5:54:27
     * 
     * @param goodsId 商品ID
     * @return
     */
    public void pushToAllForBuyoutOrderCount(int goodsId)
    {
        
        Map<String, Object> paraMap = systemConfigDao.getSystemParameter();
        // 系统设置的买断数量
        int buyoutCount = (Integer)paraMap.get("buyoutCount");
        Map<String, Object> resultMap = pushDao.getTodayBuyoutOrderCount(goodsId);
        // 今日买断数量
        long todayBuyoutCount = (Long)resultMap.get("num");
        if (todayBuyoutCount >= buyoutCount)
        {
            // 设计师用户名
            String username = (String)resultMap.get("username");
            // 当日设计图买断总金额
            BigDecimal big = (BigDecimal)resultMap.get("totalFee");
            double totalFee = 0;
            if(big != null){
            	totalFee = big.doubleValue();
            }
            final Map<String, String> data = new HashMap<String, String>();
            final String title = "【" + username + "】" + "设计师的设计图被土豪买断";
            final String content = "【" + username + "】" + "设计师的设计图被土豪买断, 净赚" + "【" + totalFee + "】";
            data.put("reason", content);
            data.put("result", content);
            data.put("date", DateUtil.getDate(DateUtil.YYYY_MM_DD_HHMMSS));
            Constants.PUSH_WORKQUEUE.execute(goodsId + "_buyoutOrderCount",new Runnable()
            {
                
                @Override
                public void run()
                {
                    // TODO Auto-generated method stub
                	pushToAll(Audience.all(), content, title, "0", data);
                }
            });
            
        }
    }
    
    /**
     * 
     * TODO 商户当日成衣秀交易额大于等于X时，发送广播，广播格式为：“【用户名】设计师的作品大受好评 ，今日累计成交额已达【X】.
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年8月20日 下午5:54:42
     * 
     * @param goodsId 商品ID
     * @return
     */
    public void pushToAllForDesignerTradeAmount(int goodsId)
    {
        Map<String, Object> paraMap = systemConfigDao.getSystemParameter();
        // 系统设置的累计成交额
        double tradeAmount = (Double)paraMap.get("tradeAmount");
        Map<String, Object> resultMap = pushDao.getTodayDesignerTradeAmount(goodsId);
        // 今日累计成交额
        final BigDecimal todayTradeAmount = (BigDecimal)resultMap.get("totalFee");
        double d = 0;
        if(todayTradeAmount != null){
        	d = todayTradeAmount.doubleValue();
        }
        if (d >= tradeAmount)
        {
            // 设计师用户名
            String username = (String)resultMap.get("username");
            final Map<String, String> data = new HashMap<String, String>();
            final String title = "【" + username + "】" + "设计师的作品大受好评";
            final String content = "【" + username + "】" + "设计师的作品大受好评, 今日累计成交额已达" + "【" + d + "】";
            data.put("reason", content);
            data.put("result", content);
            data.put("date", DateUtil.getDate(DateUtil.YYYY_MM_DD_HHMMSS));
            Constants.PUSH_WORKQUEUE.execute(goodsId + "_designerTradeAmount",new Runnable()
            {
                
                @Override
                public void run()
                {
                    // TODO Auto-generated method stub
                	pushToAll(Audience.all(), content, title, "0", data);
                }
            });
            
        }
    }
    
    /**
     * 
     * TODO 成衣秀/设计图/买家秀/定制单的累计评论数量大于X时，发送广播：“【作品名称】大受欢迎，累计评论数 量已达X条，大家也可去膜拜评论”.
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年8月20日 下午5:54:56
     * 
     * @param goodsId 商品ID
     * @return
     */
    public void pushToAllForDiscussCount(int goodsId)
    {
        Map<String, Object> paraMap = systemConfigDao.getSystemParameter();
        // 系统设置的累计评论数量
        int discussCount = (Integer)paraMap.get("discussCount");
        Map<String, Object> resultMap = pushDao.getTodayDiscussCount(goodsId);
        // 累计评论数量
        final long totalDiscussCount = (Long)resultMap.get("num");
        
        if (totalDiscussCount >= discussCount)
        {
            // 商品名称
            String goodsName = (String)resultMap.get("goodsName");
            final Map<String, String> data = new HashMap<String, String>();
            final String title = "【" + goodsName + "】" + "大受好评";
            final String content =
                "【" + goodsName + "】" + "大受好评, 累计评论数量已达" + "【" + totalDiscussCount + "】" + "条, 大家也可去膜拜评论";
            data.put("reason", content);
            data.put("result", content);
            data.put("date", DateUtil.getDate(DateUtil.YYYY_MM_DD_HHMMSS));
            Constants.PUSH_WORKQUEUE.execute(goodsId + "_discussCount",new Runnable()
            {
                
                @Override
                public void run()
                {
                    // TODO Auto-generated method stub
                	pushToAll(Audience.all(), content, title, "0", data);
                }
            });
        }
    }
    
    /**
     * 
     * TODO 推送信息给被评论人.
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年8月21日 下午2:57:21
     * 
     * @param to_commenter_id
     * @return
     */
    // public boolean pushToAuthors(String to_commenter_id)
    // {
    // // TODO Auto-generated method stub
    // boolean flag = false;
    // Map<String, String> data = new HashMap<String, String>();
    // data.put("id", to_commenter_id);
    // data.put("reason", "您有一条新的评论");
    // data.put("result", "您有一条新的评论");
    // // 消息类型为“11”, 表示推送消息给发布者
    // if (UserType.BUYER.getValue().equals(to_commenter_id))
    // {
    // flag = push(Audience.alias("kehu_" + to_commenter_id), "您有一条新的评论", "您有一条新的评论", "11", data);
    // }
    // else
    // {
    // flag = push(Audience.alias("shanghu_" + to_commenter_id), "您有一条新的评论", "您有一条新的评论", "11", data);
    // }
    // return flag;
    // }
    
    /**
     * 
     * TODO 推送系统消息.
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年8月21日 下午3:26:03
     * 
     * @param title
     * @param content
     * @return
     */
    public boolean pushSystemMsg(String title, String content)
    {
        boolean flag = false;
        Map<String, String> data = new HashMap<String, String>();
        data.put("id", "");
        data.put("reason", content);
        data.put("result", content);
        data.put("date", DateUtil.getDate(DateUtil.YYYY_MM_DD_HHMMSS));
        flag = pushToAll(Audience.all(), content, title, "0", data);
        return flag;
    }
    
    /**
     * 
     * TODO 向个人推送消息.
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年8月24日 上午9:38:20
     * 
     * @param id
     * @param type
     * @param title
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping("/sendMsgToUser")
    public Map<String, Object> sendMsg(String id, String type, String title, String content)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag = false;
        Map<String, String> data = new HashMap<String, String>();
        data.put("id", id);
        data.put("reason", content);
        data.put("result", title);
        data.put("date", DateUtil.getDate(DateUtil.YYYY_MM_DD_HHMMSS));
        // 消息类型为“12”, 表示推送消息给个人
        if (UserType.BUYER.getValue().equals(type))
        {
        	data.put("userType", UserType.BUYER.getValue());
            flag = pushToOne(Audience.alias(v + "_kehu_" + pushDao.getTelByUserId(id, "kehu")), content, title, "12", data);
        	//flag = test(content, title, "12", data,(v + "_kehu_" + pushDao.getTelByUserId(id, "kehu")));
        }
        else
        {
        	data.put("userType", UserType.SALER.getValue());
            flag =
            		pushToOne(Audience.alias(v + "_shanghu_" + pushDao.getTelByUserId(id, "shanghu")),
                    content,
                    title,
                    "12",
                    data);
        }
        if (flag)
        {
            map.put("success", true);
            logger.debug("发送成功");
        }
        else
        {
            map.put("success", false);
            logger.debug("发送失败");
        }
        return map;
    }
    
    /**
     * 
     * TODO 强制下线通知.
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年8月24日 上午9:38:20
     * 
     * @param id
     * @param type
     * @param title
     * @param content
     * @return
     */
    public boolean forceOfflineToUser(String id, String type, int days)
    {
        boolean flag = false;
        Map<String, String> data = new HashMap<String, String>();
        String title = "强制下线通知";
        String content = "您的账号由于违反平台相关规定, 被管理员封号" + (days * 24) + "小时, 期间您将无法进行操作";
        data.put("id", id);
        data.put("reason", content);
        data.put("result", title);
        data.put("date", DateUtil.getDate(DateUtil.YYYY_MM_DD_HHMMSS));
        // 消息类型为“13”, 表示推送强制下线通知给个人
        if (UserType.BUYER.getValue().equals(type))
        {
        	data.put("userType", UserType.BUYER.getValue());
            flag = pushToOne(Audience.alias(v + "_kehu_" + pushDao.getTelByUserId(id, "kehu")), content, title, "13", data);
        }
        else
        {
        	data.put("userType", UserType.SALER.getValue());
            flag =
            		pushToOne(Audience.alias(v + "_shanghu_" + pushDao.getTelByUserId(id, "shanghu")),
                    content,
                    title,
                    "13",
                    data);
        }
        
        return flag;
    }
    
    /**
     * 
     * TODO 卖家接单(针对客户端发布的定制).
     * 标题：有卖家抢单
     * 内容：设计师【卖家名】已报价您发布的【商品名称】, 请去订单信息处查询详情
     * 类型：14
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年8月24日 上午9:38:20
     * 
     * @param id
     * @param type
     * @param title
     * @param content
     * @return
     */
    public boolean salerOffer(OrderForApp order)
    {
        boolean flag = false;
        Map<String, String> data = new HashMap<String, String>();
        GoodsForApp goods = appDao.getGoodsDetail(order.getShangpinID());
        String title = "有卖家抢单";
        String content = "设计师【" + pushDao.getUsername(order.getMmaijiaID(), "shanghu") + "】已报价您发布的【" + goods.getMingcheng() + "】, 请去订单信息处查询详情";
        data.put("id", order.getId());
        data.put("reason", content);
        data.put("result", title);
        data.put("date", DateUtil.getDate(DateUtil.YYYY_MM_DD_HHMMSS));
        data.put("userType", UserType.BUYER.getValue());
        flag = pushToOne(Audience.alias(v + "_kehu_" + pushDao.getTelByUserId(order.getMaijiaID(), "kehu")), content, title, "14", data);
        return flag;
    }
    
    /**
     * 
     * TODO 发布的成衣被订购.
     * 标题：成衣被订购
     * 内容：用户【买家名】订购了您发布的【商品名】, 请尽快制作
     * 类型：15
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年8月24日 上午9:38:20
     * 
     * @param id
     * @param type
     * @param title
     * @param content
     * @return
     */
    public boolean clothesForOrder(OrderForApp order)
    {
        boolean flag = false;
        Map<String, String> data = new HashMap<String, String>();
        GoodsForApp goods = appDao.getGoodsDetail(order.getShangpinID());
        String title = "成衣被订购";
        String content = "用户【" + order.getBuyerName() + "】订购了您发布的【" + goods.getMingcheng() + "】, 请尽快制作";
        data.put("id", order.getId());
        data.put("reason", content);
        data.put("result", title);
        data.put("date", DateUtil.getDate(DateUtil.YYYY_MM_DD_HHMMSS));
        data.put("userType", UserType.SALER.getValue());
        flag = pushToOne(Audience.alias(v + "_shanghu_" + pushDao.getTelByUserId(order.getMmaijiaID(), "shanghu")), content, title, "15", data);
        return flag;
    }
    
    /**
     * 
     * TODO 发布的设计被买断.
     * 标题：设计被买断
     * 内容：用户【买家名】买断了您发布的【商品名】
     * 类型：16
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年8月24日 上午9:38:20
     * 
     * @param id
     * @param type
     * @param title
     * @param content
     * @return
     */
    public boolean designForBuyOut(OrderForApp order)
    {
        boolean flag = false;
        Map<String, String> data = new HashMap<String, String>();
        GoodsForApp goods = appDao.getGoodsDetail(order.getShangpinID());
        String title = "设计被买断";
        String content = "用户【" + order.getBuyerName() + "】买断了您发布的【" + goods.getMingcheng() + "】";
        data.put("id", order.getId());
        data.put("reason", content);
        data.put("result", title);
        data.put("date", DateUtil.getDate(DateUtil.YYYY_MM_DD_HHMMSS));
        data.put("userType", UserType.SALER.getValue());
        flag = pushToOne(Audience.alias(v + "_shanghu_" + pushDao.getTelByUserId(order.getMmaijiaID(), "shanghu")), content, title, "16", data);
        return flag;
    }
    
    
    /**
     * 
     * TODO 用户确认收货.
     * 标题：用户已收货
     * 内容：您的订单【商品名】确定收货成功, 敬请等待官方放款
     * 类型：17
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年8月24日 上午9:38:20
     * 
     * @param id
     * @param type
     * @param title
     * @param content
     * @return
     */
    public boolean buyerMakeSure(OrderForApp order)
    {
        boolean flag = false;
        Map<String, String> data = new HashMap<String, String>();
        GoodsForApp goods = appDao.getGoodsDetail(order.getShangpinID());
        String title = "用户已收货";
        String content = "您的订单【"+ goods.getMingcheng() + "】确定收货成功, 敬请等待官方放款";
        data.put("id", order.getId());
        data.put("reason", content);
        data.put("result", title);
        data.put("date", DateUtil.getDate(DateUtil.YYYY_MM_DD_HHMMSS));
        data.put("userType", UserType.SALER.getValue());
        flag = pushToOne(Audience.alias(v + "_shanghu_" + pushDao.getTelByUserId(order.getMmaijiaID(), "shanghu")), content, title, "17", data);
        return flag;
    }
    
    
    /**
     * 
     * TODO 发布的加工单被人接单.
     * 标题：加工单被抢单
     * 内容：您发布的加工单【商品名】被【抢单用户名】抢单, 请到加工单详情查看
     * 类型：18
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年8月24日 上午9:38:20
     * 
     * @param id
     * @param type
     * @param title
     * @param content
     * @return
     */
    public boolean worksheetForOrder(OrderForApp order)
    {
        boolean flag = false;
        Map<String, String> data = new HashMap<String, String>();
        GoodsForApp goods = appDao.getGoodsDetail(order.getShangpinID());
        String title = "加工单被抢单";
        String content = "您发布的加工单【" + goods.getMingcheng() + "】被【" + order.getSalerName() + "】抢单, 请到加工单详情查看";
        data.put("id", order.getId());
        data.put("reason", content);
        data.put("result", title);
        data.put("date", DateUtil.getDate(DateUtil.YYYY_MM_DD_HHMMSS));
        data.put("userType", UserType.SALER.getValue());
        flag = pushToOne(Audience.alias(v + "_shanghu_" + pushDao.getTelByUserId(order.getMaijiaID(), "shanghu")), content, title, "18", data);
        return flag;
    }
    
    /**
     * 
     * TODO 发布的加工单发货.
     * 标题：加工单已发货
     * 内容：您发布的加工单【商品名】已加工完成, 已于【发货时间】成功发货
     * 类型：19
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年8月24日 上午9:38:20
     * 
     * @param id
     * @param type
     * @param title
     * @param content
     * @return
     */
    public boolean worksheetForDeliver(OrderForApp order)
    {
        boolean flag = false;
        Map<String, String> data = new HashMap<String, String>();
        GoodsForApp goods = appDao.getGoodsDetail(order.getShangpinID());
        String title = "加工单已发货";
        String content = "您发布的加工单【" + goods.getMingcheng() + "】已加工完成, 已于【" + order.getFahuoSj().split("\\.")[0] + "】成功发货";
        data.put("id", order.getId());
        data.put("reason", content);
        data.put("result", title);
        data.put("date", DateUtil.getDate(DateUtil.YYYY_MM_DD_HHMMSS));
        data.put("userType", UserType.SALER.getValue());
        flag = pushToOne(Audience.alias(v + "_shanghu_" + pushDao.getTelByUserId(order.getMaijiaID(), "shanghu")), content, title, "19", data);
        return flag;
    }
    
    
    /**
     * 
     * TODO 抢客户发布的定制的反馈.
     * 标题：抢单成功/抢单失败
     * 内容：成衣秀【商品名】被您抢单成功, 请尽快进行加工/抢单失败【失败原因/订单被取消或者用户选择了其他人】
     * 类型：20
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年8月24日 上午9:38:20
     * 
     * @param id
     * @param type
     * @param title
     * @param content
     * @return
     */
    public boolean customOrderResult(OrderForApp order)
    {
    	boolean flag = false;
        Map<String, String> data = new HashMap<String, String>();
        String title = "";
        String content = "";
        List<String> failList = new ArrayList<String>();
        failList = appDao.getMmJiaIDListBySpIDForCustomization(order.getShangpinID());
        List<String> l = new ArrayList<String>();
        for (int i = 0; i < failList.size(); i++)
        {
            l.add(v + "_shanghu_" + pushDao.getTelByUserId(failList.get(i), "shanghu"));
        }
        if (failList != null && l.size() > 0)
        {
        	title = "抢单失败";
        	content = "抢单失败【订单被取消或者用户选择了其他人】";
        	data.put("id", order.getId());
        	data.put("reason", content);
        	data.put("result", title);
        	data.put("date", DateUtil.getDate(DateUtil.YYYY_MM_DD_HHMMSS));
        	data.put("userType", UserType.SALER.getValue());
            flag = pushToOne(Audience.alias(l), content, title, "20", data);
        }
        String sucMmJiaIDBySpID = appDao.getSucMmJiaIDBySpIDForCustomization(order.getShangpinID());
        if(sucMmJiaIDBySpID.length() > 0){
        	GoodsForApp goods = appDao.getGoodsDetail(order.getShangpinID());
        	title = "抢单成功";
        	content = "成衣秀【" + goods.getMingcheng() + "】被您抢单成功, 请尽快进行加工";
        	data.put("id", order.getId());
        	data.put("reason", content);
        	data.put("result", title);
        	data.put("date", DateUtil.getDate(DateUtil.YYYY_MM_DD_HHMMSS));
        	data.put("userType", UserType.SALER.getValue());
            flag = pushToOne(Audience.alias(v + "_shanghu_" + pushDao.getTelByUserId(sucMmJiaIDBySpID,"shanghu")), content, title, "20", data);
        }
        
        return flag;
    }
    
    
    /**
     * 
     * TODO 接受的加工单被收货.
     * 标题：加工单被收货
     * 内容：您加工的加工单【商品名】已被收货, 敬请等待官方放款
     * 类型：21
     * <p>
     * 方法详细说明,如果要换行请使用<br>
     * 标签
     * </p>
     * <br>
     * author: 周震 date: 2015年8月24日 上午9:38:20
     * 
     * @param id
     * @param type
     * @param title
     * @param content
     * @return
     */
    public boolean worksheetForMakeSure(OrderForApp order)
    {
        boolean flag = false;
        Map<String, String> data = new HashMap<String, String>();
        GoodsForApp goods = appDao.getGoodsDetail(order.getShangpinID());
        String title = "加工单被收货";
        String content = "您加工的加工单【" + goods.getMingcheng() + "】已被收货, 敬请等待官方放款";
        data.put("id", order.getId());
        data.put("reason", content);
        data.put("result", title);
        data.put("date", DateUtil.getDate(DateUtil.YYYY_MM_DD_HHMMSS));
        data.put("userType", UserType.SALER.getValue());
        flag = pushToOne(Audience.alias(v + "_shanghu_" + pushDao.getTelByUserId(order.getMmaijiaID(), "shanghu")), content, title, "21", data);
        return flag;
    }
    
}
