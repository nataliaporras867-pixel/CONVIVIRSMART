package com.convivir.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProveedorViewController {

    @GetMapping("/admin/proveedores")
    public String vista() {
        return "admin/proveedores"; // → templates/admin/proveedores.html
    }
}