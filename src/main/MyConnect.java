package main;

import java.sql.*;

public class MyConnect {
	public static Connection getConnection() {
		try {
			Class.forName(com.mysql.cj.jdbc.Driver.class.getName());
			return DriverManager.getConnection(
					"jdbc:mysql://localhost/homeisolation?useUnicode=true&charecterEncoding=uft-8",
					"root", "Tonmysql04");
			} 
			catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			}
		return null;
	}
}
