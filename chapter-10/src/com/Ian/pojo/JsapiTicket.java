package com.Ian.pojo;

/**
 * 
 * 暂存公众号用于调用微信JS接口的临时票据model
 * 
 * @author ian
 * @date 2016-07-20
 *
 */
public class JsapiTicket {

	private Integer id;
	private String token;
	private String ticket;
	private String expireTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

}
