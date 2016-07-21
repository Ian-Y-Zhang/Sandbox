package com.Ian.message.resp;

/**
 * 
 * 图片消息类
 * 
 * @author Ian
 * @date 2016-05-12
 *
 */

public class ImageMessage extends BaseMessage {

	private Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image image) {
		this.Image = image;
	}

}
