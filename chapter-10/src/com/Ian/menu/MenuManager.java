package com.Ian.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.Ian.menu.Button;
import com.Ian.menu.ClickButton;
import com.Ian.menu.ComplexButton;
import com.Ian.menu.Menu;
import com.Ian.menu.ViewButton;
import com.Ian.pojo.Token;
import com.Ian.util.CommonUtil;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 菜单管理器类
 * 
 * @author Ian
 * @date 2016-05-14
 */

public class MenuManager {

	private static Logger log = LoggerFactory.getLogger(MenuManager.class);

	/**
	 * 
	 * 定义菜单结构
	 * 
	 * @return
	 */
	private static Menu getMenu() {
		ClickButton btn11 = new ClickButton();
		btn11.setName("猜数字");
		btn11.setType("click");
		btn11.setKey("guessnumber");

		ViewButton btn12 = new ViewButton();
		btn12.setName("公众号测试 - 扫一扫");
		btn12.setType("view");
		btn12.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe1f433ef3e719a7b&redirect_uri=http%3a%2f%2fwww.xvjb.cn%2fweixin%2ftest.jsp&scope=snsapi_userinfo&response_type=code&state=STATE#wechat_redirect");
		
		ViewButton btn13 = new ViewButton();
		btn13.setName("公众号测试 - OAuth2.0");
		btn13.setType("view");
		btn13.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe1f433ef3e719a7b&redirect_uri=http%3a%2f%2fwww.xvjb.cn%2fweixin%2foauthServlet&scope=snsapi_userinfo&response_type=code&state=STATE#wechat_redirect");

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("游戏");
		mainBtn1.setSub_button(new Button[] { btn11, btn12, btn13 });

		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1 });

		return menu;
	}

	public static void main(String[] args) {
		String appId = "wxe1f433ef3e719a7b";
		String appSecret = "94c30e41e756c5d44c46e47cc71c0eef";
		// String appId = "wx40123c07352b0237";
		// String appSecret = "bccb9a5dbea9fb2ef358348478df6d06";

		Token token = null;
		String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
		String requestUrl = token_url.replace("APPID", appId).replace("APPSECRET", appSecret);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				token = new Token();
				token.setAccessToken(jsonObject.getString("access_token"));
				token.setExpiresIn(jsonObject.getIntValue("expires_in"));
			} catch (JSONException e) {
				token = null;
				log.error("take token failed errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"),
						jsonObject.getString("errmsg"));
			}
		}

		if (null != token) {
			boolean result = MenuUtil.createMenu(getMenu(), token.getAccessToken());
			if (result) {
				log.info("menu created success!");
			} else {
				log.info("menu create failed!");
			}
		}
	}
}
