package com.convivir.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "unidades")
public class Unidad {

    @Id
    private String id;
    private String nombre;
    private String numero;
    private String torre;
    private String piso;
    private String tipo; 
    private double area;
    private String propietarioId;
    private String ocupanteId;
    private double coeficienteCopropiedad;
    private String estado; 
    private String propietarioEmail;
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    private LocalDateTime fechaActualizacion = LocalDateTime.now();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public boolean isAlDia() {
     
        return "AL_DIA".equalsIgnoreCase(this.estado); 
    }

    public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPropietarioEmail() {
		return propietarioEmail;
	}
	public void setPropietarioEmail(String propietarioEmail) {
		this.propietarioEmail = propietarioEmail;
	}
	public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getTorre() { return torre; }
    public void setTorre(String torre) { this.torre = torre; }

    public String getPiso() { return piso; }
    public void setPiso(String piso) { this.piso = piso; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getArea() { return area; }
    public void setArea(double area) { this.area = area; }

    public String getPropietarioId() { return propietarioId; }
    public void setPropietarioId(String propietarioId) { this.propietarioId = propietarioId; }

    public String getOcupanteId() { return ocupanteId; }
    public void setOcupanteId(String ocupanteId) { this.ocupanteId = ocupanteId; }

    public double getCoeficienteCopropiedad() { return coeficienteCopropiedad; }
    public void setCoeficienteCopropiedad(double coeficienteCopropiedad) { this.coeficienteCopropiedad = coeficienteCopropiedad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}