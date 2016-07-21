package com.Ian.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * MySQL数据库操作工具类
 * 
 * @author ian
 * @date 2016-07-20
 */
public class JDBCUtil {

	private static Logger log = LoggerFactory.getLogger(JDBCUtil.class);
	
	/**
	 * 
	 * 获取MySQL数据库连接
	 * 
	 * @param req
	 * @return
	 */
	public Connection getConn() {
		Connection conn = null;
		String url = "jdbc:mysql://127.0.0.1:3306/weixin";
		String username = "root";
		// String password = "123456";
		String password = "N0iEkMZrG1oF";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log.error("connect data base failed");
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("connect data base failed");
		}
		return conn;
	}

	/**
	 * 
	 * 释放JDBC资源
	 * 
	 * @param conn
	 * @param ps
	 * @param re
	 */
	public void releaseResources(Connection conn, PreparedStatement ps, ResultSet rs) {
		if (null != rs) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("result set close failed");
			}
		}
		if (null != ps) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("pre-statement close failed");
			}
		}
		if (null != conn) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("connection close failed");
			}
		}
	}
}
