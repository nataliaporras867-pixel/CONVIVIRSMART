package com.convivir.app.service;

import com.convivir.app.dto.RegistroRequest;
import com.convivir.app.model.Usuario;
import com.convivir.app.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID; 

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> listarSoloResidentes() {
        return usuarioRepository.findByRol(Usuario.Rol.RESIDENTE);
    }

    public Usuario obtenerPorId(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        return usuarioRepository.findById(id).orElse(null);
    }

    public void procesarRegistro(Usuario usuario) {
        if (usuario.getId() != null && !usuario.getId().isEmpty()) {
            Usuario existente = usuarioRepository.findById(usuario.getId())
                    .orElseThrow(() -> new RuntimeException("Residente no encontrado"));
            
            existente.setNombres(usuario.getNombres());
            existente.setApellidos(usuario.getApellidos());
            existente.setCorreo(usuario.getCorreo());
            existente.setTelefono(usuario.getTelefono());
            
            if (usuario.getUnidadId() != null && !usuario.getUnidadId().isEmpty()) {
                existente.setUnidadId(usuario.getUnidadId());
            }
            
            existente.setFechaActualizacion(LocalDateTime.now());
            usuarioRepository.save(existente);
        } else {
            usuario.setPasswordHash(passwordEncoder.encode(usuario.getPasswordHash()));
            usuario.setRol(Usuario.Rol.RESIDENTE);
            usuario.setEstado(true);
            usuario.setFechaCreacion(LocalDateTime.now());
            usuarioRepository.save(usuario);
        }
    }

    
    public void procesarRegistro(RegistroRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNombres(request.getNombres());
        usuario.setApellidos(request.getApellidos());
        usuario.setCorreo(request.getCorreo());
        
        
        usuario.setTelefono(request.getTelefono());
        
        usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(Usuario.Rol.valueOf(request.getRol()));
        usuario.setEstado(true);
        
        
        usuario.setFechaCreacion(LocalDateTime.now());
        
        usuarioRepository.save(usuario);
    }

    public void eliminar(String id) {
        usuarioRepository.deleteById(id);
    }

    public String generarTokenRecuperacion(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("No existe un usuario registrado con ese correo electrónico."));
        
        String token = UUID.randomUUID().toString();
        usuario.setTokenRecuperacion(token);
        usuario.setFechaExpiracionToken(LocalDateTime.now().plusMinutes(15));
        
        usuarioRepository.save(usuario);
        return token; 
    }

    public void cambiarPasswordConToken(String token, String nuevaPassword) {
        Usuario usuario = usuarioRepository.findByTokenRecuperacion(token)
                .orElseThrow(() -> new RuntimeException("El enlace de recuperación no es válido o ya fue utilizado."));
        
        if (usuario.getFechaExpiracionToken().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("El enlace de recuperación ha expirado. Solicita uno nuevo.");
        }
        
        usuario.setPasswordHash(passwordEncoder.encode(nuevaPassword));
        usuario.setTokenRecuperacion(null);
        usuario.setFechaExpiracionToken(null);
        
        usuarioRepository.save(usuario);
    }
}