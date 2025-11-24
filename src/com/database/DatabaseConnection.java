package com.database;

import java.sql.*;

/**
 * Clase encargada de gestionar la conexión a una base de datos MySQL.
 */
public class DatabaseConnection {
    // URL de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca_db";
    private static final String USER = "root";       // Usuario de la base de datos
    private static final String PASSWORD = "";       // Contraseña del usuario
    private static Connection connection = null;     // Instancia única de conexión

    /**
     * Establece la conexión con la base de datos si no existe o si está cerrada.
     * @return Un objeto Connection activo.
     * @throws SQLException si ocurre un error al conectar.
     */
    public static Connection conectar() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Cargar el driver JDBC de MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Intentar conectar con la base de datos
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión exitosa a la base de datos");
            } catch (ClassNotFoundException e) {
                // Error si el driver no se encuentra
                throw new SQLException("Driver MySQL no encontrado", e);
            }
        }
        return connection;
    }

    /**
     * Cierra la conexión activa si existe y no está cerrada.
     */
    public static void desconectar() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión cerrada");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
