package com.Ian.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Ian.pojo.SNSUserInfo;
import com.Ian.pojo.WeixinOauth2Token;
import com.Ian.util.AdvancedUtil;

/**
 * 
 * 授权后的回调请求处理
 * 
 * @author Ian
 * @date 2016-05-16
 */

public class OAuthServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1359052070024080458L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String code = req.getParameter("code");
		String appId = req.getParameter("appId");
		if (null == appId) {
			appId = "wxe1f433ef3e719a7b";
			// appId = "wx40123c07352b0237";
			req.setAttribute("appId", appId);
		}
		String appSecret = req.getParameter("appSecret");
		if (null == appSecret) {
			appSecret = "94c30e41e756c5d44c46e47cc71c0eef";
			// appSecret = "bccb9a5dbea9fb2ef358348478df6d06";
			req.setAttribute("appSecret", appSecret);
		}
		if (!"authdeny".equals(code)) {
			WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken(appId, appSecret, code);
			String accessToken = weixinOauth2Token.getAccessToken();
			String openId = weixinOauth2Token.getOpenId();
			SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);
			req.setAttribute("snsUserInfo", snsUserInfo);
			// 
			req.getSession().setAttribute("openId", openId);
			req.getSession().setAttribute("nickName", snsUserInfo.getNickname());
		}
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}

}
