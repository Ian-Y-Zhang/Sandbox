package com.Ian.message.req;

/**
 * 
 * 语音消息类
 * 
 * @author Ian
 * @date 2016-05-12
 *
 */

public class VoiceMessage extends BaseMessage {

	private String MediaId;

	private String Format;

	private String Recognition;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		this.MediaId = mediaId;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		this.Format = format;
	}

	public String getRecognition() {
		return Recognition;
	}

	public void setRecognition(String recognition) {
		this.Recognition = recognition;
	}

}
