package com.database;
import java.sql.*;

public class DatabaseConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/biblioteca_db";
	private static final String USER = "root";
	private static final String PASSWORD = "";
	
	
	public static Connection conectar() {
		
		Connection con=null;
		
		try {
			
			con=DriverManager.getConnection(URL,USER, PASSWORD);
			System.out.println("Conexion exitosa");
			
		}catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
		return con;
	}
}


