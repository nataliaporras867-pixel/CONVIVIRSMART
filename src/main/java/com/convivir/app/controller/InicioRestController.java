package com.convivir.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.convivir.app.service.NoticiaService; // Importamos el servicio de noticias
import java.util.Map;

@RestController
@RequestMapping("/api/inicio")
public class InicioRestController {

    private final NoticiaService noticiaService;

    
    public InicioRestController(NoticiaService noticiaService) {
        this.noticiaService = noticiaService;
    }

    @GetMapping("/info")
    public ResponseEntity<?> getInfo() {
        return ResponseEntity.ok(Map.of(
                "nombre", "CONVIVIRSMART",
                "descripcion", "Portal Web de Gestión de Conjunto Residencial",
                "version", "1.0.0",
                "estado", "activo"
        ));
    }

    @GetMapping("/zonas-comunes")
    public ResponseEntity<?> getZonasComunes() {
        return ResponseEntity.ok(java.util.List.of(
                Map.of("nombre", "Salón Social", "capacidad", 50, "icono", "🎉"),
                Map.of("nombre", "Cancha", "capacidad", 22, "icono", "⚽"),
                Map.of("nombre", "BBQ", "capacidad", 20, "icono", "🔥"),
                Map.of("nombre", "Gimnasio", "capacidad", 15, "icono", "💪")
        ));
    }
    
    @GetMapping("/noticias")
    public ResponseEntity<?> getNoticiasPublicas() {
        return ResponseEntity.ok(noticiaService.listarTodas());
    }
}