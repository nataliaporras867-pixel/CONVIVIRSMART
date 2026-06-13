package com.convivir.app.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.convivir.app.model.ZonaComun;
import com.convivir.app.service.ZonaComunService;

@RestController
@RequestMapping("/api/zonas")
public class ZonaComunRestController {

    private final ZonaComunService zonaComunService;

    public ZonaComunRestController(ZonaComunService zonaComunService) {
        this.zonaComunService = zonaComunService;
    }

    @GetMapping
    public List<ZonaComun> obtenerTodasLasZonas() {
        return zonaComunService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZonaComun> obtenerZonaPorId(@PathVariable String id) {
        ZonaComun zona = zonaComunService.buscarPorId(id);
        if (zona != null) {
            return ResponseEntity.ok(zona);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> crearZonaDesdeApi(@RequestBody ZonaComun zona) {
        zonaComunService.guardar(zona);
        return ResponseEntity.ok("Zona común creada exitosamente a través de la API REST");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarZonaDesdeApi(@PathVariable String id) {
        if (zonaComunService.buscarPorId(id) != null) {
            zonaComunService.eliminar(id);
            return ResponseEntity.ok("Zona común eliminada exitosamente");
        }
        return ResponseEntity.notFound().build();
    }
}