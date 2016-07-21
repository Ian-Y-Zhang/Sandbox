package com.Ian.message.event;

/**
 * 
 * 自定义菜单事件
 * 
 * @author Ian
 * @date 2016-05-12
 *
 */

public class MenuEvent extends BaseEvent {

	private String EventKey;

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		this.EventKey = eventKey;
	}

}
