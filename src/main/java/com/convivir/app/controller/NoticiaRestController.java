package com.convivir.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.convivir.app.model.Noticia;
import com.convivir.app.service.NoticiaService;

@RestController
@RequestMapping("/api/noticias")
public class NoticiaRestController {

    private final NoticiaService noticiaService;

    public NoticiaRestController(NoticiaService noticiaService) {
        this.noticiaService = noticiaService;
    }

    @GetMapping
    public List<Noticia> listarNoticiasJson() {
        return noticiaService.listarTodas();
    }

    @GetMapping("/{id}")
    public Noticia obtenerNoticia(@PathVariable String id) {
        return noticiaService.buscarPorId(id);
    }
}