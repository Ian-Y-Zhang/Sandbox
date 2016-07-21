package com.Ian.template;

import com.Ian.util.CommonUtil;
import com.Ian.util.TemplateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Test {

	public static void main(String[] args) {
		TemplateCell templateCell = new TemplateCell();
		templateCell.setValue("First value");
		templateCell.setColor("#F00");

		TemplateTest templateTest = new TemplateTest();
		templateTest.setFirst(templateCell);
		
		templateCell = new TemplateCell();
		templateCell.setValue("Key world value 1");
		templateCell.setColor("#0F1");

		templateTest.setKeyword1(templateCell);
		
		templateCell = new TemplateCell();
		templateCell.setValue("Key world value 2");
		templateCell.setColor("#0F2");
		
		templateTest.setKeyword2(templateCell);
		
		templateCell = new TemplateCell();
		templateCell.setValue("Key world value 3");
		templateCell.setColor("#0F3");
		
		templateTest.setKeyword3(templateCell);
		
		templateCell = new TemplateCell();
		templateCell.setValue("Remark value");
		templateCell.setColor("#0FF");
		
		templateTest.setRemark(templateCell);
		
		Template template = new Template();
		template.setTouser("oerbbs9YkxUaU61loe1ObayQkLCw");
		template.setTemplate_id("d5iVDAGVisgbMhb-WkEA6wJsP1hFwcJIj3Zlsro5V9c");
		template.setUrl("http://www.sina.com");
		template.setTopcolor("#777");
		template.setData(templateTest);
		
		
		String str = JSON.toJSONString(template);
		System.out.println(str);
		
		String appId = "wxe1f433ef3e719a7b";
		String appSecret = "94c30e41e756c5d44c46e47cc71c0eef";
		String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
		String requestUrl = token_url.replace("APPID", appId).replace("APPSECRET", appSecret);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			String accessToken = jsonObject.getString("access_token");
			TemplateUtil.sendTemplateMessage(accessToken, str);
		}
	}

}
