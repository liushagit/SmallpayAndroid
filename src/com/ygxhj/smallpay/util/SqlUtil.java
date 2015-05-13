package com.ygxhj.smallpay.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlUtil {

	private static final String DRIVER_MYSQL_NAME = "com.mysql.jdbc.Driver";
	private static final String default_url = "jdbc:mysql://xxx:xxx/smallpay_res?useUnicode=true&characterEncoding=UTF-8";
	private static final String default_user = "xx";
	private static final String default_password = "xxx";
	private static Connection conn = null;
	
	public static Connection getConnectionDefault(){
		return getConnectionMySql(default_url, default_user, default_password);
	}
	public static Connection getConnectionMySql(String url, String user,
			String password) {
		try {

			Class.forName(DRIVER_MYSQL_NAME);
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static <T> T query(String sql, DBHandler<T> handler, Object... args) {
		T t = null;
		if(!isConnect()){
			return t;
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(sql);
			setArgs(ps, args);
			rs = ps.executeQuery();
			if (rs != null) {
				t = handler.handler(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		
		return t;
	}
	
	private static void setArgs(PreparedStatement ps, Object... args) throws SQLException{
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
		}
	}
	
	
	private static boolean isConnect(){
		try {
			if (conn == null || conn.isClosed()) {
				getConnectionDefault();
			}
		} catch (SQLException e1) {
			getConnectionDefault();
		}
		
		try {
			if (conn == null || conn.isClosed()) {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
}
