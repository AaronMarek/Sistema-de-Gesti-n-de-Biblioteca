package com.main;

import com.database.DatabaseConnection;
import com.model.*;
import com.dao.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static UsuarioDAO usuarioDAO;
    private static Usuario usuarioActual;

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE GESTIÓN (USUARIOS) v1.0          ║");
        System.out.println("╚════════════════════════════════════════════════╝");

        try {
            usuarioDAO = new UsuarioDAO();

            if (autenticarUsuario()) {
                mostrarMenuPrincipal();
            } else {
                System.out.println("Autenticación fallida. Saliendo del sistema...");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DatabaseConnection.desconectar();
            scanner.close();
        }
    }

    private static boolean autenticarUsuario() {
        System.out.println("\n--- AUTENTICACIÓN DE USUARIO ---");
        System.out.print("Usuario: ");
        String username = scanner.nextLine().trim();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine().trim();

        try {
            usuarioActual = usuarioDAO.autenticar(username, password);
            if (usuarioActual != null) {
                System.out.println("\n✓ Bienvenido, " + usuarioActual.getNombreCompleto());
                System.out.println("  Rol: " + usuarioActual.getRol());
                return true;
            } else {
                System.out.println("\n✗ Usuario o contraseña incorrectos");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error de autenticación: " + e.getMessage());
            return false;
        }
    }

    private static void mostrarMenuPrincipal() {
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║            MENÚ PRINCIPAL              ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║ 1. Listar usuarios                     ║");
            System.out.println("║ 2. Crear usuario                       ║");
            System.out.println("║ 3. Editar usuario (por ID)             ║");
            System.out.println("║ 4. Eliminar usuario (por ID)           ║");
            System.out.println("║ 0. Salir                               ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print("Seleccione una opción: ");

            int opcion = leerEntero();

            switch (opcion) {
                case 1 -> listarUsuarios();
                case 2 -> crearUsuario();
                case 3 -> editarUsuario();
                case 4 -> eliminarUsuario();
                case 0 -> {
                    System.out.println("\n¡Hasta pronto!");
                    continuar = false;
                }
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private static void listarUsuarios() {
        try {
            List<Usuario> usuarios = usuarioDAO.obtenerTodos();
            System.out.println("\n=== USUARIOS DEL SISTEMA ===");
            for (Usuario u : usuarios) {
                System.out.println(u);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }
    }

    private static void crearUsuario() {
        try {
            System.out.println("\n--- CREAR NUEVO USUARIO ---");
            System.out.print("Username: ");
            String username = scanner.nextLine().trim();

            System.out.print("Password: ");
            String password = scanner.nextLine().trim();

            System.out.print("Nombre completo: ");
            String nombre = scanner.nextLine().trim();

            System.out.print("Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Rol (ADMIN/BIBLIOTECARIO/LECTOR): ");
            String rolStr = scanner.nextLine().trim().toUpperCase();
            Usuario.Rol rol;
            try {
                rol = Usuario.Rol.valueOf(rolStr);
            } catch (IllegalArgumentException ex) {
                System.out.println("Rol inválido. Usando LECTOR por defecto.");
                rol = Usuario.Rol.LECTOR;
            }

            Usuario usuario = new Usuario(username, password, nombre, email, rol);

            if (usuarioDAO.crear(usuario)) {
                System.out.println("✓ Usuario creado exitosamente con ID: " + usuario.getIdUsuario());
            } else {
                System.out.println("✗ Error al crear usuario");
            }
        } catch (SQLException e) {
            System.out.println("Error al crear usuario: " + e.getMessage());
        }
    }

    private static void editarUsuario() {
        try {
            System.out.print("ID del usuario a editar: ");
            int id = leerEntero();
            Usuario usuario = usuarioDAO.obtenerPorId(id);
            if (usuario == null) {
                System.out.println("Usuario no encontrado");
                return;
            }

            System.out.println("Usuario actual: " + usuario);
            System.out.print("Nuevo username (Enter para mantener): ");
            String username = scanner.nextLine().trim();
            if (!username.isEmpty()) usuario.setUsername(username);

            System.out.print("Nueva password (Enter para mantener): ");
            String password = scanner.nextLine().trim();
            if (!password.isEmpty()) usuario.setPassword(password);

            System.out.print("Nuevo nombre completo (Enter para mantener): ");
            String nombre = scanner.nextLine().trim();
            if (!nombre.isEmpty()) usuario.setNombreCompleto(nombre);

            System.out.print("Nuevo email (Enter para mantener): ");
            String email = scanner.nextLine().trim();
            if (!email.isEmpty()) usuario.setEmail(email);

            System.out.print("Rol (ADMIN/BIBLIOTECARIO/LECTOR) (Enter para mantener): ");
            String rolStr = scanner.nextLine().trim();
            if (!rolStr.isEmpty()) {
                try {
                    usuario.setRol(Usuario.Rol.valueOf(rolStr.toUpperCase()));
                } catch (IllegalArgumentException ex) {
                    System.out.println("Rol inválido. Se mantiene el rol actual.");
                }
            }

            System.out.print("Activo? (true/false) (Enter para mantener): ");
            String activoStr = scanner.nextLine().trim();
            if (!activoStr.isEmpty()) {
                usuario.setActivo(Boolean.parseBoolean(activoStr));
            }

            if (usuarioDAO.actualizar(usuario)) {
                System.out.println("✓ Usuario actualizado");
            } else {
                System.out.println("✗ Error al actualizar usuario");
            }

        } catch (SQLException e) {
            System.out.println("Error al editar usuario: " + e.getMessage());
        }
    }

    private static void eliminarUsuario() {
        try {
            System.out.print("ID del usuario a eliminar: ");
            int id = leerEntero();
            System.out.print("¿Seguro que desea eliminar el usuario " + id + " ? (s/n): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            if (!confirm.equals("s") && !confirm.equals("si")) {
                System.out.println("Eliminación cancelada.");
                return;
            }

            if (usuarioDAO.eliminar(id)) {
                System.out.println("✓ Usuario eliminado");
            } else {
                System.out.println("✗ No se pudo eliminar el usuario (¿existía?).");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
        }
    }

    private static int leerEntero() {
        while (true) {
            String linea = scanner.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.print("Por favor ingrese un número válido: ");
            }
        }
    }
}
