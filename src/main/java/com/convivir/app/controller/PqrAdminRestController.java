package com.convivir.app.controller;

import com.convivir.app.model.Pqr;
import com.convivir.app.service.PqrService;
import com.convivir.app.service.EmailService; // 🚀 Inyectamos tu nuevo servicio de correos
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/admin/pqrs")
public class PqrAdminRestController {

    @Autowired
    private PqrService pqrService;

    @Autowired
    private EmailService emailService; 

    @PostMapping("/actualizar-estado")
    public void actualizarEstadoPqr(
            @RequestParam("pqrId") String pqrId,
            @RequestParam("nuevoEstado") String nuevoEstado,
            @RequestParam("comentario") String comentario,
            Principal principal,
            HttpServletResponse response) throws IOException {
        
        String adminName = (principal != null) ? principal.getName() : "Administrador";
        
        
        Pqr pqrActualizada = pqrService.cambiarEstado(pqrId, nuevoEstado, comentario, adminName);
        
        
        if (pqrActualizada != null) {
            try {
               
                emailService.enviarNotificacionEstado(pqrActualizada, comentario);
            } catch (Exception e) {
                
                System.err.println("❌ Error al procesar el envío del correo: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("⚠️ No se pudo enviar el correo porque la PQR con ID " + pqrId + " no existe.");
        }
        
        
        response.sendRedirect("/admin/pqrs/gestionar/" + pqrId + "?successActualizacion");
    }
}