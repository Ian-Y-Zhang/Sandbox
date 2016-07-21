package com.Ian.message.req;

/**
 * 
 * 请求消息基类（普通用户 -> 公众帐号）
 * 
 * @author Ian
 * @date 2016-05-12
 *
 */

public class BaseMessage {
	
	private String ToUserName;
	
	private String FromUserName;
	
	private long CreateTime;
	
	private String MsgType;
	
	private long MsgId;

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

	public long getMsgId() {
		return MsgId;
	}

	public void setMsgId(long msgId) {
		this.MsgId = msgId;
	}

}
