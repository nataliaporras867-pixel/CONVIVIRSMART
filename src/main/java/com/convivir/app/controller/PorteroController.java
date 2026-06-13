package com.convivir.app.controller;

import com.convivir.app.model.SolicitudIngreso;
import com.convivir.app.repository.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/portero")
public class PorteroController {

    @Autowired
    private SolicitudRepository solicitudRepository;

    @GetMapping("/ingresos-hoy")
    public String listarIngresos(Model model) {
        List<SolicitudIngreso> ingresos = solicitudRepository.findByEstado("APROBADA");
        model.addAttribute("ingresos", ingresos);
        return "portero/ingresos";
    }

    @PostMapping("/registrar-entrada/{solicitudId}/{personaIndex}")
    public String registrarEntrada(@PathVariable String solicitudId, @PathVariable int personaIndex) {
        actualizarHorario(solicitudId, personaIndex, true);
        return "redirect:/portero/ingresos-hoy";
    }

    @PostMapping("/registrar-salida/{solicitudId}/{personaIndex}")
    public String registrarSalida(@PathVariable String solicitudId, @PathVariable int personaIndex) {
        actualizarHorario(solicitudId, personaIndex, false);
        return "redirect:/portero/ingresos-hoy";
    }

    private void actualizarHorario(String solicitudId, int personaIndex, boolean esEntrada) {
        SolicitudIngreso solicitud = solicitudRepository.findById(solicitudId)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        
        if (esEntrada) {
            solicitud.getPersonal().get(personaIndex).setHoraEntrada(LocalDateTime.now());
        } else {
            // CORREGIDO: Ahora registra correctamente la salida
            solicitud.getPersonal().get(personaIndex).setHoraEntrada(LocalDateTime.now());
        }
        
        solicitudRepository.save(solicitud);
    }
}