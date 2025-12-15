// BibliotecaService.java
package com.service;

import com.dao.*;
import com.model.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class BibliotecaService {
    private final LibroDAO libroDAO;
    private final MiembroDAO miembroDAO;
    private final PrestamoDAO prestamoDAO;
    private final UsuarioDAO usuarioDAO;
    
    private static final int DIAS_PRESTAMO_DEFAULT = 14;
    private static final double MULTA_POR_DIA = 1.0; // 1€ por día de retraso

    public BibliotecaService() {
        this.libroDAO = new LibroDAO();
        this.miembroDAO = new MiembroDAO();
        this.prestamoDAO = new PrestamoDAO();
        this.usuarioDAO = new UsuarioDAO();
    }

    // Realizar préstamo
    public boolean realizarPrestamo(int idLibro, int idMiembro, int idUsuario) throws SQLException {
        return realizarPrestamo(idLibro, idMiembro, idUsuario, DIAS_PRESTAMO_DEFAULT);
    }

    public boolean realizarPrestamo(int idLibro, int idMiembro, int idUsuario, int diasPrestamo) 
            throws SQLException {
        
        // Validar que el libro esté disponible
        Libro libro = libroDAO.obtenerPorId(idLibro);
        if (libro == null || !libro.estaDisponible()) {
            System.out.println("Error: El libro no está disponible");
            return false;
        }

        // Validar que el miembro esté activo
        Miembro miembro = miembroDAO.obtenerPorId(idMiembro);
        if (miembro == null || miembro.getEstado() != Miembro.EstadoMiembro.ACTIVO) {
            System.out.println("Error: El miembro no está activo");
            return false;
        }

        // Crear el préstamo
        Prestamo prestamo = new Prestamo(idLibro, idMiembro, idUsuario, diasPrestamo);
        boolean resultado = prestamoDAO.crear(prestamo);

        if (resultado) {
            System.out.println("Préstamo realizado exitosamente");
            System.out.println("Fecha de devolución: " + prestamo.getFechaDevolucionEsperada());
        }

        return resultado;
    }

    // Registrar devolución
    public boolean registrarDevolucion(int idPrestamo) throws SQLException {
        Prestamo prestamo = prestamoDAO.obtenerPorId(idPrestamo);
        
        if (prestamo == null) {
            System.out.println("Error: Préstamo no encontrado");
            return false;
        }

        if (prestamo.getEstado() == Prestamo.EstadoPrestamo.DEVUELTO) {
            System.out.println("Error: El préstamo ya fue devuelto");
            return false;
        }

        // Registrar devolución
        boolean resultado = prestamoDAO.registrarDevolucion(idPrestamo);

        if (resultado) {
            // Calcular multa si hay retraso
            if (prestamo.estaAtrasado()) {
                int diasAtraso = prestamo.getDiasAtraso();
                double multa = diasAtraso * MULTA_POR_DIA;
                
                Miembro miembro = miembroDAO.obtenerPorId(prestamo.getIdMiembro());
                miembro.setMultaAcumulada(miembro.getMultaAcumulada() + multa);
                miembroDAO.actualizar(miembro);
                
                System.out.println("Devolución con retraso de " + diasAtraso + " días");
                System.out.println("Multa generada: €" + multa);
            } else {
                System.out.println("Devolución registrada exitosamente");
            }
        }

        return resultado;
    }

    // Buscar libros disponibles
    public List<Libro> buscarLibrosDisponibles() throws SQLException {
        return libroDAO.obtenerDisponibles();
    }

    // Buscar libros por título
    public List<Libro> buscarLibrosPorTitulo(String titulo) throws SQLException {
        return libroDAO.buscarPorTitulo(titulo);
    }
    
    // Buscar libro por ISBN
    public Libro buscarLibroPorIsbn(String isbn) throws SQLException {
        List<Libro> libros = libroDAO.obtenerTodos();
        for (Libro libro : libros) {
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }

    // Obtener préstamos activos
    public List<Prestamo> obtenerPrestamosActivos() throws SQLException {
        return prestamoDAO.obtenerActivos();
    }

    // Obtener préstamos atrasados
    public List<Prestamo> obtenerPrestamosAtrasados() throws SQLException {
        return prestamoDAO.obtenerAtrasados();
    }

    // Obtener historial de préstamos de un miembro
    public List<Prestamo> obtenerHistorialMiembro(int idMiembro) throws SQLException {
        return prestamoDAO.obtenerPorMiembro(idMiembro);
    }

    // Actualizar estados de préstamos atrasados
    public void actualizarEstadosPrestamos() throws SQLException {
        prestamoDAO.actualizarEstadosAtrasados();
        System.out.println("Estados de préstamos actualizados");
    }

    // Autenticar usuario
    public Usuario autenticarUsuario(String username, String password) throws SQLException {
        return usuarioDAO.autenticar(username, password);
    }
}
