package com.convivir.app.controller;

import com.convivir.app.model.Pqr;
import com.convivir.app.service.PqrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/pqrs")
public class PqrAdminController {

    @Autowired
    private PqrService pqrService;

    
    @GetMapping
    public String listarPqrsAdmin(Model model) {
        List<Pqr> todas = pqrService.listarTodas();
        model.addAttribute("pqrs", todas);
        return "admin-pqr-lista";
    }

    @GetMapping("/gestionar/{id}")
    public String verGestionarPqr(@PathVariable("id") String id, Model model) {
        Optional<Pqr> pqrOpt = pqrService.buscarPorId(id);
        if (pqrOpt.isPresent()) {
            model.addAttribute("pqr", pqrOpt.get());
            
            return "admin-pqr-gestion"; 
        }
        return "redirect:/admin/pqrs";
    }
}