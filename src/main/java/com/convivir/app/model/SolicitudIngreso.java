package com.convivir.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "solicitudes_ingreso")
public class SolicitudIngreso {
    @Id
    private String id;
    private String idProveedor;
    private String nombreProveedor; // Para mostrarlo rápido en la tabla
    private LocalDateTime fechaHoraEstimada;
    private String motivo;
    private String estado; // PENDIENTE, APROBADA, RECHAZADA, FINALIZADA
    private List<Persona> personal; // Clase interna o externa
    private List<String> equipos;
    
    public List<Persona> getPersonal() { return personal; }
    public void setPersonal(List<Persona> personal) { this.personal = personal; }
    
    @Data
    public static class Persona {
        private String nombre;
        private String documento;
        private LocalDateTime horaEntrada;
        private LocalDateTime horaSalida;
		public void setHoraEntrada(LocalDateTime now) {
			// TODO Auto-generated method stub
			
		}
    }

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdProveedor() {
		return idProveedor;
	}
	public void setIdProveedor(String idProveedor) {
		this.idProveedor = idProveedor;
	}
	public String getNombreProveedor() {
		return nombreProveedor;
	}
	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}
	public LocalDateTime getFechaHoraEstimada() {
		return fechaHoraEstimada;
	}
	public void setFechaHoraEstimada(LocalDateTime fechaHoraEstimada) {
		this.fechaHoraEstimada = fechaHoraEstimada;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public List<String> getEquipos() {
		return equipos;
	}
	public void setEquipos(List<String> equipos) {
		this.equipos = equipos;
	}
}