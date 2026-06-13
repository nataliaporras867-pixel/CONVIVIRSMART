package com.convivir.app.controller;

import com.convivir.app.model.Unidad;
import com.convivir.app.service.UnidadService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/unidades")
public class UnidadWebController {

    private final UnidadService unidadService;

    public UnidadWebController(UnidadService unidadService) {
        this.unidadService = unidadService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("unidades", unidadService.listarTodas());
        return "admin/unidades/lista";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("unidad", new Unidad());
        model.addAttribute("titulo", "Nueva Unidad");
        return "admin/unidades/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, Model model,
                         RedirectAttributes flash) {
        return unidadService.buscarPorId(id).map(unidad -> {
            model.addAttribute("unidad", unidad);
            model.addAttribute("titulo", "Editar Unidad");
            return "admin/unidades/formulario";
        }).orElseGet(() -> {
            flash.addFlashAttribute("error", "Unidad no encontrada");
            return "redirect:/admin/unidades";
        });
    }
    


    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Unidad unidad, 
                          @RequestParam(required = false) String emailPropietario, 
                          RedirectAttributes flash) {
        try {
          
            if (emailPropietario != null && !emailPropietario.isEmpty()) {
                unidad.setPropietarioEmail(emailPropietario);
            }
            unidadService.guardar(unidad);
            flash.addFlashAttribute("exito", "Unidad guardada y vinculada correctamente");
        } catch (Exception e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/unidades";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, RedirectAttributes flash) {
        try {
            unidadService.eliminar(id);
            flash.addFlashAttribute("exito", "Unidad eliminada correctamente");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al eliminar la unidad");
        }
        return "redirect:/admin/unidades";
    }
}