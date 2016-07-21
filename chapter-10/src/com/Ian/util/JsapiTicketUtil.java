package com.Ian.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.Ian.pojo.JsapiTicket;

/**
 * 
 * jsapi_ticket表操作类
 * 
 * @author ian
 * @date 2016-07-20
 *
 */
public class JsapiTicketUtil {

	private static Logger log = LoggerFactory.getLogger(JsapiTicketUtil.class);
	
	/**
	 * 
	 * 增加jsapi ticket及有效截止时间
	 * 
	 * @param oJsapiTicket
	 * @return
	 */
	public static int saveTicket(JsapiTicket oJsapiTicket) {
		int result = 0;
		JDBCUtil jdbcUtil = new JDBCUtil();
		String sql = "insert into jsapi_ticket(ACCESS_TOKEN, JSAPI_TICKET, EXPIRE_TIME) values(?, ?, ?)";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = jdbcUtil.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, oJsapiTicket.getToken());
			ps.setString(2, oJsapiTicket.getTicket());
			ps.setString(3, oJsapiTicket.getExpireTime());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("insert jsapi_ticket failed");
		} finally {
			jdbcUtil.releaseResources(conn, ps, null);
		}
		return result;
	}
	
	/**
	 * 
	 * 更新jsapi ticket及有效截止时间
	 * 
	 * @param oJsapiTicket
	 * @return
	 */
	public static int updateTicket(JsapiTicket oJsapiTicket) {
		int result = 0;
		JDBCUtil jdbcUtil = new JDBCUtil();
		String sql = "update jsapi_ticket set JSAPI_TICKET = ?, EXPIRE_TIME = ? where ACCESS_TOKEN = ?";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = jdbcUtil.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, oJsapiTicket.getTicket());
			ps.setString(2, oJsapiTicket.getExpireTime());
			ps.setString(3, oJsapiTicket.getToken());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("update jsapi_ticket failed");
		} finally {
			jdbcUtil.releaseResources(conn, ps, null);
		}
		return result;
	}
	
	/**
	 * 
	 * 获取jsapi ticket及有效截止时间
	 * 
	 * @param oJsapiTicket
	 * @return
	 */
	public static JsapiTicket getTicket(JsapiTicket oJsapiTicket){
		JsapiTicket jsapiTicket = null;
		JDBCUtil jdbcUtil = new JDBCUtil();
		String sql = "select * from jsapi_ticket where ACCESS_TOKEN = ? limit 0,1";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = jdbcUtil.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, oJsapiTicket.getToken());
			rs = ps.executeQuery();
			if (rs.next()) {
				jsapiTicket = new JsapiTicket();
				jsapiTicket.setId(rs.getInt("ID"));
				jsapiTicket.setToken(rs.getString("ACCESS_TOKEN"));
				jsapiTicket.setTicket(rs.getString("JSAPI_TICKET"));
				jsapiTicket.setExpireTime(rs.getString("EXPIRE_TIME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("select jsapi_ticket failed");
		} finally {
			jdbcUtil.releaseResources(conn, ps, rs);
		}
		return jsapiTicket;
	}
}
