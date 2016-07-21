package com.Ian.pojo;

/**
 * 
 * 暂存公众号的全局唯一票据model
 * 
 * @author ian
 * @date 2016-07-20
 *
 */
public class AccessToken {
	
	private String appId;
	private String appSecret;
	private String token;
	private String expireTime;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

}
