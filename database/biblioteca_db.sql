-- Creación de la base de datos
CREATE DATABASE IF NOT EXISTS biblioteca_db;
USE biblioteca_db;

-- Tabla de Usuarios del Sistema
CREATE TABLE usuarios (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nombre_completo VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    rol ENUM('ADMIN', 'BIBLIOTECARIO', 'LECTOR') DEFAULT 'LECTOR',
    activo BOOLEAN DEFAULT TRUE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de Miembros (personas que pueden pedir libros prestados)
CREATE TABLE miembros (
    id_miembro INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    dni VARCHAR(20) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(100),
    direccion VARCHAR(200),
    fecha_inscripcion DATE NOT NULL,
    estado ENUM('ACTIVO', 'SUSPENDIDO', 'INACTIVO') DEFAULT 'ACTIVO',
    multa_acumulada DECIMAL(10,2) DEFAULT 0.00
);

-- Tabla de Categorías
CREATE TABLE categorias (
    id_categoria INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) UNIQUE NOT NULL,
    descripcion TEXT
);

-- Tabla de Autores
CREATE TABLE autores (
    id_autor INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    nacionalidad VARCHAR(50),
    biografia TEXT
);

-- Tabla de Libros
CREATE TABLE libros (
    id_libro INT PRIMARY KEY AUTO_INCREMENT,
    isbn VARCHAR(20) UNIQUE NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    id_autor INT,
    id_categoria INT,
    editorial VARCHAR(100),
    anio_publicacion YEAR,
    numero_paginas INT,
    idioma VARCHAR(50) DEFAULT 'Español',
    cantidad_total INT NOT NULL DEFAULT 1,
    cantidad_disponible INT NOT NULL DEFAULT 1,
    ubicacion VARCHAR(50),
    estado ENUM('NUEVO', 'BUENO', 'REGULAR', 'DETERIORADO') DEFAULT 'BUENO',
    FOREIGN KEY (id_autor) REFERENCES autores(id_autor) ON DELETE SET NULL,
    FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria) ON DELETE SET NULL
);

-- Tabla de Préstamos
CREATE TABLE prestamos (
    id_prestamo INT PRIMARY KEY AUTO_INCREMENT,
    id_libro INT NOT NULL,
    id_miembro INT NOT NULL,
    id_usuario INT NOT NULL,
    fecha_prestamo DATE NOT NULL,
    fecha_devolucion_esperada DATE NOT NULL,
    fecha_devolucion_real DATE,
    estado ENUM('ACTIVO', 'DEVUELTO', 'ATRASADO') DEFAULT 'ACTIVO',
    observaciones TEXT,
    FOREIGN KEY (id_libro) REFERENCES libros(id_libro) ON DELETE CASCADE,
    FOREIGN KEY (id_miembro) REFERENCES miembros(id_miembro) ON DELETE CASCADE,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE
);

-- Tabla de Multas
CREATE TABLE multas (
    id_multa INT PRIMARY KEY AUTO_INCREMENT,
    id_prestamo INT NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    motivo VARCHAR(200),
    fecha_multa DATE NOT NULL,
    pagada BOOLEAN DEFAULT FALSE,
    fecha_pago DATE,
    FOREIGN KEY (id_prestamo) REFERENCES prestamos(id_prestamo) ON DELETE CASCADE
);

-- Tabla de Reservas
CREATE TABLE reservas (
    id_reserva INT PRIMARY KEY AUTO_INCREMENT,
    id_libro INT NOT NULL,
    id_miembro INT NOT NULL,
    fecha_reserva TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('PENDIENTE', 'COMPLETADA', 'CANCELADA') DEFAULT 'PENDIENTE',
    FOREIGN KEY (id_libro) REFERENCES libros(id_libro) ON DELETE CASCADE,
    FOREIGN KEY (id_miembro) REFERENCES miembros(id_miembro) ON DELETE CASCADE
);
