package com.Ian.pojo;

/**
 * 
 * 凭证
 * 
 * @author Ian
 * @date 2016-05-14
 */

public class Token {

	private String accessToken;

	private int expiresIn;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

}
