package com.convivir.app.repository;

import com.convivir.app.model.Proveedor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProveedorRepository extends MongoRepository<Proveedor, String> {
    List<Proveedor> findByActivoTrue();
    List<Proveedor> findByNombreContainingIgnoreCase(String nombre);
    boolean existsByNit(String nit);
}