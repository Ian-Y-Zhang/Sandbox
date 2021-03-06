package com.Ian.message.req;

/**
 * 
 * 链接消息类
 * 
 * @author Ian
 * @date 2016-05-12
 *
 */

public class LinkMessage extends BaseMessage {

	private String Title;

	private String Description;

	private String Url;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		this.Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		this.Description = description;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		this.Url = url;
	}

}
