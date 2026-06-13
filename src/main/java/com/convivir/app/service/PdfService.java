package com.convivir.app.service;

import com.convivir.app.model.Unidad;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import java.io.OutputStream;
import java.time.LocalDate;

@Service
public class PdfService {

    public void generarPazYSalvo(Unidad unidad, OutputStream outputStream) throws Exception {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);
        document.open();

       
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        document.add(new Paragraph("CERTIFICADO DE PAZ Y SALVO", titleFont));
        document.add(Chunk.NEWLINE);

        
        document.add(new Paragraph("La administración de CONVIVIRSMART certifica que:"));
        document.add(new Paragraph("La unidad " + unidad.getNumero() + " de la torre " + unidad.getTorre() + 
                     " se encuentra a paz y salvo por todo concepto a la fecha de hoy: " + LocalDate.now()));
        
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Atentamente,"));
        document.add(new Paragraph("La Administración"));

        document.close();
    }
}