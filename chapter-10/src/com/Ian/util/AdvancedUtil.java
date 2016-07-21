package com.Ian.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.Ian.pojo.SNSUserInfo;
import com.Ian.pojo.WeixinOauth2Token;
import com.Ian.pojo.WeixinUserInfo;
import com.Ian.pojo.WeixinUserList;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 高级接口工具类
 * 
 * @author Ian
 * @date 2016-05-16
 */

public class AdvancedUtil {

	private static Logger log = LoggerFactory.getLogger(AdvancedUtil.class);

	/***************************************** OAuth2.0网页授权接口 *****************************************/

	/**
	 * 
	 * 获取网页授权凭证
	 * 
	 * @param appId
	 *            公众帐号的唯一标识
	 * @param appSecret
	 *            公众帐号的密钥
	 * @param code
	 * @return WeixinOauth2Token
	 */
	public static WeixinOauth2Token getOauth2AccessToken(String appId, String appSecret, String code) {
		WeixinOauth2Token wat = null;
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		requestUrl = requestUrl.replace("APPID", appId);
		requestUrl = requestUrl.replace("SECRET", appSecret);
		requestUrl = requestUrl.replace("CODE", code);

		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				wat = new WeixinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getIntValue("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			} catch (Exception e) {
				wat = null;
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("get Oauth2 failed errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return wat;
	}

	/**
	 * 
	 * @param appId
	 *            公众帐号的唯一标识
	 * @param refreshToken
	 * @return WeixinOauth2Token
	 */
	public static WeixinOauth2Token refreshOauth2AccessToken(String appId, String refreshToken) {
		WeixinOauth2Token wat = null;
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
		requestUrl = requestUrl.replace("APPID", appId);
		requestUrl = requestUrl.replace("REFRESH_TOKEN", refreshToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				wat = new WeixinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getIntValue("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			} catch (Exception e) {
				wat = null;
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("refresh oauth failed errcord:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return wat;
	}

	/**
	 * 
	 * 通过网页授权获取用户信息
	 * 
	 * @param accessToken
	 *            网页授权接口调用凭证
	 * @param openId
	 *            用户标识
	 * @return SNSUserInfo
	 */
	public static SNSUserInfo getSNSUserInfo(String accessToken, String openId) {
		SNSUserInfo snsUserInfo = null;
		String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				snsUserInfo = new SNSUserInfo();
				snsUserInfo.setOpenId(jsonObject.getString("openid"));
				snsUserInfo.setNickname(jsonObject.getString("nickname"));
				snsUserInfo.setSex(jsonObject.getIntValue("sex"));
				snsUserInfo.setCountry(jsonObject.getString("country"));
				snsUserInfo.setProvince(jsonObject.getString("province"));
				snsUserInfo.setCity(jsonObject.getString("city"));
				snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
				snsUserInfo.setPrivilegeList(JSONArray.parseArray(jsonObject.getString("privilege"), String.class));
			} catch (Exception e) {
				snsUserInfo = null;
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("get user infomation failed errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return snsUserInfo;
	}

	/***************************************** 获取用户基本信息(UnionID机制) ************************************/

	/**
	 * 
	 * 获取用户信息
	 * 
	 * @param accessToken
	 *            接口访问凭证
	 * @param openId
	 *            用户标识
	 * @return WeixinUserInfo
	 */
	public static WeixinUserInfo getUserInfo(String accessToken, String openId) {
		WeixinUserInfo weixinUserInfo = null;
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				weixinUserInfo = new WeixinUserInfo();
				weixinUserInfo.setOpenId(jsonObject.getString("openid"));
				weixinUserInfo.setSubscribe(jsonObject.getIntValue("subscribe"));
				weixinUserInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
				weixinUserInfo.setNickname(jsonObject.getString("nickname"));
				weixinUserInfo.setSex(jsonObject.getIntValue("sex"));
				weixinUserInfo.setCountry(jsonObject.getString("country"));
				weixinUserInfo.setProvince(jsonObject.getString("province"));
				weixinUserInfo.setCity(jsonObject.getString("city"));
				weixinUserInfo.setLanguage(jsonObject.getString("language"));
				weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
				weixinUserInfo.setRemark(jsonObject.getString("remark"));
				weixinUserInfo.setGroupId(jsonObject.getIntValue("groupid"));
				JSONArray jsonArray = jsonObject.getJSONArray("tagid_list");
				int[] aTagidList = new int[jsonArray.size()];
				for (int i = 0; i < jsonArray.size(); i++) {
					aTagidList[i] = jsonArray.getIntValue(i);
				}
				weixinUserInfo.setTagidList(aTagidList);
			} catch (Exception e) {
				if (0 == weixinUserInfo.getSubscribe()) {
					log.error("user {} have unsubscribed", weixinUserInfo.getOpenId());
				} else {
					int errorCode = jsonObject.getIntValue("errcode");
					String errorMsg = jsonObject.getString("errmsg");
					log.error("get user information failed errcode:{} errmsg:{}", errorCode, errorMsg);
				}
			}
		}
		return weixinUserInfo;
	}

	/**************************************** 获取用户列表 *************************************************/

	/**
	 * 
	 * 获取关注者列表
	 * 
	 * @param accessToken
	 *            调用接口凭证
	 * @param nextOpenId
	 *            第一个拉取的openId，若不填则默认从头开始拉取
	 * @return WeixinUserList
	 */
	public static WeixinUserList getUserList(String accessToken, String nextOpenId) {
		WeixinUserList weixinUserList = new WeixinUserList();

		if (null == nextOpenId) {
			nextOpenId = "";
		}
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("NEXT_OPENID", nextOpenId);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				weixinUserList = new WeixinUserList();
				weixinUserList.setTotal(jsonObject.getIntValue("total"));
				weixinUserList.setCount(jsonObject.getIntValue("count"));
				weixinUserList.setNextOpenId(jsonObject.getString("next_openid"));
				JSONObject dataObject = (JSONObject) jsonObject.get("data");
				weixinUserList.setOpenIdList(JSONArray.parseArray(dataObject.getString("openid"), String.class));
			} catch (JSONException e) {
				weixinUserList = null;
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("get user list failed errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return weixinUserList;
	}

}
