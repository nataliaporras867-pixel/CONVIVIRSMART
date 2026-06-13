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
    
    // Unificamos la ruta a la carpeta externa raíz de Render

 // Reemplaza la línea vieja por esta ruta absoluta e inequívoca:
    private final String UPLOAD_DIR = System.getProperty("user.dir") + java.io.File.separator + "uploads" + java.io.File.separator;

    public ZonaComunWebController(ZonaComunService zonaComunService) {
        this.zonaComunService = zonaComunService;
    }

    // CORRECCIÓN: Ajustado para que coincida exactamente con el menú Sidebar de tu HTML
    @GetMapping("/admin/zonas-comunes")
    public String gestionarZonas(Model model) {
        model.addAttribute("listaZonas", zonaComunService.listarTodas());
        model.addAttribute("nuevaZona", new ZonaComun());
        return "admin/gestion-zonas"; // Retorna tu plantilla HTML
    }

    // CORRECCIÓN: Ruta de guardado unificada
    @PostMapping("/admin/zonas/guardar")
    public String guardarZona(@ModelAttribute("nuevaZona") ZonaComun zona, 
                              @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                System.out.println("¡Archivo detectado en Zona Comun!: " + file.getOriginalFilename());
                
                // Generamos un nombre único para evitar duplicados
                String nombreArchivo = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + nombreArchivo);
                
                // Crea la carpeta externa 'uploads' si no existe en el disco duro de Render
                Files.createDirectories(path.getParent());
                Files.write(path, file.getBytes());
                
                // Guardamos usando la ruta mapeada en MvcConfig
                zona.setImagenUrl("/uploads/" + nombreArchivo);
            } else {
                System.out.println("No se detectó un archivo nuevo para la Zona Común.");
                // Si es edición y no se sube un archivo nuevo, mantenemos la imagen existente
                if (zona.getId() != null && !zona.getId().isEmpty()) {
                    ZonaComun zonaExistente = zonaComunService.buscarPorId(zona.getId());
                    if (zonaExistente != null) {
                        zona.setImagenUrl(zonaExistente.getImagenUrl());
                    }
                }
            }
            
            zonaComunService.guardar(zona);
            
        } catch (Exception e) {
            System.err.println("Error fatal al procesar la imagen de zona común: " + e.getMessage());
            return "redirect:/admin/zonas-comunes?errorImage";
        }
        
        // CORRECCIÓN: Retorna '?successZona' para activar la alerta verde que tienes en tu HTML
        return "redirect:/admin/zonas-comunes?successZona";
    }

    // CORRECCIÓN: Rutas de edición y eliminación actualizadas para redireccionar al lugar correcto
    @GetMapping("/admin/zonas/editar/{id}")
    public String editarZona(@PathVariable("id") String id, Model model) {
        ZonaComun zona = zonaComunService.buscarPorId(id);
        if (zona != null) {
            model.addAttribute("nuevaZona", zona);
            model.addAttribute("listaZonas", zonaComunService.listarTodas());
            return "admin/gestion-zonas";
        }
        return "redirect:/admin/zonas-comunes";
    }

    @GetMapping("/admin/zonas/eliminar/{id}")
    public String eliminarZona(@PathVariable("id") String id) {
        zonaComunService.eliminar(id);
        return "redirect:/admin/zonas-comunes?deleted";
    }

    @GetMapping("/zonas-comunes")
    public String verZonasPublicas(Model model) {
        model.addAttribute("zonas", zonaComunService.listarTodas());
        return "public/zonas-comunes";
    }
}