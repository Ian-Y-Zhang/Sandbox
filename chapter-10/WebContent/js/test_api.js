wx.ready(function() {
	// 1 判断当前版本是否支持指定 JS 接口，支持批量判断
	document.querySelector('#checkJsApi').onclick = function() {
		wx.checkJsApi({
			jsApiList : [ 'scanQRCode' ],
			success : function(res) {
				alert(JSON.stringify(res));
			}
		});
	};

	// 扫描条形码并返回结果
	document.querySelector('#scanQRCode1').onclick = function() {
		wx.scanQRCode({
			needResult : 1,
			scanType: ["barCode"],
			desc : 'scanQRCode desc',
			success : function(res) {
				alert(JSON.stringify(res));
				alert(res['resultStr']);
			}
		});
	};

});