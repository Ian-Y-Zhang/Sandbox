<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>OAuth2.0网页授权</title>
<meta name="viewport" content="width=device-width,user-scalable=0">
<link rel="stylesheet"
	href="http://demo.open.weixin.qq.com/jssdk/css/style.css">
</head>
<body>
	<%
		String nickName = (String) request.getSession().getAttribute("nickName");
	%>
	<div class="wxapi_container">
		<div class="lbox_close wxapi_form">
			<h3 id="menu-basic">菜单</h3>
			<span class="desc"><%=nickName%>，你好！</span>
			<button class="btn btn_primary" id="applyNewMenu">运行</button>
		</div>
	</div>
</body>
<script type="text/javascript">
	document.getElementById('applyNewMenu').onclick = function() {
		self.location = 'test.jsp';
	};
</script>
</html>