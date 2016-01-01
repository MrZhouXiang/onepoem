package com.puyun.myshop.ctrl;

import javax.servlet.http.HttpSession;

import com.puyun.myshop.base.Constants;

public abstract class BaseCtrl {
	public void setLoginUser(HttpSession session, String tel) {
		session.setAttribute(Constants.APP_SESSION, tel);
	}

	public String getLoginUser(HttpSession session) {
		if (session == null) {
			return null;
		}
		return session.getAttribute(Constants.APP_SESSION).toString();
	}
}
