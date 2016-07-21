<%@page import="java.util.Map"%>
<%@page import="com.Ian.util.JSSDKsign"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>沙盘游戏</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=0">
<link rel="stylesheet"
	href="http://demo.open.weixin.qq.com/jssdk/css/style.css">
</head>
<body ontouchstart="">
	<div class="wxapi_container">
		<div class="wxapi_index_container">
			<ul class="label_box lbox_close wxapi_index_list">
				<li class="label_item wxapi_index_item"><a class="label_inner"
					href="#menu-basic">基础接口</a></li>
				<li class="label_item wxapi_index_item"><a class="label_inner"
					href="#menu-scan">微信扫一扫接口</a></li>
			</ul>
		</div>
		<div class="lbox_close wxapi_form">
			<h3 id="menu-basic">基础接口</h3>
			<span class="desc">判断当前客户端是否支持指定JS接口</span>
			<button class="btn btn_primary" id="checkJsApi">checkJsApi</button>

			<h3 id="menu-scan">微信扫一扫</h3>
			<span class="desc">调起微信扫一扫接口</span>
			<button class="btn btn_primary" id="scanQRCode1">scanQRCode(直接返回结果)</button>
		</div>
	</div>
</body>
<%
	String url = request.getRequestURL().toString();
	String param = request.getQueryString();
	if (null != param) {
		url += "?" + request.getQueryString();
	}
	Map<String, String> ret = JSSDKsign.sign(url);
%>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>
	wx.config({
		debug : false,
		appId : '<%=ret.get("appId")%>',
		timestamp : <%=ret.get("timestamp")%>,
		nonceStr : '<%=ret.get("nonceStr")%>',
		signature : '<%=ret.get("signature")%>',
		jsApiList : [ 'checkJsApi', 'scanQRCode' ]
	});
</script>
<script src="js/test_api.js"></script>
</html>