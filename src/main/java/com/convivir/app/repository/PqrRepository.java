package com.convivir.app.repository;

import com.convivir.app.model.Pqr;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PqrRepository extends MongoRepository<Pqr, String> {
    
    List<Pqr> findByResidenteId(String residenteId);
}