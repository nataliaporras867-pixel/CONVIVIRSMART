package com.convivir.app.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reservas")
public class Reserva {
	
	@Id
	private String id;
	private String zonaComunId;
	private String residenteId;
	
	private LocalDateTime fechaHoraInicio;
	private LocalDateTime fechaHoraFin;
	
	private EstadoReserva estado;
	private LocalDateTime fechaSolicitud;
	private String notasAdministracion;
	
	public enum EstadoReserva{
		
		PENDIENTE, APROBADA, RECHAZADA, CANCELADA
	}
	
	public Reserva() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getZonaComunId() {
		return zonaComunId;
	}

	public void setZonaComunId(String zonaComunId) {
		this.zonaComunId = zonaComunId;
	}

	public String getResidenteId() {
		return residenteId;
	}

	public void setResidenteId(String residenteId) {
		this.residenteId = residenteId;
	}

	public LocalDateTime getFechaHoraInicio() {
		return fechaHoraInicio;
	}

	public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
		this.fechaHoraInicio = fechaHoraInicio;
	}

	public LocalDateTime getFechaHoraFin() {
		return fechaHoraFin;
	}

	public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
		this.fechaHoraFin = fechaHoraFin;
	}

	public EstadoReserva getEstado() {
		return estado;
	}

	public void setEstado(EstadoReserva estado) {
		this.estado = estado;
	}

	public LocalDateTime getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public String getNotasAdministracion() {
		return notasAdministracion;
	}

	public void setNotasAdministracion(String notasAdministracion) {
		this.notasAdministracion = notasAdministracion;
	}
	
	

}
