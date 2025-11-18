package com.Main;
import com.database.DatabaseConnection;


class Main {
	public static void main(String[] args) {
		DatabaseConnection dbc = new DatabaseConnection();
		dbc.conectar();
	}

}
