package com.convivir.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.convivir.app.model.Asamblea;
import com.convivir.app.service.AsambleaService;
import com.convivir.app.service.VotoService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/asambleas")
public class AsambleaAdminController {

    @Autowired private AsambleaService asambleaService;
    @Autowired private VotoService votoService;

    @GetMapping("/nueva")
    public String formularioCrear(Model model) {
        Asamblea asamblea = new Asamblea();
        asamblea.setAbierta(true);
        asamblea.setOpciones(Arrays.asList("A FAVOR", "EN CONTRA", "ABSTENCIÓN"));
        model.addAttribute("asamblea", asamblea);
        return "admin/crear_asamblea";
    }

    @GetMapping("/listar")
    public String listarAdmin(Model model) {
        model.addAttribute("asambleas", asambleaService.listarTodas());
        return "admin/lista_asambleas";
    }

    @PostMapping("/guardar")
    public String guardarAsamblea(@ModelAttribute Asamblea asamblea,
                                  @RequestParam(value = "opcionesTexto", required = false) String opcionesTexto) {
        if (opcionesTexto != null && !opcionesTexto.isBlank()) {
            List<String> opciones = Arrays.asList(opcionesTexto.split(","));
            asamblea.setOpciones(opciones.stream().map(String::trim).toList());
        }
        asambleaService.guardar(asamblea);
        return "redirect:/admin/asambleas/listar";
    }

    @PostMapping("/cerrar/{id}")
    public String cerrar(@PathVariable String id, RedirectAttributes flash) {
        asambleaService.cerrar(id);
        flash.addFlashAttribute("exito", "Asamblea cerrada.");
        return "redirect:/admin/asambleas/listar";
    }

    @GetMapping("/resultados/{id}")
    public String resultados(@PathVariable String id, Model model) {
        asambleaService.buscarPorId(id).ifPresent(a -> {
            model.addAttribute("asamblea", a);
            model.addAttribute("resultados", votoService.contarPorOpcion(id));
            model.addAttribute("totalVotos", votoService.listarPorAsamblea(id).size());
        });
        return "admin/resultados_votacion";
    }
}
