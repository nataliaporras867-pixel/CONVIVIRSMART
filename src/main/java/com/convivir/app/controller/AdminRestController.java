package com.convivir.app.controller;

import com.convivir.app.model.Usuario;
import com.convivir.app.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final UsuarioRepository usuarioRepository;

    public AdminRestController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/dashboard/kpis")
    public ResponseEntity<?> getKpis() {
        long totalResidentes = usuarioRepository
                .findByRol(Usuario.Rol.RESIDENTE).size();
        long totalPropietarios = usuarioRepository
                .findByRol(Usuario.Rol.PROPIETARIO).size();
        long totalPorteros = usuarioRepository
                .findByRol(Usuario.Rol.PORTERO).size();
        long totalProveedores = usuarioRepository
                .findByRol(Usuario.Rol.PROVEEDOR).size();

        return ResponseEntity.ok(Map.of(
                "totalResidentes", totalResidentes,
                "totalPropietarios", totalPropietarios,
                "totalPorteros", totalPorteros,
                "totalProveedores", totalProveedores
        ));
    }

 
    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

   
    @GetMapping("/usuarios/rol/{rol}")
    public ResponseEntity<List<Usuario>> getUsuariosByRol(@PathVariable String rol) {
        try {
            Usuario.Rol rolEnum = Usuario.Rol.valueOf(rol.toUpperCase());
            return ResponseEntity.ok(usuarioRepository.findByRol(rolEnum));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

   
    @PutMapping("/usuarios/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable String id,
                                            @RequestBody Map<String, Boolean> body) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setEstado(body.get("estado"));
            usuarioRepository.save(usuario);
            return ResponseEntity.ok(Map.of("mensaje", "Estado actualizado correctamente"));
        }).orElse(ResponseEntity.notFound().build());
    }

   
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable String id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("mensaje", "Usuario eliminado correctamente"));
    }
}