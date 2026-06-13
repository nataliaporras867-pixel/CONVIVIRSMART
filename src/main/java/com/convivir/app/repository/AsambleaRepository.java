package com.convivir.app.repository;

import com.convivir.app.model.Asamblea;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsambleaRepository extends MongoRepository<Asamblea, String> {
    
}