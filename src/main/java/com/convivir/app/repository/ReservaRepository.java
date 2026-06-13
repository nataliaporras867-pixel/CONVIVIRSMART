package com.convivir.app.repository;

import com.convivir.app.model.Reserva;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends MongoRepository<Reserva, String> {
    
    List<Reserva> findByResidenteId(String residenteId);
   
    List<Reserva> findByZonaComunIdAndEstadoAndFechaHoraInicioBeforeAndFechaHoraFinAfter(
        String zonaComunId, 
        Reserva.EstadoReserva estado,
        LocalDateTime fechaHoraFinSolicitada, 
        LocalDateTime fechaHoraInicioSolicitada
    );
}