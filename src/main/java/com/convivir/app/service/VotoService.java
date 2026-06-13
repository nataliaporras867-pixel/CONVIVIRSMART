package com.convivir.app.service;

import com.convivir.app.dto.VotoDTO;
import com.convivir.app.model.Voto;
import com.convivir.app.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    public void registrar(String asambleaId, VotoDTO dto) {
        if (votoRepository.existsByAsambleaIdAndPropietarioId(asambleaId, dto.getPropietarioId())) {
            throw new IllegalStateException("El propietario ya votó en esta asamblea.");
        }
        Voto voto = new Voto(asambleaId, dto.getPropietarioId(), dto.getOpcionSeleccionada());
        votoRepository.save(voto);
    }

    public List<Voto> listarPorAsamblea(String asambleaId) {
        return votoRepository.findByAsambleaId(asambleaId);
    }

    public Map<String, Long> contarPorOpcion(String asambleaId) {
        return votoRepository.findByAsambleaId(asambleaId).stream()
                .collect(Collectors.groupingBy(Voto::getOpcionSeleccionada, Collectors.counting()));
    }

    public boolean yaVoto(String asambleaId, String propietarioId) {
        return votoRepository.existsByAsambleaIdAndPropietarioId(asambleaId, propietarioId);
    }
}
