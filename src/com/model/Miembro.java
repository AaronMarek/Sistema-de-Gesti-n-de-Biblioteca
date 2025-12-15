package com.model;

import java.time.LocalDate;

public class Miembro {
    private int idMiembro;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String email;
    private String direccion;
    private LocalDate fechaInscripcion;
    private EstadoMiembro estado;
    private double multaAcumulada;

    public enum EstadoMiembro {
        ACTIVO, SUSPENDIDO, INACTIVO
    }

    public Miembro() {}

    public Miembro(String nombre, String apellido, String dni, String telefono, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
        this.fechaInscripcion = LocalDate.now();
        this.estado = EstadoMiembro.ACTIVO;
        this.multaAcumulada = 0.0;
    }

	public int getIdMiembro() {
		return idMiembro;
	}

	public void setIdMiembro(int idMiembro) {
		this.idMiembro = idMiembro;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public LocalDate getFechaInscripcion() {
		return fechaInscripcion;
	}

	public void setFechaInscripcion(LocalDate fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}

	public EstadoMiembro getEstado() {
		return estado;
	}

	public void setEstado(EstadoMiembro estado) {
		this.estado = estado;
	}

	public double getMultaAcumulada() {
		return multaAcumulada;
	}

	public void setMultaAcumulada(double multaAcumulada) {
		this.multaAcumulada = multaAcumulada;
	}

	@Override
	public String toString() {
		return "Miembro [idMiembro=" + idMiembro + ", nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni
				+ ", telefono=" + telefono + ", email=" + email + ", direccion=" + direccion + ", fechaInscripcion="
				+ fechaInscripcion + ", estado=" + estado + ", multaAcumulada=" + multaAcumulada + "]";
	}
    
    
}
