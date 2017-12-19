package ch01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionMaker {
	public Connection getConnection() throws ClassNotFoundException, SQLException { 
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost/spring_db?autoReconnect=true&useSSL=false","id","password");
	
		return c;
	}
}
