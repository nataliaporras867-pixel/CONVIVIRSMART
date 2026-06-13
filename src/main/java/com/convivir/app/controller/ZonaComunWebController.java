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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ZonaComunWebController {

    private final ZonaComunService zonaComunService;
    
    // RUTA ABSOLUTA AUTOMÁTICA: Elige C:/ en Windows y la ruta fija del proyecto en Render (Linux)
    private final String UPLOAD_DIR = System.getProperty("os.name").toLowerCase().contains("win") 
            ? "C:/convivir_uploads/" 
            : "/opt/render/project/src/uploads/";

    public ZonaComunWebController(ZonaComunService zonaComunService) {
        this.zonaComunService = zonaComunService;
    }

    // 1. Vista de administración sincronizada con el menú Sidebar
    @GetMapping("/admin/zonas-comunes")
    public String gestionarZonas(Model model) {
        model.addAttribute("listaZonas", zonaComunService.listarTodas());
        model.addAttribute("nuevaZona", new ZonaComun());
        return "admin/gestion-zonas";
    }

    // 2. Procesamiento del formulario con almacenamiento en disco externo
    @PostMapping("/admin/zonas/guardar")
    public String guardarZona(@ModelAttribute("nuevaZona") ZonaComun zona, 
                              @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                // Generamos un nombre único basado en milisegundos para evitar conflictos
                String nombreArchivo = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                
                // Nos aseguramos de que la carpeta física exista en el sistema operativo
                File directorio = new File(UPLOAD_DIR);
                if (!directorio.exists()) {
                    directorio.mkdirs();
                }
                
                // Escribimos los bytes del archivo en la ruta absoluta elegida
                Path path = Paths.get(UPLOAD_DIR + nombreArchivo);
                Files.write(path, file.getBytes());
                
                // Guardamos en la base de datos la estructura virtual correspondiente al MvcConfig
                zona.setImagenUrl("/uploads/" + nombreArchivo);
            } else {
                // Si estamos editando y no se seleccionó un archivo nuevo, preservamos la imagen anterior
                if (zona.getId() != null && !zona.getId().isEmpty()) {
                    ZonaComun zonaExistente = zonaComunService.buscarPorId(zona.getId());
                    if (zonaExistente != null) {
                        zona.setImagenUrl(zonaExistente.getImagenUrl());
                    }
                }
            }
            
            zonaComunService.guardar(zona);
            
        } catch (Exception e) {
            System.err.println("Error crítico al procesar la imagen de zona común: " + e.getMessage());
            return "redirect:/admin/zonas-comunes?errorImage";
        }
        
        // Retorna con el parámetro correcto para disparar la alerta de éxito en tu HTML
        return "redirect:/admin/zonas-comunes?successZona";
    }

    // 3. Acción de edición cargando los datos en la misma plantilla
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

    // 4. Eliminación física lógica del registro de la zona
    @GetMapping("/admin/zonas/eliminar/{id}")
    public String eliminarZona(@PathVariable("id") String id) {
        zonaComunService.eliminar(id);
        return "redirect:/admin/zonas-comunes?deleted";
    }

    // 5. Vista pública para los residentes de la copropiedad
    @GetMapping("/zonas-comunes")
    public String verZonasPublicas(Model model) {
        model.addAttribute("zonas", zonaComunService.listarTodas());
        return "public/zonas-comunes";
    }
}