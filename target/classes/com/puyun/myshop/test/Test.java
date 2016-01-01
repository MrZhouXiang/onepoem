package com.puyun.myshop.test;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.puyun.myshop.base.util.Utils;
import com.puyun.myshop.ctrl.AppCtrl;
import com.puyun.myshop.dao.AppDao;
import com.puyun.myshop.entity.User;

public class Test
{
    private static final Logger logger = Logger.getLogger(Test.class);
    
    public static void main(String[] args)
    {
        @SuppressWarnings("resource")
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-*.xml");
        AppDao dao = (AppDao)ctx.getBean("appDao");
      
        for (int i = 0; i < 100; i++)
        {
            User user = new User();
            String tel= "15000"+Utils.random6();
            user.setTel(tel);
            user.setPassword("e10adc3949ba59abbe56e057f20f883e");//123456
            user.setLoginName(tel);
//            user.setType("1");
            reg(dao, user);
        }
        
    }
    
    public static void reg(AppDao appDao, User user)
    {
        if (appDao.getUser(user.getTel()) == null)
        {
            int userId = appDao.register(user);
            user.setId(String.valueOf(userId));
            logger.debug("注册成功");
        }
        else
        {
            logger.debug("该手机号已被注册");
        }
    }
}
