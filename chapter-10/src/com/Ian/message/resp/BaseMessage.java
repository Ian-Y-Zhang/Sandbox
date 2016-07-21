package com.Ian.message.resp;

/**
 * 
 * 消息基类（公众帐号 -> 普通用户）
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

}
