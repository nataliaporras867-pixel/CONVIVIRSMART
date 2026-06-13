package com.convivir.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;

@Document(collection = "zonas_comunes")
public class ZonaComun {
    
    @Id
    private String id;
    private String nombre;
    private String descripcion;
    private int capacidadMaxima;
    private BigDecimal costoReserva;
    private boolean disponible;
    
   
    private String imagenUrl;
    
    public ZonaComun() {}
    
    
    public ZonaComun(String nombre, String descripcion, int capacidadMaxima, BigDecimal costoReserva, boolean disponible, String imagenUrl) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.capacidadMaxima = capacidadMaxima;
        this.costoReserva = costoReserva;
        this.disponible = disponible;    
        this.imagenUrl = imagenUrl; 
    }

    
    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

   
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public int getCapacidad() {
        return this.capacidadMaxima;
    }

    public BigDecimal getCostoReserva() {
        return costoReserva;
    }

    public void setCostoReserva(BigDecimal costoReserva) {
        this.costoReserva = costoReserva;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}