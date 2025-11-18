package com.model;

import java.time.LocalDate;

public class Prestamo {
    private int idPrestamo;
    private int idLibro;
    private int idMiembro;
    private int idUsuario;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEsperada;
    private LocalDate fechaDevolucionReal;
    private String estado;
    private String observaciones;
    
    
	public int getIdPrestamo() {
		return idPrestamo;
	}
	public void setIdPrestamo(int idPrestamo) {
		this.idPrestamo = idPrestamo;
	}
	public int getIdLibro() {
		return idLibro;
	}
	public void setIdLibro(int idLibro) {
		this.idLibro = idLibro;
	}
	public int getIdMiembro() {
		return idMiembro;
	}
	public void setIdMiembro(int idMiembro) {
		this.idMiembro = idMiembro;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public LocalDate getFechaPrestamo() {
		return fechaPrestamo;
	}
	public void setFechaPrestamo(LocalDate fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}
	public LocalDate getFechaDevolucionEsperada() {
		return fechaDevolucionEsperada;
	}
	public void setFechaDevolucionEsperada(LocalDate fechaDevolucionEsperada) {
		this.fechaDevolucionEsperada = fechaDevolucionEsperada;
	}
	public LocalDate getFechaDevolucionReal() {
		return fechaDevolucionReal;
	}
	public void setFechaDevolucionReal(LocalDate fechaDevolucionReal) {
		this.fechaDevolucionReal = fechaDevolucionReal;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}


}
