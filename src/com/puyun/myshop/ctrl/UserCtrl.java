package com.puyun.myshop.ctrl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.puyun.myshop.dao.AppDao;
import com.puyun.myshop.dao.UserDao;

/**
 * 一首小诗客户商户相关请求控制类
 * 
 * @author 姓名
 * @version [版本号, 2014-9-10]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping("/user")
@Deprecated
public class UserCtrl
{
    private static final Logger logger = Logger.getLogger(UserCtrl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private PushCtrl pushCtrl;

    @Autowired
    private AppDao appDao;

    public UserCtrl()
    {
        super();
        logger.debug("创建对象UserCtrl");
    }

}
