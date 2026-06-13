package com.convivir.app.controller;

import com.convivir.app.dto.VotoDTO;
import com.convivir.app.model.Asamblea;
import com.convivir.app.service.AsambleaService;
import com.convivir.app.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/asambleas")
public class AsambleaRestController {

    @Autowired private VotoService votoService;
    @Autowired private AsambleaService asambleaService;

    @PostMapping("/{id}/votar")
    public ResponseEntity<?> registrarVoto(@PathVariable String id, @RequestBody VotoDTO voto) {
        try {
            votoService.registrar(id, voto);
            return ResponseEntity.ok(Map.of("mensaje", "Voto registrado con éxito"));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}/resultados")
    public ResponseEntity<?> resultados(@PathVariable String id) {
        return ResponseEntity.ok(votoService.contarPorOpcion(id));
    }

    @PostMapping("/{id}/cerrar")
    public ResponseEntity<?> cerrar(@PathVariable String id) {
        asambleaService.cerrar(id);
        return ResponseEntity.ok(Map.of("mensaje", "Asamblea cerrada"));
    }
}