package com.convivir.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.convivir.app.model.ZonaComun;
import com.convivir.app.service.ZonaComunService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ZonaComunWebController {

    private final ZonaComunService zonaComunService;
    
    // 1. Unificamos la ruta a la carpeta externa raíz
    private final String UPLOAD_DIR = "uploads/";

    public ZonaComunWebController(ZonaComunService zonaComunService) {
        this.zonaComunService = zonaComunService;
    }

    @GetMapping("/admin/zonas")
    public String gestionarZonas(Model model) {
        model.addAttribute("listaZonas", zonaComunService.listarTodas());
        model.addAttribute("nuevaZona", new ZonaComun());
        return "admin/gestion-zonas";
    }

    @PostMapping("/admin/zonas/guardar")
    public String guardarZona(@ModelAttribute("nuevaZona") ZonaComun zona, 
                              @RequestParam("file") MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                // Generamos un nombre único para evitar duplicados
                String nombreArchivo = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + nombreArchivo);
                
                // Crea la carpeta externa 'uploads' si no existe
                Files.createDirectories(path.getParent());
                Files.write(path, file.getBytes());
                
                // 2. CORRECCIÓN: Guardamos usando la ruta mapeada en MvcConfig
                zona.setImagenUrl("/uploads/" + nombreArchivo);
            } else {
                // Si no se sube un archivo nuevo, mantenemos la imagen existente
                if (zona.getId() != null && !zona.getId().isEmpty()) {
                    ZonaComun zonaExistente = zonaComunService.buscarPorId(zona.getId());
                    if (zonaExistente != null) {
                        zona.setImagenUrl(zonaExistente.getImagenUrl());
                    }
                }
            }
            
            zonaComunService.guardar(zona);
            
        } catch (Exception e) {
            System.err.println("Error al procesar la imagen de zona común: " + e.getMessage());
            return "redirect:/admin/zonas?errorImage";
        }
        
        return "redirect:/admin/zonas?success";
    }

    @GetMapping("/admin/zonas/editar/{id}")
    public String editarZona(@PathVariable("id") String id, Model model) {
        ZonaComun zona = zonaComunService.buscarPorId(id);
        if (zona != null) {
            model.addAttribute("nuevaZona", zona);
            model.addAttribute("listaZonas", zonaComunService.listarTodas());
            return "admin/gestion-zonas";
        }
        return "redirect:/admin/zonas";
    }

    @GetMapping("/admin/zonas/eliminar/{id}")
    public String eliminarZona(@PathVariable("id") String id) {
        zonaComunService.eliminar(id);
        return "redirect:/admin/zonas?deleted";
    }

    @GetMapping("/zonas-comunes")
    public String verZonasPublicas(Model model) {
        model.addAttribute("zonas", zonaComunService.listarTodas());
        return "public/zonas-comunes";
    }
}