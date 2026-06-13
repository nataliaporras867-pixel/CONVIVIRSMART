package com.convivir.app.controller;

import com.convivir.app.dto.RestablecerPasswordRequest;
import com.convivir.app.dto.SolicitudRecuperacionRequest;
import com.convivir.app.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/recuperar")
public class RecuperarPasswordRestController {

    private final UsuarioService usuarioService;

    public RecuperarPasswordRestController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/solicitar")
    public ResponseEntity<?> solicitarToken(@RequestBody SolicitudRecuperacionRequest request) {
        Map<String, String> response = new HashMap<>();
        try {
            String token = usuarioService.generarTokenRecuperacion(request.getCorreo());
            response.put("mensaje", "Se ha generado la solicitud de recuperación.");
            
            response.put("token_simulado", token); 
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/restablecer")
    public ResponseEntity<?> restablecerPassword(@RequestBody RestablecerPasswordRequest request) {
        Map<String, String> response = new HashMap<>();
        try {
            usuarioService.cambiarPasswordConToken(request.getToken(), request.getNuevaPassword());
            response.put("mensaje", "Contraseña actualizada exitosamente.");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}