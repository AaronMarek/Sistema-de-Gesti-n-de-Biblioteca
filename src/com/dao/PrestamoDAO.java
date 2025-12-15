// PrestamoDAO.java
package com.dao;

import com.database.DatabaseConnection;
import com.model.Prestamo;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    // CREATE - Registrar nuevo préstamo
    public boolean crear(Prestamo prestamo) throws SQLException {
        String sql = "INSERT INTO prestamos (id_libro, id_miembro, id_usuario, fecha_prestamo, " +
                     "fecha_devolucion_esperada, estado, observaciones) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, prestamo.getIdLibro());
            stmt.setInt(2, prestamo.getIdMiembro());
            stmt.setInt(3, prestamo.getIdUsuario());
            stmt.setDate(4, Date.valueOf(prestamo.getFechaPrestamo()));
            stmt.setDate(5, Date.valueOf(prestamo.getFechaDevolucionEsperada()));
            stmt.setString(6, prestamo.getEstado().name());
            stmt.setString(7, prestamo.getObservaciones());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    prestamo.setIdPrestamo(rs.getInt(1));
                }
                return true;
            }
        }
        return false;
    }

    // READ - Obtener por ID
    public Prestamo obtenerPorId(int id) throws SQLException {
        String sql = "SELECT p.*, l.titulo as titulo_libro, " +
                     "CONCAT(m.nombre, ' ', m.apellido) as nombre_miembro " +
                     "FROM prestamos p " +
                     "JOIN libros l ON p.id_libro = l.id_libro " +
                     "JOIN miembros m ON p.id_miembro = m.id_miembro " +
                     "WHERE p.id_prestamo = ?";
        
        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearPrestamo(rs);
            }
        }
        return null;
    }

    // READ - Obtener préstamos activos
    public List<Prestamo> obtenerActivos() throws SQLException {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT p.*, l.titulo as titulo_libro, " +
                     "CONCAT(m.nombre, ' ', m.apellido) as nombre_miembro " +
                     "FROM prestamos p " +
                     "JOIN libros l ON p.id_libro = l.id_libro " +
                     "JOIN miembros m ON p.id_miembro = m.id_miembro " +
                     "WHERE p.estado = 'ACTIVO' OR p.estado = 'ATRASADO' " +
                     "ORDER BY p.fecha_devolucion_esperada";
        
        try (Connection conn = DatabaseConnection.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                prestamos.add(mapearPrestamo(rs));
            }
        }
        return prestamos;
    }

    // Obtener préstamos por miembro
    public List<Prestamo> obtenerPorMiembro(int idMiembro) throws SQLException {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT p.*, l.titulo as titulo_libro, " +
                     "CONCAT(m.nombre, ' ', m.apellido) as nombre_miembro " +
                     "FROM prestamos p " +
                     "JOIN libros l ON p.id_libro = l.id_libro " +
                     "JOIN miembros m ON p.id_miembro = m.id_miembro " +
                     "WHERE p.id_miembro = ? " +
                     "ORDER BY p.fecha_prestamo DESC";
        
        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idMiembro);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                prestamos.add(mapearPrestamo(rs));
            }
        }
        return prestamos;
    }

    // Obtener préstamos atrasados
    public List<Prestamo> obtenerAtrasados() throws SQLException {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT p.*, l.titulo as titulo_libro, " +
                     "CONCAT(m.nombre, ' ', m.apellido) as nombre_miembro " +
                     "FROM prestamos p " +
                     "JOIN libros l ON p.id_libro = l.id_libro " +
                     "JOIN miembros m ON p.id_miembro = m.id_miembro " +
                     "WHERE p.estado IN ('ACTIVO', 'ATRASADO') " +
                     "AND p.fecha_devolucion_esperada < CURDATE() " +
                     "ORDER BY p.fecha_devolucion_esperada";
        
        try (Connection conn = DatabaseConnection.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                prestamos.add(mapearPrestamo(rs));
            }
        }
        return prestamos;
    }

    // UPDATE - Registrar devolución
    public boolean registrarDevolucion(int idPrestamo) throws SQLException {
        String sql = "UPDATE prestamos SET fecha_devolucion_real = ?, estado = 'DEVUELTO' " +
                     "WHERE id_prestamo = ?";
        
        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, Date.valueOf(LocalDate.now()));
            stmt.setInt(2, idPrestamo);
            
            return stmt.executeUpdate() > 0;
        }
    }

    // UPDATE - Actualizar estado
    public boolean actualizarEstado(int idPrestamo, Prestamo.EstadoPrestamo nuevoEstado) throws SQLException {
        String sql = "UPDATE prestamos SET estado = ? WHERE id_prestamo = ?";
        
        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nuevoEstado.name());
            stmt.setInt(2, idPrestamo);
            
            return stmt.executeUpdate() > 0;
        }
    }

    // Actualizar estados de préstamos atrasados
    public void actualizarEstadosAtrasados() throws SQLException {
        String sql = "UPDATE prestamos SET estado = 'ATRASADO' " +
                     "WHERE estado = 'ACTIVO' AND fecha_devolucion_esperada < CURDATE()";
        
        try (Connection conn = DatabaseConnection.conectar();
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate(sql);
        }
    }

    private Prestamo mapearPrestamo(ResultSet rs) throws SQLException {
        Prestamo prestamo = new Prestamo();
        prestamo.setIdPrestamo(rs.getInt("id_prestamo"));
        prestamo.setIdLibro(rs.getInt("id_libro"));
        prestamo.setTituloLibro(rs.getString("titulo_libro"));
        prestamo.setIdMiembro(rs.getInt("id_miembro"));
        prestamo.setNombreMiembro(rs.getString("nombre_miembro"));
        prestamo.setIdUsuario(rs.getInt("id_usuario"));
        prestamo.setFechaPrestamo(rs.getDate("fecha_prestamo").toLocalDate());
        prestamo.setFechaDevolucionEsperada(rs.getDate("fecha_devolucion_esperada").toLocalDate());
        
        Date fechaDevReal = rs.getDate("fecha_devolucion_real");
        if (fechaDevReal != null) {
            prestamo.setFechaDevolucionReal(fechaDevReal.toLocalDate());
        }
        
        prestamo.setEstado(Prestamo.EstadoPrestamo.valueOf(rs.getString("estado")));
        prestamo.setObservaciones(rs.getString("observaciones"));
        return prestamo;
    }
}