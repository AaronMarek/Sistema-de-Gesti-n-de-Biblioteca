package com.main;

import com.dao.LibroDAO;
import com.dao.MiembroDAO;
import com.dao.UsuarioDAO;
import com.database.DatabaseConnection;
import com.model.Libro;
import com.model.Miembro;
import com.model.Usuario;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Interfaz de consola principal de la aplicación.
 * Diseñada para trabajar con los DAOs existentes (UsuarioDAO, LibroDAO, MiembroDAO).
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    // DAOs (instanciados una vez para reusar)
    private static final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static final LibroDAO libroDAO = new LibroDAO();
    private static final MiembroDAO miembroDAO = new MiembroDAO();

    private static Usuario usuarioLogueado = null;

    public static void main(String[] args) {
        System.out.println("==== Sistema de Gestión de Biblioteca ====");
        
        // Intento simple de probar conexión al iniciar
        try {
            DatabaseConnection.conectar().close();
            System.out.println("Base de datos: conexión OK");
        } catch (SQLException e) {
            System.err.println("No se pudo conectar a la base de datos: " + e.getMessage());
        }

        boolean salir = false;
        while (!salir) {
            mostrarMenuPrincipal();
            String opcion = scanner.nextLine().trim();
            switch (opcion) {
                case "1":
                    opcionLogin();
                    break;
                case "2":
                    listarLibros();
                    break;
                case "3":
                    verLibroPorId();
                    break;
                case "4":
                    listarMiembros();
                    break;
                case "5":
                    crearMiembro();
                    break;
                case "6":
                    buscarLibroPorTitulo();
                    break;
                case "7":
                    actualizarMiembro();
                    break;
                case "0":
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }

        System.out.println("Aplicación finalizada. ¡Hasta luego!");
    }

    private static void mostrarMenuPrincipal() {
        System.out.println();
        System.out.println("=== Menú Principal ===");
        if (usuarioLogueado != null) {
            System.out.println("Usuario: " + usuarioLogueado.getNombreCompleto() + " (" + usuarioLogueado.getRol() + ")");
        }
        System.out.println("1) Iniciar sesión");
        System.out.println("2) Listar libros");
        System.out.println("3) Ver libro por ID");
        System.out.println("4) Listar miembros");
        System.out.println("5) Crear miembro");
        System.out.println("6) Buscar libro por título");
        System.out.println("7) Actualizar miembro");
        System.out.println("0) Salir");
        System.out.print("Elige una opción: ");
    }

    private static void opcionLogin() {
        System.out.print("Usuario: ");
        String user = scanner.nextLine().trim();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine().trim();

        try {
            Usuario u = usuarioDAO.autenticar(user, pass);
            if (u != null) {
                usuarioLogueado = u;
                System.out.println("✓ Login correcto. Bienvenido " + u.getNombreCompleto() + " (" + u.getRol() + ")");
            } else {
                System.out.println("✗ Usuario o contraseña incorrectos.");
            }
        } catch (SQLException e) {
            System.err.println("Error comprobando credenciales: " + e.getMessage());
        }
    }

    private static void listarLibros() {
        try {
            List<Libro> libros = libroDAO.obtenerTodos();
            if (libros == null || libros.isEmpty()) {
                System.out.println("No hay libros registrados.");
                return;
            }
            System.out.println("\n=== Lista de Libros ===");
            System.out.println(String.format("%-5s %-40s %-25s %-15s %-12s", 
                "ID", "Título", "Autor", "Editorial", "Disponibles"));
            System.out.println("─".repeat(100));
            
            for (Libro l : libros) {
                System.out.printf("%-5d %-40s %-25s %-15s %d/%d%n",
                    l.getIdLibro(), 
                    truncar(l.getTitulo(), 38), 
                    truncar(l.getNombreAutor(), 23),
                    truncar(l.getEditorial(), 13),
                    l.getCantidadDisponible(),
                    l.getCantidadTotal());
            }
        } catch (SQLException e) {
            System.err.println("Error al listar libros: " + e.getMessage());
        }
    }

    private static void verLibroPorId() {
        try {
            System.out.print("ID del libro: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            Libro libro = libroDAO.obtenerPorId(id);
            
            if (libro == null) {
                System.out.println("✗ Libro no encontrado con ID " + id);
                return;
            }
            
            System.out.println("\n=== Detalle del Libro ===");
            System.out.println("ID:              " + libro.getIdLibro());
            System.out.println("ISBN:            " + libro.getIsbn());
            System.out.println("Título:          " + libro.getTitulo());
            System.out.println("Autor:           " + libro.getNombreAutor());
            System.out.println("Categoría:       " + libro.getNombreCategoria());
            System.out.println("Editorial:       " + libro.getEditorial());
            System.out.println("Año:             " + libro.getAnioPublicacion());
            System.out.println("Páginas:         " + libro.getNumeroPaginas());
            System.out.println("Idioma:          " + libro.getIdioma());
            System.out.println("Total:           " + libro.getCantidadTotal());
            System.out.println("Disponibles:     " + libro.getCantidadDisponible());
            System.out.println("Ubicación:       " + libro.getUbicacion());
            System.out.println("Estado:          " + libro.getEstado());
            
        } catch (NumberFormatException nfe) {
            System.out.println("✗ ID inválido. Debe ser un número.");
        } catch (SQLException e) {
            System.err.println("Error al obtener libro: " + e.getMessage());
        }
    }

    private static void listarMiembros() {
        try {
            List<Miembro> miembros = miembroDAO.obtenerTodos();
            if (miembros == null || miembros.isEmpty()) {
                System.out.println("No hay miembros registrados.");
                return;
            }
            
            System.out.println("\n=== Lista de Miembros ===");
            System.out.println(String.format("%-5s %-30s %-15s %-30s %-15s", 
                "ID", "Nombre Completo", "DNI", "Email", "Estado"));
            System.out.println("─".repeat(100));
            
            for (Miembro m : miembros) {
                String nombreCompleto = m.getNombre() + " " + m.getApellido();
                System.out.printf("%-5d %-30s %-15s %-30s %-15s%n",
                    m.getIdMiembro(), 
                    truncar(nombreCompleto, 28),
                    m.getDni(),
                    truncar(m.getEmail(), 28),
                    m.getEstado());
            }
        } catch (SQLException e) {
            System.err.println("Error al listar miembros: " + e.getMessage());
        }
    }

    private static void crearMiembro() {
        try {
            System.out.println("\n=== Crear Nuevo Miembro ===");
            
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine().trim();
            
            System.out.print("Apellido: ");
            String apellido = scanner.nextLine().trim();
            
            System.out.print("DNI: ");
            String dni = scanner.nextLine().trim();
            
            System.out.print("Teléfono: ");
            String telefono = scanner.nextLine().trim();
            
            System.out.print("Email: ");
            String email = scanner.nextLine().trim();
            
            System.out.print("Dirección: ");
            String direccion = scanner.nextLine().trim();
            
            // Crear objeto Miembro con todos los campos requeridos
            Miembro m = new Miembro();
            m.setNombre(nombre);
            m.setApellido(apellido);
            m.setDni(dni);
            m.setTelefono(telefono);
            m.setEmail(email);
            m.setDireccion(direccion);
            m.setFechaInscripcion(LocalDate.now());
            m.setEstado(Miembro.EstadoMiembro.ACTIVO);
            m.setMultaAcumulada(0.0);
            
            boolean ok = miembroDAO.crear(m);
            if (ok) {
                System.out.println("✓ Miembro creado correctamente con ID: " + m.getIdMiembro());
            } else {
                System.out.println("✗ No se pudo crear el miembro.");
            }
        } catch (SQLException e) {
            System.err.println("Error al crear miembro: " + e.getMessage());
        }
    }

    private static void buscarLibroPorTitulo() {
        try {
            System.out.print("Ingrese título a buscar: ");
            String titulo = scanner.nextLine().trim();
            
            List<Libro> libros = libroDAO.buscarPorTitulo(titulo);
            
            if (libros == null || libros.isEmpty()) {
                System.out.println("No se encontraron libros con ese título.");
                return;
            }
            
            System.out.println("\n=== Resultados de Búsqueda ===");
            System.out.println(String.format("%-5s %-40s %-25s %-12s", 
                "ID", "Título", "Autor", "Disponibles"));
            System.out.println("─".repeat(85));
            
            for (Libro l : libros) {
                System.out.printf("%-5d %-40s %-25s %d/%d%n",
                    l.getIdLibro(), 
                    truncar(l.getTitulo(), 38),
                    truncar(l.getNombreAutor(), 23),
                    l.getCantidadDisponible(),
                    l.getCantidadTotal());
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar libros: " + e.getMessage());
        }
    }

    private static void actualizarMiembro() {
        try {
            System.out.print("ID del miembro a actualizar: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            
            Miembro m = miembroDAO.obtenerPorId(id);
            if (m == null) {
                System.out.println("✗ Miembro no encontrado con ID " + id);
                return;
            }
            
            System.out.println("\n=== Actualizar Miembro ===");
            System.out.println("Nombre actual: " + m.getNombre());
            System.out.print("Nuevo nombre (Enter para mantener): ");
            String nombre = scanner.nextLine().trim();
            if (!nombre.isEmpty()) {
                m.setNombre(nombre);
            }
            
            System.out.println("Email actual: " + m.getEmail());
            System.out.print("Nuevo email (Enter para mantener): ");
            String email = scanner.nextLine().trim();
            if (!email.isEmpty()) {
                m.setEmail(email);
            }
            
            boolean ok = miembroDAO.actualizar(m);
            if (ok) {
                System.out.println("✓ Miembro actualizado correctamente.");
            } else {
                System.out.println("✗ No se pudo actualizar el miembro.");
            }
            
        } catch (NumberFormatException nfe) {
            System.out.println("✗ ID inválido. Debe ser un número.");
        } catch (SQLException e) {
            System.err.println("Error al actualizar miembro: " + e.getMessage());
        }
    }

    // Método auxiliar para truncar texto
    private static String truncar(String texto, int longitud) {
        if (texto == null) return "";
        if (texto.length() <= longitud) return texto;
        return texto.substring(0, longitud - 2) + "..";
    }
}
