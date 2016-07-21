package com.Ian.util;

import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.Formatter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;

/**
 * 
 * 获取JS-SDK使用权限签名
 * 
 * @author ian
 * @date 2017-07-20
 *
 */
public class JSSDKsign {
	public static void main(String[] args) {
		String url = "http://www.xvjb.cn/weixin/index.jsp";
		Map<String, String> ret = sign(url);
		for (Map.Entry<String, String> entry : ret.entrySet()) {
			System.out.println(entry.getKey() + " --- " + entry.getValue());
		}
	}

	public static Map<String, String> sign(String url) {
		String appId = "wxe1f433ef3e719a7b";
		String appSecret = "94c30e41e756c5d44c46e47cc71c0eef";
		String accessToken = CommonUtil.getToken(appId, appSecret).getAccessToken();
		String jsapi_ticket = CommonUtil.getTicket(accessToken).getTicket();

		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string;
		String signature = "";

		string = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);
		ret.put("appId", appId);
		
		return ret;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
}
