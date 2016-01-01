package com.puyun.myshop.entity;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 用户类 过时
 * 
 * @author ldz 创建时间: 2015-2-14
 */
@Deprecated
public class User
{
    private String id;// 用户ID
    private String tel;// 手机号
    private String loginName;// 登录账号

    @JsonIgnore
    private String password;// 密码（MD5加密）

    public User()
    {
        super();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTel()
    {
        return tel;
    }

    public void setTel(String tel)
    {
        this.tel = tel;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getLoginName()
    {
        return loginName;
    }

    public void setLoginName(String loginName)
    {
        this.loginName = loginName;
    }

}
