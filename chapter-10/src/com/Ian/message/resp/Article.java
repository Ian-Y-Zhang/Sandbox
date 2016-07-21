package com.Ian.message.resp;

/**
 * 
 * 图文model
 * 
 * @author Ian
 * @date 2016-05-12
 *
 */

public class Article {

	private String Title;

	private String Description;

	private String PicUrl;

	private String Url;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		this.Description = description;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		this.PicUrl = picUrl;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		this.Url = url;
	}

}
