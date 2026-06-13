package com.convivir.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "votos")
public class Voto {
    
    @Id
    private String id;
    private String asambleaId;        
    private String propietarioId;     
    private String opcionSeleccionada; 

   
    public Voto() {}

    public Voto(String asambleaId, String propietarioId, String opcionSeleccionada) {
        this.asambleaId = asambleaId;
        this.propietarioId = propietarioId;
        this.opcionSeleccionada = opcionSeleccionada;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAsambleaId() { return asambleaId; }
    public void setAsambleaId(String asambleaId) { this.asambleaId = asambleaId; }

    public String getPropietarioId() { return propietarioId; }
    public void setPropietarioId(String propietarioId) { this.propietarioId = propietarioId; }

    public String getOpcionSeleccionada() { return opcionSeleccionada; }
    public void setOpcionSeleccionada(String opcionSeleccionada) { this.opcionSeleccionada = opcionSeleccionada; }
}