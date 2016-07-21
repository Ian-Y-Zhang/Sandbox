package com.Ian.message.resp;

/**
 * 
 * 视频消息类
 * 
 * @author Ian
 * @date 2016-05-12
 *
 */

public class VideoMessage extends BaseMessage {

	private Video Video;

	public Video getVideo() {
		return Video;
	}

	public void setVideo(Video video) {
		this.Video = video;
	}

}
