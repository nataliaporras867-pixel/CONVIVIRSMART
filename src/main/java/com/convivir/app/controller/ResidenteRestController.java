package com.convivir.app.controller;

import com.convivir.app.model.Usuario;
import com.convivir.app.service.ResidenteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/residentes")
public class ResidenteRestController {

    private final ResidenteService residenteService;

    public ResidenteRestController(ResidenteService residenteService) {
        this.residenteService = residenteService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(residenteService.listarResidentes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable String id) {
        return residenteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        try {
            residenteService.eliminar(id);
            return ResponseEntity.ok(Map.of("mensaje", "Residente eliminado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable String id,
                                            @RequestBody Map<String, Boolean> body) {
        try {
            residenteService.cambiarEstado(id, body.get("estado"));
            return ResponseEntity.ok(Map.of("mensaje", "Estado actualizado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }
}