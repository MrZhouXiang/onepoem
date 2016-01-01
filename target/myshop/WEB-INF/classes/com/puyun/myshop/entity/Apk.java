package com.puyun.myshop.entity;

/**
 * 
 * TODO	安卓安装包信息. 
 * Created on 2015年9月17日 下午4:22:35 
 * @author 周震
 */
public class Apk {
	private int apkid;
	private String url;
	private int versionCode;
	private String versionName;
	private String versionDesc;
	private int status;
	private int forcedUpdate;//0：不强制更新       1：强制更新
	private String apkName;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getApkid() {
		return apkid;
	}
	public void setApkid(int apkid) {
		this.apkid = apkid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public String getVersionDesc() {
		return versionDesc;
	}
	public void setVersionDesc(String versionDesc) {
		this.versionDesc = versionDesc;
	}
    public int getForcedUpdate()
    {
        return forcedUpdate;
    }
    public void setForcedUpdate(int forcedUpdate)
    {
        this.forcedUpdate = forcedUpdate;
    }
	public String getApkName() {
		return apkName;
	}
	public void setApkName(String apkName) {
		this.apkName = apkName;
	}

	
}
