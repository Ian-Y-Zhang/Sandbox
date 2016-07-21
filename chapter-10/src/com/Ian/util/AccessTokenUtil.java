package com.Ian.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.Ian.pojo.AccessToken;

/**
 * 
 * Access_token表操作类
 * 
 * @author ian
 * @date 2016-07-20
 *
 */
public class AccessTokenUtil {

	private static Logger log = LoggerFactory.getLogger(AccessTokenUtil.class);
	
	/**
	 * 
	 * 增加access token及有效截止时间
	 * 
	 * @param oAccessToken
	 * @return
	 */
	public static int saveToken(AccessToken oAccessToken) {
		int result = 0;
		JDBCUtil jdbcUtil = new JDBCUtil();
		String sql = "insert into access_token(APP_ID, APP_SECRET, ACCESS_TOKEN, EXPIRE_TIME) values(?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = jdbcUtil.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, oAccessToken.getAppId());
			ps.setString(2, oAccessToken.getAppSecret());
			ps.setString(3, oAccessToken.getToken());
			ps.setString(4, oAccessToken.getExpireTime());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("insert access_token failed");
		} finally {
			jdbcUtil.releaseResources(conn, ps, null);
		}
		return result;
	}

	/**
	 * 
	 * 更新access token及有效截止时间
	 * 
	 * @param oAccessToken
	 * @return
	 */
	public static int updateToken(AccessToken oAccessToken) {
		int result = 0;
		JDBCUtil jdbcUtil = new JDBCUtil();
		String sql = "update access_token set ACCESS_TOKEN = ?, EXPIRE_TIME = ? where APP_ID = ? and APP_SECRET = ?";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = jdbcUtil.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, oAccessToken.getToken());
			ps.setString(2, oAccessToken.getExpireTime());
			ps.setString(3, oAccessToken.getAppId());
			ps.setString(4, oAccessToken.getAppSecret());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("update access_token failed");
		} finally {
			jdbcUtil.releaseResources(conn, ps, null);
		}
		return result;
	}

	/**
	 * 
	 * 获取access token及有效截止时间
	 * 
	 * @param oAccessToken
	 * @return
	 */
	public static AccessToken getToken(AccessToken oAccessToken) {
		AccessToken accessToken = null;
		JDBCUtil jdbcUtil = new JDBCUtil();
		String sql = "select * from access_token where APP_ID = ? and APP_SECRET = ? limit 0,1";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = jdbcUtil.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, oAccessToken.getAppId());
			ps.setString(2, oAccessToken.getAppSecret());
			rs = ps.executeQuery();
			if (rs.next()) {
				accessToken = new AccessToken();
				accessToken.setAppId(rs.getString("APP_ID"));
				accessToken.setAppSecret(rs.getString("APP_SECRET"));
				accessToken.setToken(rs.getString("ACCESS_TOKEN"));
				accessToken.setExpireTime(rs.getString("EXPIRE_TIME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("select access_token failed");
		} finally {
			jdbcUtil.releaseResources(conn, ps, rs);
		}
		return accessToken;
	}
}
