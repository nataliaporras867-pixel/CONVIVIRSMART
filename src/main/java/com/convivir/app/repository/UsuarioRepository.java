package com.convivir.app.repository;

import com.convivir.app.model.Usuario;
import com.convivir.app.model.Usuario.Rol;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
	
    Optional<Usuario> findByTokenRecuperacion(String token);
    

    Optional<Usuario> findByCorreo(String correo);
    List<Usuario> findByRol(Rol residente);
    List<Usuario> findByRol(String rol);
    List<Usuario> findByEstado(boolean estado);
    boolean existsByCorreo(String correo);

    @Query("{ '_id': { $oid: ?0 } }")
    Optional<Usuario> findByObjectId(String id);
}