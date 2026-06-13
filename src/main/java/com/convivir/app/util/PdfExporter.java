package com.convivir.app.util;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

public class PdfExporter {
    public static void export(List<String[]> data, String[] headers, String title, HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        
        document.open();
        
        // Título
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        document.add(new Paragraph(title, fontTitle));
        document.add(new Paragraph("\n"));

        // Tabla
        PdfPTable table = new PdfPTable(headers.length);
        table.setWidthPercentage(100);

        // Cabeceras
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header));
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            table.addCell(cell);
        }

        // Datos
        for (String[] rowData : data) {
            for (String cellData : rowData) {
                table.addCell(cellData);
            }
        }

        document.add(table);
        document.close();
    }
}