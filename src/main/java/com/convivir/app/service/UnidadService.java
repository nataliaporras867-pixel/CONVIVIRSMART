package com.convivir.app.service;

import com.convivir.app.model.Unidad;
import com.convivir.app.repository.UnidadRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UnidadService {

    private final UnidadRepository unidadRepository;

    public UnidadService(UnidadRepository unidadRepository) {
        this.unidadRepository = unidadRepository;
    }

    public List<Unidad> listarTodas() {
        return unidadRepository.findAll();
    }
    
    public Unidad findById(String id) {
       
        return unidadRepository.findById(id).orElse(null);
    }
    
    public List<Unidad> findByPropietarioEmail(String email) {
        return unidadRepository.findByPropietarioEmail(email);
        
    }

    public Optional<Unidad> buscarPorId(String id) {
        return unidadRepository.findById(id);
    }

    public void guardar(Unidad unidad) {
        if (unidad.getId() != null && unidad.getId().trim().isEmpty()) {
            unidad.setId(null);
        }
        if (unidad.getEstado() == null || unidad.getEstado().isEmpty()) {
            unidad.setEstado("DISPONIBLE");
        }
        unidad.setFechaActualizacion(LocalDateTime.now());
        if (unidad.getId() == null) {
            unidad.setFechaCreacion(LocalDateTime.now());
        }
        unidadRepository.save(unidad);
    }
    
    public void registrarYVincular(Unidad unidad, String emailPropietario) {
        
        unidad.setPropietarioEmail(emailPropietario);
        guardar(unidad);
    }

    public void eliminar(String id) {
        unidadRepository.deleteById(id);
    }
}