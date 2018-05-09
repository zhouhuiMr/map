package per.datasources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class createDataSources {
	private static Connection connec = null;
	
	public Connection getConnection() {
		dataSourcesInit();
		return connec;
	}
	
	/**
	 * 关闭数据库连接
	 * 
	 * @since 2018.5.8
	 * @author Administrator
	 * */
	public void closeConnection() {
		try {
			if(connec != null) {
				connec.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connec = null;
	}
	
	/**
	 * 初始化数据库连接
	 * 
	 * @since 2018.05.08
	 * @author Administrator
	 * */
	private void dataSourcesInit() {
		try {
			Class.forName("com.hxtt.sql.access.AccessDriver");
			String url = "";
			url += "jdbc:Access:///";
			url += dataSourcesConfig.accessURI;
			connec = DriverManager.getConnection(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
