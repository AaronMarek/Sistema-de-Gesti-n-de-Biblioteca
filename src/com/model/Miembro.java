package com.model;

import java.math.BigDecimal;
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
    private String estado;
    private BigDecimal multaAcumulada;
    
    
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
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public BigDecimal getMultaAcumulada() {
		return multaAcumulada;
	}
	public void setMultaAcumulada(BigDecimal multaAcumulada) {
		this.multaAcumulada = multaAcumulada;
	}

    
}
