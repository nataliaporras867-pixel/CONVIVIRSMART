package com.convivir.app.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.convivir.app.model.Reserva;
import com.convivir.app.service.ReservaService;
import com.convivir.app.service.ZonaComunService;

@Controller
public class ReservaWebController {

    private final ReservaService reservaService;
    private final ZonaComunService zonaComunService;

    public ReservaWebController(ReservaService reservaService, ZonaComunService zonaComunService) {
        this.reservaService = reservaService;
        this.zonaComunService = zonaComunService;
    }
    
    @GetMapping("/residente/reservas")
    public String verMisReservas(Model model, @AuthenticationPrincipal User principal) {
        String residenteEmail = principal.getUsername();
        model.addAttribute("zonasDisponibles", zonaComunService.listarDisponibles());
        model.addAttribute("misReservas", reservaService.listarPorResidente(residenteEmail));
        model.addAttribute("nuevaReserva", new Reserva());
        return "admin/residentes/mis-reservas"; 
    }

    @PostMapping("/residente/reservas/solicitar")
    public String solicitarReserva(@ModelAttribute("nuevaReserva") Reserva reserva, 
                                   @AuthenticationPrincipal User principal) {
        reserva.setResidenteId(principal.getUsername());
        reserva.setEstado(Reserva.EstadoReserva.PENDIENTE);
        reservaService.guardar(reserva);
        return "redirect:/residente/reservas?success";
    }

    @GetMapping("/admin/reservas")
    public String gestionarReservasAdmin(Model model) {
        model.addAttribute("todasLasReservas", reservaService.listarTodas());
        
        
        model.addAttribute("zonasDisponibles", zonaComunService.listarDisponibles());
        
        return "admin/gestion-reservas"; 
    }

    @PostMapping("/admin/reservas/gestionar")
    public String actualizarEstadoReserva(@RequestParam("id") String id, 
                                          @RequestParam("accion") String accion,
                                          @RequestParam(value = "notas", required = false) String notas) {
        
        Reserva reserva = reservaService.buscarPorId(id);
        
        if (reserva != null) {
            if ("APROBAR".equals(accion)) {
                reserva.setEstado(Reserva.EstadoReserva.APROBADA);
            } else if ("RECHAZAR".equals(accion)) {
                reserva.setEstado(Reserva.EstadoReserva.RECHAZADA);
            }
            
            if (notas != null && !notas.trim().isEmpty()) {
                reserva.setNotasAdministracion(notas);
            }
            
            reservaService.guardar(reserva);
        }
        
        return "redirect:/admin/reservas?updated";
    } 
} 