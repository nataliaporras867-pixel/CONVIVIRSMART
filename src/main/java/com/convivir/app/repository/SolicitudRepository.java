package com.convivir.app.repository;

import com.convivir.app.model.SolicitudIngreso;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface SolicitudRepository extends MongoRepository<SolicitudIngreso, String> {
    // Busca todas las solicitudes que tengan estado "APROBADA"
    List<SolicitudIngreso> findByEstado(String estado);
}