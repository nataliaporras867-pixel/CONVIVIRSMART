package com.convivir.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "asambleas")
public class Asamblea {
    @Id
    private String id;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaHora;
    private String enlaceReunion;
    private boolean abierta;
    private List<String> opciones = new ArrayList<>();   // ej. ["A FAVOR","EN CONTRA","ABSTENCION"]
    private String tema;                                  // pregunta/tema de la votación

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public String getEnlaceReunion() { return enlaceReunion; }
    public void setEnlaceReunion(String enlaceReunion) { this.enlaceReunion = enlaceReunion; }
    public boolean isAbierta() { return abierta; }
    public void setAbierta(boolean abierta) { this.abierta = abierta; }
    public List<String> getOpciones() { return opciones; }
    public void setOpciones(List<String> opciones) { this.opciones = opciones; }
    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }
}
