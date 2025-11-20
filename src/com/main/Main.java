package com.main;
import java.sql.SQLException;

import com.database.DatabaseConnection;


class Main {
	public static void main(String[] args) {
		DatabaseConnection dbc = new DatabaseConnection();
		try {
			dbc.conectar();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
