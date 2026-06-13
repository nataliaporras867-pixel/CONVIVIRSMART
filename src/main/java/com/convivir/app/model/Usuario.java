package com.convivir.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String id;

    @NotBlank(message = "Los nombres no pueden estar vacíos.")
    @Size(min = 2, max = 50, message = "Los nombres deben tener entre 2 y 50 caracteres.")
    private String nombres;

    @NotBlank(message = "Los apellidos no pueden estar vacíos.")
    @Size(min = 2, max = 50, message = "Los apellidos deben tener entre 2 y 50 caracteres.")
    private String apellidos;

    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "El formato del correo electrónico no es válido.")
    @Indexed(unique = true)
    private String correo;

    @NotBlank(message = "El teléfono es obligatorio para el control de la unidad.")
    @Size(min = 7, max = 15, message = "El teléfono debe tener entre 7 y 15 dígitos numéricos.")
    private String telefono;

    private String passwordHash;
    private String tokenRecuperacion;
    private LocalDateTime fechaExpiracionToken;
    private Rol rol;
    private String unidadId;
    private boolean estado = true;
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    private LocalDateTime fechaActualizacion = LocalDateTime.now();

    public enum Rol {
        ADMIN, RESIDENTE, PORTERO, PROPIETARIO, PROVEEDOR
    }

    

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getTokenRecuperacion() { return tokenRecuperacion; }
    public void setTokenRecuperacion(String tokenRecuperacion) { this.tokenRecuperacion = tokenRecuperacion; }

    public LocalDateTime getFechaExpiracionToken() { return fechaExpiracionToken; }
    public void setFechaExpiracionToken(LocalDateTime fechaExpiracionToken) { this.fechaExpiracionToken = fechaExpiracionToken; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public String getUnidadId() { return unidadId; }
    public void setUnidadId(String unidadId) { this.unidadId = unidadId; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}