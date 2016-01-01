package com.puyun.clothingshow.entity;

public enum UserType {
	//买家：kehu
	//卖家：shanghu
	BUYER("kehu"), SALER("shanghu");
    
    private final String value;

    //构造器默认也只能是private, 从而保证构造函数只能在内部使用
    UserType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
