package com.dao;

import com.database.DatabaseConnection;
import com.model.Libro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    // CREATE
    public boolean crear(Libro libro) throws SQLException {
        String sql = "INSERT INTO libros (isbn, titulo, id_autor, id_categoria, editorial, " +
                     "anio_publicacion, numero_paginas, idioma, cantidad_total, cantidad_disponible, " +
                     "ubicacion, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, libro.getIsbn());
            stmt.setString(2, libro.getTitulo());
            stmt.setInt(3, libro.getIdAutor());
            stmt.setInt(4, libro.getIdCategoria());
            stmt.setString(5, libro.getEditorial());
            stmt.setInt(6, libro.getAnioPublicacion());
            stmt.setInt(7, libro.getNumeroPaginas());
            stmt.setString(8, libro.getIdioma());
            stmt.setInt(9, libro.getCantidadTotal());
            stmt.setInt(10, libro.getCantidadDisponible());
            stmt.setString(11, libro.getUbicacion());
            stmt.setString(12, libro.getEstado().name());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    libro.setIdLibro(rs.getInt(1));
                }
                return true;
            }
        }
        return false;
    }

    // READ - Obtener por ID
    public Libro obtenerPorId(int id) throws SQLException {
        String sql = "SELECT l.*, CONCAT(a.nombre, ' ', a.apellido) as nombre_autor, " +
                     "c.nombre as nombre_categoria " +
                     "FROM libros l " +
                     "LEFT JOIN autores a ON l.id_autor = a.id_autor " +
                     "LEFT JOIN categorias c ON l.id_categoria = c.id_categoria " +
                     "WHERE l.id_libro = ?";
        
        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearLibro(rs);
            }
        }
        return null;
    }

    // READ - Obtener todos
    public List<Libro> obtenerTodos() throws SQLException {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT l.*, CONCAT(a.nombre, ' ', a.apellido) as nombre_autor, " +
                     "c.nombre as nombre_categoria " +
                     "FROM libros l " +
                     "LEFT JOIN autores a ON l.id_autor = a.id_autor " +
                     "LEFT JOIN categorias c ON l.id_categoria = c.id_categoria " +
                     "ORDER BY l.titulo";
        
        try (Connection conn = DatabaseConnection.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                libros.add(mapearLibro(rs));
            }
        }
        return libros;
    }

    // Buscar libros por título
    public List<Libro> buscarPorTitulo(String titulo) throws SQLException {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT l.*, CONCAT(a.nombre, ' ', a.apellido) as nombre_autor, " +
                     "c.nombre as nombre_categoria " +
                     "FROM libros l " +
                     "LEFT JOIN autores a ON l.id_autor = a.id_autor " +
                     "LEFT JOIN categorias c ON l.id_categoria = c.id_categoria " +
                     "WHERE l.titulo LIKE ? ORDER BY l.titulo";
        
        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + titulo + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                libros.add(mapearLibro(rs));
            }
        }
        return libros;
    }

    // Obtener libros disponibles
    public List<Libro> obtenerDisponibles() throws SQLException {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT l.*, CONCAT(a.nombre, ' ', a.apellido) as nombre_autor, " +
                     "c.nombre as nombre_categoria " +
                     "FROM libros l " +
                     "LEFT JOIN autores a ON l.id_autor = a.id_autor " +
                     "LEFT JOIN categorias c ON l.id_categoria = c.id_categoria " +
                     "WHERE l.cantidad_disponible > 0 ORDER BY l.titulo";
        
        try (Connection conn = DatabaseConnection.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                libros.add(mapearLibro(rs));
            }
        }
        return libros;
    }

    // UPDATE
    public boolean actualizar(Libro libro) throws SQLException {
        String sql = "UPDATE libros SET isbn = ?, titulo = ?, id_autor = ?, id_categoria = ?, " +
                     "editorial = ?, anio_publicacion = ?, numero_paginas = ?, idioma = ?, " +
                     "cantidad_total = ?, cantidad_disponible = ?, ubicacion = ?, estado = ? " +
                     "WHERE id_libro = ?";
        
        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, libro.getIsbn());
            stmt.setString(2, libro.getTitulo());
            stmt.setInt(3, libro.getIdAutor());
            stmt.setInt(4, libro.getIdCategoria());
            stmt.setString(5, libro.getEditorial());
            stmt.setInt(6, libro.getAnioPublicacion());
            stmt.setInt(7, libro.getNumeroPaginas());
            stmt.setString(8, libro.getIdioma());
            stmt.setInt(9, libro.getCantidadTotal());
            stmt.setInt(10, libro.getCantidadDisponible());
            stmt.setString(11, libro.getUbicacion());
            stmt.setString(12, libro.getEstado().name());
            stmt.setInt(13, libro.getIdLibro());
            
            return stmt.executeUpdate() > 0;
        }
    }

    // DELETE
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM libros WHERE id_libro = ?";
        
        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    private Libro mapearLibro(ResultSet rs) throws SQLException {
        Libro libro = new Libro();
        libro.setIdLibro(rs.getInt("id_libro"));
        libro.setIsbn(rs.getString("isbn"));
        libro.setTitulo(rs.getString("titulo"));
        libro.setIdAutor(rs.getInt("id_autor"));
        libro.setNombreAutor(rs.getString("nombre_autor"));
        libro.setIdCategoria(rs.getInt("id_categoria"));
        libro.setNombreCategoria(rs.getString("nombre_categoria"));
        libro.setEditorial(rs.getString("editorial"));
        libro.setAnioPublicacion(rs.getInt("anio_publicacion"));
        libro.setNumeroPaginas(rs.getInt("numero_paginas"));
        libro.setIdioma(rs.getString("idioma"));
        libro.setCantidadTotal(rs.getInt("cantidad_total"));
        libro.setCantidadDisponible(rs.getInt("cantidad_disponible"));
        libro.setUbicacion(rs.getString("ubicacion"));
        libro.setEstado(Libro.EstadoLibro.valueOf(rs.getString("estado")));
        return libro;
    }
}
