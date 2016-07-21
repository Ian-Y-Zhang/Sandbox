package com.Ian.pojo;

/**
 * 
 * 微信JS接口的临时票据
 * 
 * @author ian
 * @date 2016-07-20
 *
 */
public class JsTicket {

	private String ticket;

	private int expiresIn;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

}
