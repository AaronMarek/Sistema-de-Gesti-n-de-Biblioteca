package com.model;

import java.time.LocalDate;

public class Prestamo {
	private int idPrestamo;
	private int idLibro;
	private String tituloLibro;
	private int idMiembro;
	private String nombreMiembro;
	private int idUsuario;
	private LocalDate fechaPrestamo;
	private LocalDate fechaDevolucionEsperada;
	private LocalDate fechaDevolucionReal;
	private EstadoPrestamo estado;
	private String observaciones;

	public enum EstadoPrestamo {
		ACTIVO, DEVUELTO, ATRASADO
	}

	public Prestamo() {
	}

	public Prestamo(int idLibro, int idMiembro, int idUsuario, int diasPrestamo) {
		this.idLibro = idLibro;
		this.idMiembro = idMiembro;
		this.idUsuario = idUsuario;
		this.fechaPrestamo = LocalDate.now();
		this.fechaDevolucionEsperada = LocalDate.now().plusDays(diasPrestamo);
		this.estado = EstadoPrestamo.ACTIVO;
	}

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

	public String getTituloLibro() {
		return tituloLibro;
	}

	public void setTituloLibro(String tituloLibro) {
		this.tituloLibro = tituloLibro;
	}

	public int getIdMiembro() {
		return idMiembro;
	}

	public void setIdMiembro(int idMiembro) {
		this.idMiembro = idMiembro;
	}

	public String getNombreMiembro() {
		return nombreMiembro;
	}

	public void setNombreMiembro(String nombreMiembro) {
		this.nombreMiembro = nombreMiembro;
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

	public EstadoPrestamo getEstado() {
		return estado;
	}

	public void setEstado(EstadoPrestamo estado) {
		this.estado = estado;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	@Override
	public String toString() {
		return "Prestamo [idPrestamo=" + idPrestamo + ", idLibro=" + idLibro + ", tituloLibro=" + tituloLibro
				+ ", idMiembro=" + idMiembro + ", nombreMiembro=" + nombreMiembro + ", idUsuario=" + idUsuario
				+ ", fechaPrestamo=" + fechaPrestamo + ", fechaDevolucionEsperada=" + fechaDevolucionEsperada
				+ ", fechaDevolucionReal=" + fechaDevolucionReal + ", estado=" + estado + ", observaciones="
				+ observaciones + "]";
	}	
	
	
}
