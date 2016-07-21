package com.Ian.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.Ian.menu.Menu;
import com.Ian.util.CommonUtil;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 自定义菜单工具类
 * 
 * @author Ian
 * @date 2016-05-14
 */

public class MenuUtil {

	private static Logger log = LoggerFactory.getLogger(MenuUtil.class);

	public final static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	public final static String MENU_GET_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	public final static String MENU_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

	/**
	 * 
	 * 创建菜单
	 * 
	 * @param menu
	 *            菜单实例
	 * @param accessToken
	 *            凭证
	 * @return true 成功 false 失败
	 */
	public static boolean createMenu(Menu menu, String accessToken) {
		boolean result = false;
		String url = MENU_CREATE_URL.replace("ACCESS_TOKEN", accessToken);
		String jsonMenu = JSONObject.toJSONString(menu);
		JSONObject jsonObject = CommonUtil.httpsRequest(url, "POST", jsonMenu);
		if (null != jsonObject) {
			int errorCode = jsonObject.getIntValue("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
			} else {
				result = false;
				log.error("create menu failed errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return result;
	}

	/**
	 * 
	 * 查询菜单
	 * 
	 * @param accessToken
	 *            凭证
	 * @return
	 */
	public static String getMenu(String accessToken) {
		String result = null;
		String requestUrl = MENU_GET_URL.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			result = jsonObject.toString();
		}
		return result;
	}

	/**
	 * 
	 * 删除菜单
	 * 
	 * @param accessToken
	 *            凭证
	 * @return true 成功 false 失败
	 */
	public static boolean deleteMenu(String accessToken) {
		boolean result = false;
		String requestUrl = MENU_DELETE_URL.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			int errorCode = jsonObject.getIntValue("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
			} else {
				result = false;
				log.error("delete menu failed errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return result;
	}
}
