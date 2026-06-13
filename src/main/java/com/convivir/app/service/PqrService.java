package com.convivir.app.service;

import com.convivir.app.model.Pqr;
import com.convivir.app.repository.PqrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PqrService {

    @Autowired
    private PqrRepository pqrRepository;

    public Pqr registrarPqr(Pqr pqr, String residenteId) {
        pqr.setResidenteId(residenteId);
        if (pqr.getEstado() == null) {
            pqr.setEstado("PENDIENTE");
        }
        
        pqr.agregarHistorial("-", "PENDIENTE", "PQR radicada exitosamente por el residente.", "Sistema");
        return pqrRepository.save(pqr);
    }

    public List<Pqr> listarPorResidente(String residenteId) {
        return pqrRepository.findByResidenteId(residenteId);
    }

    
    public Optional<Pqr> buscarPorId(String id) {
        return pqrRepository.findById(id);
    }

    
    public Pqr cambiarEstado(String pqrId, String nuevoEstado, String comentario, String responsable) {
        Optional<Pqr> optionalPqr = pqrRepository.findById(pqrId);
        if (optionalPqr.isPresent()) {
            Pqr pqr = optionalPqr.get();
            String estadoAnterior = pqr.getEstado();
            
            pqr.setEstado(nuevoEstado);
            pqr.agregarHistorial(estadoAnterior, nuevoEstado, comentario, responsable);
            
            return pqrRepository.save(pqr);
        }
        return null;
    }

    public List<Pqr> listarTodas() {
        return pqrRepository.findAll();
    
	}
}