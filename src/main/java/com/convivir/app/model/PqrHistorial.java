package com.convivir.app.model;

import java.time.LocalDateTime;

public class PqrHistorial {
	
	private String estadoAnterior;
	private String estadoNuevo;
	private String comentario;
	private LocalDateTime fechaCambio;
	private String responsable;
	
	public PqrHistorial() {
		this.fechaCambio = LocalDateTime.now();
	}
	
	public PqrHistorial(String estadoAnterior, String estadoNuevo, String comentario, String responsable) {
        this.estadoAnterior = estadoAnterior;
        this.estadoNuevo = estadoNuevo;
        this.comentario = comentario;
        this.responsable = responsable;
        this.fechaCambio = LocalDateTime.now();
   }

	public String getEstadoAnterior() {
		return estadoAnterior;
	}

	public void setEstadoAnterior(String estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}

	public String getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(String estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public LocalDateTime getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(LocalDateTime fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	
}
