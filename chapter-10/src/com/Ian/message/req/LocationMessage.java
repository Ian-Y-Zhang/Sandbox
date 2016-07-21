package com.Ian.message.req;

/**
 * 
 * 地理位置消息类
 * 
 * @author Ian
 * @date 2016-05-12
 *
 */

public class LocationMessage extends BaseMessage {

	private String Location_X;

	private String Location_Y;

	private String Scale;

	private String Label;

	public String getLocation_X() {
		return Location_X;
	}

	public void setLocation_X(String location_X) {
		this.Location_X = location_X;
	}

	public String getLocation_Y() {
		return Location_Y;
	}

	public void setLocation_Y(String location_Y) {
		this.Location_Y = location_Y;
	}

	public String getScale() {
		return Scale;
	}

	public void setScale(String scale) {
		this.Scale = scale;
	}

	public String getLabel() {
		return Label;
	}

	public void setLabel(String label) {
		this.Label = label;
	}

}
