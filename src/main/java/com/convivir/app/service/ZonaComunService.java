package com.convivir.app.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.convivir.app.model.ZonaComun;
import com.convivir.app.repository.ZonaComunRepository;

@Service
public class ZonaComunService {

    private final ZonaComunRepository zonaComunRepository;

    public ZonaComunService(ZonaComunRepository zonaComunRepository) {
        this.zonaComunRepository = zonaComunRepository;
    }

    
    public List<ZonaComun> listarTodas() {
        return zonaComunRepository.findAll();
    }

    
    public List<ZonaComun> listarDisponibles() {
        return zonaComunRepository.findAll(); 
    }

    
    public ZonaComun buscarPorId(String id) {
        return zonaComunRepository.findById(id).orElse(null);
    }

    
    public void guardar(ZonaComun zona) {
        zonaComunRepository.save(zona);
    }

    
    public void eliminar(String id) {
        zonaComunRepository.deleteById(id);
    }
}