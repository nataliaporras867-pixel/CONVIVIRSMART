package com.convivir.app.controller;

import com.convivir.app.dto.RegistroRequest;
import com.convivir.app.service.UsuarioService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class RegistroRestController {

    private final UsuarioService usuarioService;

    public RegistroRestController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody RegistroRequest request) {

        try {
            
            if (!request.getPassword().equals(request.getConfirmarPassword())) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Las contraseñas no coinciden"));
            }

            
            usuarioService.procesarRegistro(request);

            return ResponseEntity.ok(
                    Map.of("mensaje", "Usuario registrado exitosamente"));

        } catch (Exception e) {
            
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Fallo en el registro: " + e.getMessage()));
        }
    }
}