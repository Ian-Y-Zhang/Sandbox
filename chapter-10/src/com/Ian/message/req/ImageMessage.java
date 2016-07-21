package com.Ian.message.req;

/**
 * 
 * 图片消息类
 * 
 * @author Ian
 * @date 2016-05-12
 *
 */

public class ImageMessage extends BaseMessage {

	private String PicUrl;

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		this.PicUrl = picUrl;
	}

}
