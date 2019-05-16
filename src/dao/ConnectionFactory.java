package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	static Connection createConnection() throws SQLException{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/javasystem?"
					+ "autoReconnect=true&useSSL=false", // この辺は自環境で再現する時怪しい気がする・・・
	        		"root","mySql108yen");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}

	}

}
