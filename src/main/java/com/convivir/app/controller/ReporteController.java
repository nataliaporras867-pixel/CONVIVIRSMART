package com.convivir.app.controller;

import com.convivir.app.service.CuotaService;
import com.convivir.app.util.ExcelExporter;
import com.convivir.app.util.PdfExporter;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/reportes")
public class ReporteController {

    @Autowired
    private CuotaService cuotaService;

    
    @GetMapping
    public String verPaginaReportes() {
        return "admin/reportes";
    }
    
    
    @GetMapping("/morosos/pdf")
    public void exportarMorososPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Reporte_Morosos.pdf");

        var morosos = cuotaService.listarTodas().stream()
                            .filter(c -> "PENDIENTE".equals(c.getEstado()))
                            .collect(Collectors.toList());

        List<String[]> data = new ArrayList<>();
        for (var c : morosos) {
            data.add(new String[]{
                c.getResidenteNombre(),
                c.getMes(),
                String.valueOf(c.getValor())
            });
        }

        String[] headers = {"Residente", "Mes", "Valor"};
        PdfExporter.export(data, headers, "Reporte de Residentes Morosos", response);
    }

    
    @GetMapping("/morosos/excel")
    public void exportarMorososExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Reporte_Morosos.xlsx");

        var morosos = cuotaService.listarTodas().stream()
                            .filter(c -> "PENDIENTE".equals(c.getEstado()))
                            .collect(Collectors.toList());

        List<String[]> data = new ArrayList<>();
        for (var c : morosos) {
            data.add(new String[]{
                c.getResidenteNombre(),
                c.getMes(),
                String.valueOf(c.getValor())
            });
        }

        String[] headers = {"Residente", "Mes", "Valor"};
        ExcelExporter.export(data, headers, "Reporte_Morosos.xlsx", response);
    }
}