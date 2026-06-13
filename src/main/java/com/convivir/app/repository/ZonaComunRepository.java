package com.convivir.app.repository;

import com.convivir.app.model.ZonaComun;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ZonaComunRepository extends MongoRepository<ZonaComun, String> {
    List<ZonaComun> findByDisponibleTrue();
}