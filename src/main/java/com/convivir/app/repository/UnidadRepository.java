package com.convivir.app.repository;

import com.convivir.app.model.Unidad;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UnidadRepository extends MongoRepository<Unidad, String> {
    List<Unidad> findByTorre(String torre);
    List<Unidad> findByEstado(String estado);
    List<Unidad> findByPropietarioId(String propietarioId);
    Optional<Unidad> findByNumeroAndTorre(String numero, String torre);
    List<Unidad> findByPropietarioEmail(String email);
    
}