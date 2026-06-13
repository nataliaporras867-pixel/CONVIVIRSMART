package com.convivir.app.controller;

import com.convivir.app.model.Noticia;
import com.convivir.app.service.NoticiaService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    
    private final String UPLOAD_DIR = "src/main/resources/static/uploads/";

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
                
                Files.createDirectories(path.getParent());
                Files.write(path, file.getBytes());
                
                
                noticia.setUrlImagen("/uploads/" + nombreArchivo);
            }
            
            noticiaService.guardar(noticia);
        } catch (Exception e) {
            System.err.println("Error al procesar la imagen: " + e.getMessage());
        }
        return "redirect:/admin/noticias"; 
    }

    @GetMapping("/uploads/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> servirImagen(@PathVariable String filename) {
        try {
            Path path = Paths.get(UPLOAD_DIR + filename);
            Resource resource = new FileSystemResource(path);
            
            
            String contentType = Files.probeContentType(path);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
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