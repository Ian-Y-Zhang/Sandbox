package com.Ian.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.Ian.pojo.AccessToken;
import com.Ian.pojo.JsTicket;
import com.Ian.pojo.JsapiTicket;
import com.Ian.pojo.Token;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;


/**
 * 
 * 通用工具类
 * 
 * @author Ian
 * @date 2016-05-14
 */

public class CommonUtil {

	private static Logger log = LoggerFactory.getLogger(CommonUtil.class);

	public final static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	public final static String JSAPI_TICKER_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

	public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr){
		JSONObject jsonObject = null;
		try{
			TrustManager[] tm = {new MyX509TrustManager()};
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod(requestMethod);
			if(null != outputStr){
				OutputStream outputStream = conn.getOutputStream();
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while((str = bufferedReader.readLine()) != null){
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			jsonObject = JSONObject.parseObject(buffer.toString());
		}catch(ConnectException ce){
			log.error("connect time overflow:{}", ce);
		}catch(Exception e){
			log.error("https request abnormal:{}", e);
		}
		return jsonObject;
	}
	
	/**
	 * 
	 * 获取公众号的全局唯一票据
	 * 
	 * @param appid
	 * @param appsecret
	 * @return
	 */
	public static Token getToken(String appid, String appsecret){
		Token token = null;
		boolean bFlag = true;
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		AccessToken oAccessToken = new AccessToken();
		oAccessToken.setAppId(appid);
		oAccessToken.setAppSecret(appsecret);
		oAccessToken = AccessTokenUtil.getToken(oAccessToken);
		if (null != oAccessToken){
			bFlag = false;
			// access token未失效，直接返回数据库暂存的值
			String sDate = oAccessToken.getExpireTime();
			try {
				date = sdf.parse(sDate);
			} catch (ParseException e) {
				date = new Date();
				e.printStackTrace();
			}
			if (date.compareTo(new Date()) > 0){
				token = new Token();
				token.setAccessToken(oAccessToken.getToken());
				token.setExpiresIn(-1);
				return token;
			}
		}
	
		String requestUrl = TOKEN_URL.replace("APPID", appid).replace("APPSECRET", appsecret);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject){
			try{
				token = new Token();
				token.setAccessToken(jsonObject.getString("access_token"));
				token.setExpiresIn(jsonObject.getIntValue("expires_in"));
				// 暂存access token
				oAccessToken = new AccessToken();
				oAccessToken.setAppId(appid);
				oAccessToken.setAppSecret(appsecret);
				oAccessToken.setToken(token.getAccessToken());
				date = new Date();
				date.setTime(date.getTime() + token.getExpiresIn() * 1000L);
				oAccessToken.setExpireTime(sdf.format(date));
				if (bFlag){
					AccessTokenUtil.saveToken(oAccessToken);
				} else {
					AccessTokenUtil.updateToken(oAccessToken);
				}
			}catch(JSONException e){
				token = null;
				log.error("take token failed errcode:{} errmsg:{}",jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return token;
	}
	
	/**
	 * 
	 * 获取调用微信JS接口的临时票据
	 * 
	 * @param accessToken
	 * @return
	 */
	public static JsTicket getTicket(String accessToken){
		JsTicket ticket = null;
		boolean bFlag = true;
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JsapiTicket oJsapiTicket = new JsapiTicket();
		oJsapiTicket.setToken(accessToken);
		oJsapiTicket = JsapiTicketUtil.getTicket(oJsapiTicket);
		if (null != oJsapiTicket){
			bFlag = false;
			// jsapi ticket未失效，直接返回数据库暂存的值
			String sDate = oJsapiTicket.getExpireTime();
			try {
				date = sdf.parse(sDate);
			} catch (ParseException e) {
				date = new Date();
				e.printStackTrace();
			}
			if (date.compareTo(new Date()) > 0){
				ticket = new JsTicket();
				ticket.setTicket(oJsapiTicket.getTicket());
				ticket.setExpiresIn(-1);
				return ticket;
			}
		}

		String requestUrl = JSAPI_TICKER_URL.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject){
			try{
				ticket = new JsTicket();
				ticket.setTicket(jsonObject.getString("ticket"));
				ticket.setExpiresIn(jsonObject.getIntValue("expires_in"));
				// 暂存jsapi ticket
				oJsapiTicket = new JsapiTicket();
				oJsapiTicket.setToken(accessToken);
				oJsapiTicket.setTicket(ticket.getTicket());
				date = new Date();
				date.setTime(date.getTime() + ticket.getExpiresIn() * 1000L);
				oJsapiTicket.setExpireTime(sdf.format(date));
				if (bFlag){
					JsapiTicketUtil.saveTicket(oJsapiTicket);
				} else {
					JsapiTicketUtil.updateTicket(oJsapiTicket);
				}
			}catch(JSONException e){
				ticket = null;
				log.error("take js-sdk ticket failed errcode:{} errmsg:{}",jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return ticket;
	}
	
	/**
	 * 
	 * URL编码(UTF-8)
	 * 
	 * @param source
	 * @return
	 */
	public static String urlEncodeUTF8(String source){
		String result = source;
		try{
			result = URLEncoder.encode(source, "utf-8");
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 
	 * 根据内容类型判断文件扩展名
	 * 
	 * @param contentType 内容类型
	 * @return
	 */
	public static String getFileExt(String contentType){
		String fileExt = "";
		switch (contentType){
		case "image/jpeg":
			fileExt = ".jpg";
			break;
		case "audio/mpeg":
			fileExt = ".mp3";
			break;
		case "audio/amr":
			fileExt = ".amr";
			break;
		case "video/mp4":
			fileExt = ".mp4";
			break;
		case "video/mpeg4":
			fileExt = ".mp4";
			break;
		}
		return fileExt;
	}

	/**
	 * 
	 * 判断是不是微信浏览器
	 * 
	 * @param req
	 * @return
	 */
	public static boolean isMicroMessenger(HttpServletRequest req){
		boolean result = false;
		String userAgent = req.getHeader("User-Agent");
		if (userAgent.contains("MicroMessenger")){
			result = true;
		}
		return result;
	}
}
