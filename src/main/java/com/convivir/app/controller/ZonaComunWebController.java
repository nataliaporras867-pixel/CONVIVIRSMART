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
import com.convivir.app.service.UploadFileService; 
import java.io.IOException;

@Controller
public class ZonaComunWebController {

    private final ZonaComunService zonaComunService;
    private final UploadFileService uploadFileService; 

    
    public ZonaComunWebController(ZonaComunService zonaComunService, UploadFileService uploadFileService) {
        this.zonaComunService = zonaComunService;
        this.uploadFileService = uploadFileService;
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
            
            String nombreImagen = uploadFileService.saveImage(file);
            
            if (nombreImagen != null) {
                
                zona.setImagenUrl("/images/uploads/" + nombreImagen);
            } else {
                
                if (zona.getId() != null && !zona.getId().isEmpty()) {
                    ZonaComun zonaExistente = zonaComunService.buscarPorId(zona.getId());
                    if (zonaExistente != null) {
                        zona.setImagenUrl(zonaExistente.getImagenUrl());
                    }
                }
            }
            
           
            zonaComunService.guardar(zona);
            
        } catch (IOException e) {
            e.printStackTrace();
            
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