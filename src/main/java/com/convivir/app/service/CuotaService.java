package com.convivir.app.service;

import com.convivir.app.model.Cuota;
import com.convivir.app.model.Usuario;
import com.convivir.app.repository.CuotaRepository;
import com.convivir.app.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CuotaService {

    private final CuotaRepository cuotaRepository;
    private final UsuarioRepository usuarioRepository;

    public CuotaService(CuotaRepository cuotaRepository, UsuarioRepository usuarioRepository) {
        this.cuotaRepository = cuotaRepository;
        this.usuarioRepository = usuarioRepository;
    }
    public void generarCuotasMensuales(double valor, String mes, String descripcion) {
        
        List<Usuario> residentes = usuarioRepository.findByRol("RESIDENTE"); 
        
        if (residentes.isEmpty()) {
            throw new RuntimeException("No se encontraron residentes registrados.");
        }
        
        for (Usuario usuario : residentes) {
            boolean existe = cuotaRepository.existsByResidenteIdAndMes(usuario.getId(), mes);
            
            if (!existe) {
                Cuota cuota = new Cuota();
                cuota.setResidenteId(usuario.getId());
                cuota.setValor(valor);
                cuota.setMes(mes);
                cuota.setDescripcion(descripcion);
                cuota.setEstado("PENDIENTE");
                cuotaRepository.save(cuota);
            }
        }
    }
    

    public void registrarPago(String id) {
        Cuota cuota = cuotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuota no encontrada con ID: " + id));
        cuota.setEstado("PAGADO");
        cuotaRepository.save(cuota);
    }

    public void eliminarCuota(String id) {
        if (cuotaRepository.existsById(id)) {
            cuotaRepository.deleteById(id);
        }
    }

    public List<Cuota> listarTodas() {
        return cuotaRepository.findAll();
    }
    
    public List<Cuota> obtenerMorosos() {
        return cuotaRepository.findByEstado("PENDIENTE");
    }

    public List<Cuota> listarPorResidente(String residenteId) {
        return cuotaRepository.findByResidenteId(residenteId);
    }

    public void cambiarEstado(String id, String estado) {
        Cuota cuota = cuotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuota no encontrada"));
        cuota.setEstado(estado);
        cuotaRepository.save(cuota);
    }
}