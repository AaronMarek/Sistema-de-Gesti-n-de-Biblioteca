package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca_db?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL Connector/J 8+
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver no encontrado. Asegúrate de tener el connector en el classpath.");
            e.printStackTrace();
        }
    }

    public static Connection conectar() throws SQLException {
        Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("conexion exitosa");
    	return con;
    }
}
