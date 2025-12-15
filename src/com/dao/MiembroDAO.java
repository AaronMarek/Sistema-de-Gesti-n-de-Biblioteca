package com.dao;

import com.database.DatabaseConnection;
import com.model.Miembro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MiembroDAO {

    // CREATE
    public boolean crear(Miembro miembro) throws SQLException {
        String sql = "INSERT INTO miembros (nombre, apellido, dni, telefono, email, direccion, " +
                     "fecha_inscripcion, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, miembro.getNombre());
            stmt.setString(2, miembro.getApellido());
            stmt.setString(3, miembro.getDni());
            stmt.setString(4, miembro.getTelefono());
            stmt.setString(5, miembro.getEmail());
            stmt.setString(6, miembro.getDireccion());
            stmt.setDate(7, Date.valueOf(miembro.getFechaInscripcion()));
            stmt.setString(8, miembro.getEstado().name());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    miembro.setIdMiembro(rs.getInt(1));
                }
                return true;
            }
        }
        return false;
    }

    // READ
    public Miembro obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM miembros WHERE id_miembro = ?";
        
        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearMiembro(rs);
            }
        }
        return null;
    }

    public List<Miembro> obtenerTodos() throws SQLException {
        List<Miembro> miembros = new ArrayList<>();
        String sql = "SELECT * FROM miembros ORDER BY apellido, nombre";
        
        try (Connection conn = DatabaseConnection.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                miembros.add(mapearMiembro(rs));
            }
        }
        return miembros;
    }

    // Buscar por DNI
    public Miembro buscarPorDni(String dni) throws SQLException {
        String sql = "SELECT * FROM miembros WHERE dni = ?";
        
        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearMiembro(rs);
            }
        }
        return null;
    }

    // UPDATE
    public boolean actualizar(Miembro miembro) throws SQLException {
        String sql = "UPDATE miembros SET nombre = ?, apellido = ?, dni = ?, telefono = ?, " +
                     "email = ?, direccion = ?, estado = ?, multa_acumulada = ? WHERE id_miembro = ?";
        
        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, miembro.getNombre());
            stmt.setString(2, miembro.getApellido());
            stmt.setString(3, miembro.getDni());
            stmt.setString(4, miembro.getTelefono());
            stmt.setString(5, miembro.getEmail());
            stmt.setString(6, miembro.getDireccion());
            stmt.setString(7, miembro.getEstado().name());
            stmt.setDouble(8, miembro.getMultaAcumulada());
            stmt.setInt(9, miembro.getIdMiembro());
            
            return stmt.executeUpdate() > 0;
        }
    }

    // DELETE
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM miembros WHERE id_miembro = ?";
        
        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    private Miembro mapearMiembro(ResultSet rs) throws SQLException {
        Miembro miembro = new Miembro();
        miembro.setIdMiembro(rs.getInt("id_miembro"));
        miembro.setNombre(rs.getString("nombre"));
        miembro.setApellido(rs.getString("apellido"));
        miembro.setDni(rs.getString("dni"));
        miembro.setTelefono(rs.getString("telefono"));
        miembro.setEmail(rs.getString("email"));
        miembro.setDireccion(rs.getString("direccion"));
        miembro.setFechaInscripcion(rs.getDate("fecha_inscripcion").toLocalDate());
        miembro.setEstado(Miembro.EstadoMiembro.valueOf(rs.getString("estado")));
        miembro.setMultaAcumulada(rs.getDouble("multa_acumulada"));
        return miembro;
    }
}