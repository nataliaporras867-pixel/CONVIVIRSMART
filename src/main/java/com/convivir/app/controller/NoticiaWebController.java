package com.convivir.app.controller;

import com.convivir.app.model.Noticia;
import com.convivir.app.service.NoticiaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class NoticiaWebController {

    private final NoticiaService noticiaService;
    
    // 1. CORRECCIÓN: Ahora se guarda en una carpeta "uploads" externa en la raíz del proyecto
    private final String UPLOAD_DIR = "uploads/";

    public NoticiaWebController(NoticiaService noticiaService) {
        this.noticiaService = noticiaService;
    }
    
    @GetMapping("/admin/noticias")
    public String mostrarPanelNoticias(Model model) {
        model.addAttribute("nuevaNoticia", new Noticia()); 
        model.addAttribute("listaNoticias", noticiaService.listarTodas());
        return "admin/noticias-gestion"; 
    }
    
    @PostMapping("/admin/noticias/guardar")
    public String guardarNoticia(@ModelAttribute("nuevaNoticia") Noticia noticia, 
                                 @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                String nombreArchivo = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + nombreArchivo);
                
                // Crea la carpeta externa si no existe
                Files.createDirectories(path.getParent());
                Files.write(path, file.getBytes());
                
                // 2. CORRECCIÓN: Guardamos solo el nombre o la ruta web relativa limpia
                noticia.setUrlImagen("/uploads/" + nombreArchivo);
            }
            
            noticiaService.guardar(noticia);
        } catch (Exception e) {
            System.err.println("Error al procesar la imagen: " + e.getMessage());
        }
        return "redirect:/admin/noticias"; 
    }

    // 3. ELIMINADO: Quité el método @GetMapping("/uploads/{filename}") viejo.
    // Al haber creado 'MvcConfig', Spring Boot se encargará de mapear y servir
    // de forma eficiente y automática todas las imágenes de la carpeta externa.
    
    @GetMapping("/admin/noticias/eliminar/{id}")
    public String eliminarNoticia(@PathVariable String id) {
        noticiaService.eliminar(id); 
        return "redirect:/admin/noticias";
    }

    @GetMapping("/noticia/ver/{id}")
    public String verDetalle(@PathVariable String id, Model model) {
        Noticia noticia = noticiaService.buscarPorId(id);
        if (noticia == null) return "redirect:/residente/noticias"; 
        model.addAttribute("noticia", noticia);
        return "public/noticias-detalle"; 
    }
}