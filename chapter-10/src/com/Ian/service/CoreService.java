package com.Ian.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.Ian.message.resp.TextMessage;
import com.Ian.util.FaceUtil;
import com.Ian.util.GameUtil;
import com.Ian.util.MessageUtil;

/**
 * 
 * 核心服务类
 * 
 * @author Ian
 * @date 2016-05-12
 *
 */
public class CoreService {

	/**
	 * 处理微信发来的请求
	 * 
	 * @param req
	 * @return xml
	 */
	public static String processRequest(HttpServletRequest req) {
		String respXml = null;
		String respContent = "未知的消息类型！";
		try {
			Map<String, String> reqMap = MessageUtil.parseXml(req);
			String fromUserName = reqMap.get("FromUserName");
			String toUserName = reqMap.get("ToUserName");
			String msgType = reqMap.get("MsgType");

			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				String content = reqMap.get("Content").trim();
				if (FaceUtil.isQqFace(content)) {
					textMessage.setContent(content);
					respXml = MessageUtil.messageToXml(textMessage);
				} else if (content.equals("help")) {
					respContent = GameService.getGameRule();
					textMessage.setContent(respContent);
					respXml = MessageUtil.messageToXml(textMessage);
				} else if (content.equalsIgnoreCase("score")) {
					respContent = GameService.getUserScore(req, fromUserName);
					textMessage.setContent(respContent);
					respXml = MessageUtil.messageToXml(textMessage);
				} else if (GameUtil.verifyNumber(content) && !GameUtil.verifyRepeat(content)) {
					respContent = GameService.process(req, fromUserName, content);
					textMessage.setContent(respContent);
					respXml = MessageUtil.messageToXml(textMessage);
				} else {
					respContent = "请输入4个不重复的数字，例如：0269";
					textMessage.setContent(respContent);
					respXml = MessageUtil.messageToXml(textMessage);
				}
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息";
				textMessage.setContent(respContent);
				respXml = MessageUtil.messageToXml(textMessage);
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是语音消息";
				textMessage.setContent(respContent);
				respXml = MessageUtil.messageToXml(textMessage);
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
				respContent = "您发送的是视频消息";
				textMessage.setContent(respContent);
				respXml = MessageUtil.messageToXml(textMessage);
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息";
				textMessage.setContent(respContent);
				respXml = MessageUtil.messageToXml(textMessage);
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息";
				textMessage.setContent(respContent);
				respXml = MessageUtil.messageToXml(textMessage);
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				String eventType = reqMap.get("Event");
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "您好，谢谢关注Ian的个人公众号!\n回复help 查看游戏玩法。";
					textMessage.setContent(respContent);
					respXml = MessageUtil.messageToXml(textMessage);
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// 无需回复
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
					// TODO 处理扫描带参数二维码事件
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
					String latitude = reqMap.get("Latitude");
					String longitude = reqMap.get("Longitude");
					String precision = reqMap.get("Precision");
					respContent = latitude + "||" + longitude + "||" + precision;
					textMessage.setContent(respContent);
					respXml = MessageUtil.messageToXml(textMessage);
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					String eventKey = reqMap.get("EventKey");
					if (eventKey.equals("guessnumber")) {
						respContent = GameService.getGameRule();
						textMessage.setContent(respContent);
						respXml = MessageUtil.messageToXml(textMessage);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respXml;
	}

	/**
	 * 
	 * 得到包含指定Unicode代码点的字符串
	 * 
	 * @param codePoint
	 * @return
	 */
	public static String emoji(int codePoint) {
		return String.valueOf(Character.toChars(codePoint));
	}
}
