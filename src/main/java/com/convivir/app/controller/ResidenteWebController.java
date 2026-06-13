package com.convivir.app.controller;

import com.convivir.app.model.Usuario;
import com.convivir.app.service.ResidenteService;
import com.convivir.app.service.UnidadService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/residentes")
public class ResidenteWebController {

    private final ResidenteService residenteService;
    private final UnidadService unidadService;

    public ResidenteWebController(ResidenteService residenteService,
                                   UnidadService unidadService) {
        this.residenteService = residenteService;
        this.unidadService = unidadService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("residentes", residenteService.listarResidentes());
        return "admin/residentes/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("titulo", "Nuevo Residente");
        model.addAttribute("unidades", unidadService.listarTodas());
        return "admin/residentes/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, Model model,
                         RedirectAttributes flash) {
        return residenteService.buscarPorId(id)
                .or(() -> residenteService.buscarPorObjectId(id))
                .map(usuario -> {
                    model.addAttribute("usuario", usuario);
                    model.addAttribute("titulo", "Editar Residente");
                    model.addAttribute("unidades", unidadService.listarTodas());
                    return "admin/residentes/formulario";
                }).orElseGet(() -> {
                    flash.addFlashAttribute("error", "Residente no encontrado");
                    return "redirect:/admin/residentes";
                });
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Usuario usuario,
                          RedirectAttributes flash) {
        try {
            if (usuario.getId() != null && usuario.getId().trim().isEmpty()) {
                usuario.setId(null);
            }
            residenteService.guardar(usuario);
            flash.addFlashAttribute("exito", "Residente guardado correctamente");
        } catch (RuntimeException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/residentes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, RedirectAttributes flash) {
        try {
            residenteService.eliminarPorObjectId(id);
            flash.addFlashAttribute("exito", "Residente eliminado correctamente");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al eliminar el residente");
        }
        return "redirect:/admin/residentes";
    }
}