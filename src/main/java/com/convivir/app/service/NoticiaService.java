package com.convivir.app.service;

import com.convivir.app.model.Noticia;
import com.convivir.app.repository.NoticiaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NoticiaService {
    
    private final NoticiaRepository noticiaRepository;

    public NoticiaService(NoticiaRepository noticiaRepository) {
        this.noticiaRepository = noticiaRepository;
    }
    public Noticia buscarPorId(String id) {
        return noticiaRepository.findById(id).orElse(null);
    }

    public List<Noticia> listarTodas() {
        return noticiaRepository.findAll();
    }
    
    public void guardar(Noticia noticia) {
        noticiaRepository.save(noticia);
    }
    
    public void eliminar(String id) {
        noticiaRepository.deleteById(id);
    }
}