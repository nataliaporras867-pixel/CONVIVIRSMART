package com.convivir.app.repository;

import com.convivir.app.model.Cuota;


import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CuotaRepository extends MongoRepository<Cuota, String> {
    boolean existsByResidenteIdAndMes(String residenteId, String mes);
    List<Cuota> findByResidenteId(String residenteId);
    List<Cuota> findByEstado(String estado);
   
}