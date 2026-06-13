package com.convivir.app.model;

import org.springframework.data.annotation.Transient;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cuotas")
public class Cuota {
	
	
	
	@Id
	private String id;
	private String estado;
	private String residenteId;
	private double valor;
	private String mes;
	private LocalDate fechaVencimiento;
	private boolean pagado;
	private String descripcion;
	private LocalDate fechaPago;
	
	@Transient 
    private String residenteNombre;
	
	public String getResidenteNombre() { return residenteNombre; }
    public void setResidenteNombre(String residenteNombre) { this.residenteNombre = residenteNombre; }
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getResidenteId() {
		return residenteId;
	}
	public void setResidenteId(String residenteId) {
		this.residenteId = residenteId;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public LocalDate getFechaVencimiento() {
		return fechaVencimiento;
	}
	public void setFechaVencimiento(LocalDate fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	public boolean isPagado() {
		return pagado;
	}
	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}
	public LocalDate getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(LocalDate fechaPago) {
		this.fechaPago = fechaPago;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
}

