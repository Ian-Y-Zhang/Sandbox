package com.Ian.message.req;

/**
 * 
 * 视频消息类
 * 
 * @author Ian
 * @date 2016-05-12
 *
 */

public class VideoMessage extends BaseMessage {

	private String MediaId;

	private String ThumbMediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		this.MediaId = mediaId;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.ThumbMediaId = thumbMediaId;
	}

}
