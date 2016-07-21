package com.Ian.message.event;

/**
 * 
 * 事件基类
 * 
 * @author Ian
 * @date 2016-05-12
 *
 */

public class BaseEvent {

	private String ToUserName;

	private String FromUserName;

	private long CreateTime;

	private String MsgType;

	private String Event;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		this.ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		this.CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		this.MsgType = msgType;
	}

	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		this.Event = event;
	}

}
