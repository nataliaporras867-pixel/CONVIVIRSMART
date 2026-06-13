package com.convivir.app.service;

import com.convivir.app.model.Reserva;
import com.convivir.app.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaService {
	
	
	public void guardar(Reserva reserva) {
	    reservaRepository.save(reserva); 
	}

    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }

    public List<Reserva> listarPorResidente(String residenteId) {
        return reservaRepository.findByResidenteId(residenteId);
    }

    
    public void registrarReserva(Reserva reserva) {
        
        if (reserva.getFechaHoraInicio().isAfter(reserva.getFechaHoraFin()) || 
            reserva.getFechaHoraInicio().isEqual(reserva.getFechaHoraFin())) {
            throw new RuntimeException("La hora de inicio debe ser anterior a la hora de finalización.");
        }

        
        if (reserva.getFechaHoraInicio().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("No puedes agendar una reserva en una fecha o hora pasada.");
        }

        
        List<Reserva> cruces = reservaRepository.findByZonaComunIdAndEstadoAndFechaHoraInicioBeforeAndFechaHoraFinAfter(
                reserva.getZonaComunId(),
                Reserva.EstadoReserva.APROBADA, 
                reserva.getFechaHoraFin(),
                reserva.getFechaHoraInicio()
        );

        if (!cruces.isEmpty()) {
            throw new RuntimeException("Lo sentimos, este espacio ya se encuentra reservado y aprobado en el horario seleccionado.");
        }

        
        reserva.setEstado(Reserva.EstadoReserva.PENDIENTE); 
        reserva.setFechaSolicitud(LocalDateTime.now());
        reservaRepository.save(reserva);
    }
    public void actualizarEstado(String reservaId, Reserva.EstadoReserva nuevoEstado, String notas) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        
        reserva.setEstado(nuevoEstado);
        reserva.setNotasAdministracion(notas);
        reservaRepository.save(reserva);
    }
    
    public Reserva buscarPorId(String id) {
       
        return reservaRepository.findById(id).orElse(null);
    }
}