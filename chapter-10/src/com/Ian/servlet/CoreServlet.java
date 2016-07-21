package com.Ian.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Ian.service.CoreService;
import com.Ian.util.SignUtil;

public class CoreServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5204938522070903514L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		PrintWriter out = resp.getWriter();
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
		out.close();
		out = null;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");

		PrintWriter out = resp.getWriter();
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			String respXml = CoreService.processRequest(req);
			out.print(respXml);
		}
		out.close();
		out = null;
	}

}
