package com.Ian.message.event;

/**
 * 
 * 扫描带参数二维码事件
 * 
 * @author Ian
 * @date 2016-05-12
 *
 */

public class QRCodeEvent extends BaseEvent {

	private String EventKey;

	private String Ticket;

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		this.EventKey = eventKey;
	}

	public String getTicket() {
		return Ticket;
	}

	public void setTicket(String ticket) {
		this.Ticket = ticket;
	}

}
