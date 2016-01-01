package com.puyun.clothingshow.base.util;

import com.puyun.clothingshow.entity.PageBean;

public class DBUtil
{
    public static final String getPageString(PageBean page)
    {
        return " limit " + page.getPageSize() + " offset " + (page.getPageSize() * (page.getCurrentPage() - 1));
    }
}
