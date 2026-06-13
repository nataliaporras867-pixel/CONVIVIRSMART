package com.convivir.app.service;

import com.convivir.app.model.Usuario;
import com.convivir.app.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ResidenteService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public ResidenteService(UsuarioRepository usuarioRepository,
                            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> listarResidentes() {
        return usuarioRepository.findByRol(Usuario.Rol.RESIDENTE);
    }

    public Optional<Usuario> buscarPorId(String id) {
        return usuarioRepository.findById(id);
    }

    public void guardar(Usuario usuario) {
        if (usuario.getId() != null && usuario.getId().trim().isEmpty()) {
            usuario.setId(null);
        }
        if (usuario.getId() == null) {
            if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
                throw new RuntimeException("El correo ya está registrado");
            }
            usuario.setRol(Usuario.Rol.RESIDENTE);
            usuario.setEstado(true);
            usuario.setFechaCreacion(LocalDateTime.now());
            usuario.setPasswordHash(passwordEncoder.encode(usuario.getPasswordHash()));
        } else {
            usuarioRepository.findById(usuario.getId()).ifPresent(existente -> {
                if (!usuario.getPasswordHash().equals(existente.getPasswordHash())) {
                    usuario.setPasswordHash(passwordEncoder.encode(usuario.getPasswordHash()));
                }
                usuario.setRol(Usuario.Rol.RESIDENTE);
                usuario.setEstado(existente.isEstado());
                usuario.setFechaCreacion(existente.getFechaCreacion());
            });
        }
        usuario.setFechaActualizacion(LocalDateTime.now());
        usuarioRepository.save(usuario);
    }
    

    public void eliminar(String id) {
        usuarioRepository.deleteById(id);
    }

    public void cambiarEstado(String id, boolean estado) {
        usuarioRepository.findById(id).ifPresent(usuario -> {
            usuario.setEstado(estado);
            usuario.setFechaActualizacion(LocalDateTime.now());
            usuarioRepository.save(usuario);
        });
    }

	public Optional<Usuario> buscarPorObjectId(String id) {
	    return usuarioRepository.findByObjectId(id);
	}

	public void eliminarPorObjectId(String id) {
	    usuarioRepository.findByObjectId(id).ifPresent(usuario -> {
	        usuarioRepository.delete(usuario);
	    });
	}
}