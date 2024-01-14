package com.vaccination;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDatabase {

	 public static Connection getConnection() throws SQLException
	 {
		Connection con; 
		//Class.forName("com.mysql.cj.jdbc.Driver");
		String url="jdbc:mysql://localhost:3306/vaccination";
		String userName="root";
		String passWord="root";
		con=DriverManager.getConnection(url,userName,passWord);
		return con;
	 }
//	 public static void main(String[] args) throws SQLException {
//		Connection c=getConnection();
//		System.out.println(c);
//	}
}