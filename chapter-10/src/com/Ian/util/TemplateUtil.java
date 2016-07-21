package com.Ian.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 发送模板消息工具类
 * 
 * @author ian
 * @date 2016-07-21
 *
 */

public class TemplateUtil {

	private static Logger log = LoggerFactory.getLogger(TemplateUtil.class);
	
	public static void sendTemplateMessage(String accessToken, String jsonReq) {
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonReq);
		if (null != jsonObject) {
			int errcord = jsonObject.getIntValue("errcode");
			if (errcord != 0) {
				log.error("sent template message failed errcode:{} errmsg:{}",jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
			}
		}
	}
}
