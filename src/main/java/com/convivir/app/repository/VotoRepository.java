package com.convivir.app.repository;

import com.convivir.app.model.Voto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VotoRepository extends MongoRepository<Voto, String> {
    
    
    List<Voto> findByAsambleaId(String asambleaId);
    
    
    boolean existsByAsambleaIdAndPropietarioId(String asambleaId, String propietarioId);
}