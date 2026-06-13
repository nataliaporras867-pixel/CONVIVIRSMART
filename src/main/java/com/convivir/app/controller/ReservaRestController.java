package com.convivir.app.controller;

import com.convivir.app.model.Reserva;
import com.convivir.app.service.ReservaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reservas")
public class ReservaRestController {

    private final ReservaService reservaService;

    public ReservaRestController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping("/solicitar")
    public ResponseEntity<?> solicitarReserva(@RequestBody Reserva reserva, @AuthenticationPrincipal User principal) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            reserva.setResidenteId(principal.getUsername());
           
            reservaService.registrarReserva(reserva);
            
            respuesta.put("success", true);
            respuesta.put("mensaje", "Tu solicitud de reserva ha sido enviada con éxito al administrador.");
            return ResponseEntity.ok(respuesta);

        } catch (RuntimeException e) {
            
            respuesta.put("success", false);
            respuesta.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        } catch (Exception e) {
            respuesta.put("success", false);
            respuesta.put("mensaje", "Ocurrió un error inesperado al procesar la reserva.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }
}