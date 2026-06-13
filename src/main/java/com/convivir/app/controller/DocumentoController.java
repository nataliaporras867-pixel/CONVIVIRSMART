package com.convivir.app.controller;

import com.convivir.app.model.Unidad;
import com.convivir.app.service.PdfService;
import com.convivir.app.service.UnidadService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/propietario/documentos")
public class DocumentoController {

    @Autowired
    private PdfService pdfService;

    @Autowired
    private UnidadService unidadService;

    @GetMapping("/pazysalvo/{id}")
    public void descargarPazYSalvo(@PathVariable String id, HttpServletResponse response) {
        try {
            
            Unidad unidad = unidadService.findById(id);

            if (unidad == null || !unidad.isAlDia()) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Paz y salvo no disponible para esta unidad.");
                return;
            }

            
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=PazYSalvo_Unidad_" + unidad.getNumero() + ".pdf");

          
            pdfService.generarPazYSalvo(unidad, response.getOutputStream());
            
           
            response.flushBuffer();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}