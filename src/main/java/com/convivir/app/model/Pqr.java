package com.convivir.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "pqrs")
public class Pqr {
    
    @Id
    private String id;
    private String residenteId;
    private String titulo;
    private String descripcion;
    private String tipo;   
    private String estado; 
    private LocalDateTime fechaCreacion;
    
    
    private List<PqrHistorial> historial = new ArrayList<>();

    public Pqr() {
        this.fechaCreacion = LocalDateTime.now();
        this.estado = "PENDIENTE";
    }

    
    public void agregarHistorial(String estadoAnterior, String estadoNuevo, String comentario, String responsable) {
        if (this.historial == null) {
            this.historial = new ArrayList<>();
        }
        this.historial.add(new PqrHistorial(estadoAnterior, estadoNuevo, comentario, responsable));
    }

   
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getResidenteId() { return residenteId; }
    public void setResidenteId(String residenteId) { this.residenteId = residenteId; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public List<PqrHistorial> getHistorial() { return historial; }
    public void setHistorial(List<PqrHistorial> historial) { this.historial = historial; }
}