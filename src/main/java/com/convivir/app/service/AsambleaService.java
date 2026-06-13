package com.convivir.app.service;

import com.convivir.app.model.Asamblea;
import com.convivir.app.repository.AsambleaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AsambleaService {

    @Autowired
    private AsambleaRepository asambleaRepository;

    public List<Asamblea> listarTodas() { return asambleaRepository.findAll(); }

    public Optional<Asamblea> buscarPorId(String id) { return asambleaRepository.findById(id); }

    public void guardar(Asamblea asamblea) { asambleaRepository.save(asamblea); }

    public void cerrar(String id) {
        asambleaRepository.findById(id).ifPresent(a -> { a.setAbierta(false); asambleaRepository.save(a); });
    }

    public void eliminar(String id) { asambleaRepository.deleteById(id); }
}