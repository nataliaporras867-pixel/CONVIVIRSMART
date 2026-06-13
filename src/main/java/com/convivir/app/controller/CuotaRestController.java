package com.convivir.app.controller;

import com.convivir.app.model.Cuota;
import com.convivir.app.service.CuotaService;
import com.convivir.app.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/cuotas")
public class CuotaRestController {

    private final CuotaService cuotaService;
    private final UsuarioRepository usuarioRepository;

    public CuotaRestController(CuotaService cuotaService, UsuarioRepository usuarioRepository) {
        this.cuotaService = cuotaService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/generar-mensuales")
    public ResponseEntity<List<Cuota>> generarYListar(
            @RequestParam double valor, 
            @RequestParam String mes, 
            @RequestParam String descripcion) {
        
        cuotaService.generarCuotasMensuales(valor, mes, descripcion);
        return ResponseEntity.ok(listarTodas());
    }
    
    @GetMapping
    public List<Cuota> listarTodas() {
        List<Cuota> cuotas = cuotaService.listarTodas();
        
        cuotas.forEach(cuota -> {
            usuarioRepository.findById(cuota.getResidenteId()).ifPresent(usuario -> {
                cuota.setResidenteNombre(usuario.getNombres());
            });
        });
        return cuotas;
    }
    
    @GetMapping("/morosos")
    public List<Cuota> listarMorosos() {
        List<Cuota> morosos = cuotaService.obtenerMorosos();
        morosos.forEach(cuota -> {
            usuarioRepository.findById(cuota.getResidenteId()).ifPresent(usuario -> {
                cuota.setResidenteNombre(usuario.getNombres());
            });
        });
        return morosos;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        cuotaService.eliminarCuota(id); 
        return ResponseEntity.ok().build(); 
    }

    @PostMapping("/{id}/confirmar-pago")
    public ResponseEntity<String> confirmarPago(@PathVariable String id) {
        
        cuotaService.registrarPago(id); 
        return ResponseEntity.ok("Pago confirmado");
    }


    @GetMapping("/mis-cuotas")
    public List<Cuota> listarMisCuotas(Principal principal) {
        var usuario = usuarioRepository.findByCorreo(principal.getName())
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return cuotaService.listarPorResidente(usuario.getId());
    }

    @PostMapping("/{id}/notificar-pago")
    public ResponseEntity<String> notificarPago(@PathVariable String id) {
        cuotaService.cambiarEstado(id, "EN_REVISION");
        return ResponseEntity.ok("Notificación enviada");
    }
}