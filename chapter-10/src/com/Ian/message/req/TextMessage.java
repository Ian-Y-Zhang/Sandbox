package com.Ian.message.req;

/**
 * 
 * 文本消息类
 * 
 * @author Ian
 * @date 2016-05-12
 *
 */

public class TextMessage extends BaseMessage {

	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		this.Content = content;
	}

}
