package com.convivir.app.controller;

import com.convivir.app.model.Pqr;
import com.convivir.app.service.PqrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;


@RestController
@RequestMapping("/api/pqrs")
public class PqrRestController {

    @Autowired
    private PqrService pqrService;

    
    @PostMapping("/solicitar")
    public ResponseEntity<Pqr> crearPqrRest(@RequestBody Pqr pqr, Principal principal) {
        String usuarioActivo = (principal != null) ? principal.getName() : "anonimo_api";
        Pqr nuevaPqr = pqrService.registrarPqr(pqr, usuarioActivo);
        return new ResponseEntity<>(nuevaPqr, HttpStatus.CREATED);
    }

    
    @GetMapping("/listar")
    public ResponseEntity<Object> obtenerTodasRest() {
        return ResponseEntity.ok(pqrService.listarTodas());
    }
}