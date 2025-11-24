package com.model;

public class Libro {
	private int idLibro;
	private String isbn;
	private String titulo;
	private int idAutor;
	private String nombreAutor;
	private int idCategoria;
	private String nombreCategoria;
	private String editorial;
	private int anioPublicacion;
	private int numeroPaginas;
	private String idioma;
	private int cantidadTotal;
	private int cantidadDisponible;
	private String ubicacion;
	private EstadoLibro estado;

	public enum EstadoLibro {
		NUEVO, BUENO, REGULAR, DETERIORADO
	}

	public Libro() {
	}

	public Libro(String isbn, String titulo, int idAutor, int idCategoria, String editorial, int anioPublicacion,
			int cantidadTotal) {
		this.isbn = isbn;
		this.titulo = titulo;
		this.idAutor = idAutor;
		this.idCategoria = idCategoria;
		this.editorial = editorial;
		this.anioPublicacion = anioPublicacion;
		this.cantidadTotal = cantidadTotal;
		this.cantidadDisponible = cantidadTotal;
		this.estado = EstadoLibro.BUENO;
		this.idioma = "Español";
	}

	public int getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(int idLibro) {
		this.idLibro = idLibro;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getIdAutor() {
		return idAutor;
	}

	public void setIdAutor(int idAutor) {
		this.idAutor = idAutor;
	}

	public String getNombreAutor() {
		return nombreAutor;
	}

	public void setNombreAutor(String nombreAutor) {
		this.nombreAutor = nombreAutor;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public int getAnioPublicacion() {
		return anioPublicacion;
	}

	public void setAnioPublicacion(int anioPublicacion) {
		this.anioPublicacion = anioPublicacion;
	}

	public int getNumeroPaginas() {
		return numeroPaginas;
	}

	public void setNumeroPaginas(int numeroPaginas) {
		this.numeroPaginas = numeroPaginas;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public int getCantidadTotal() {
		return cantidadTotal;
	}

	public void setCantidadTotal(int cantidadTotal) {
		this.cantidadTotal = cantidadTotal;
	}

	public int getCantidadDisponible() {
		return cantidadDisponible;
	}

	public void setCantidadDisponible(int cantidadDisponible) {
		this.cantidadDisponible = cantidadDisponible;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public EstadoLibro getEstado() {
		return estado;
	}

	public void setEstado(EstadoLibro estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Libro [idLibro=" + idLibro + ", isbn=" + isbn + ", titulo=" + titulo + ", idAutor=" + idAutor
				+ ", nombreAutor=" + nombreAutor + ", idCategoria=" + idCategoria + ", nombreCategoria="
				+ nombreCategoria + ", editorial=" + editorial + ", anioPublicacion=" + anioPublicacion
				+ ", numeroPaginas=" + numeroPaginas + ", idioma=" + idioma + ", cantidadTotal=" + cantidadTotal
				+ ", cantidadDisponible=" + cantidadDisponible + ", ubicacion=" + ubicacion + ", estado=" + estado
				+ "]";
	}
	
	

}
