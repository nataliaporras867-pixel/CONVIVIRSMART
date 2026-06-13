package com.convivir.app.service;

import com.convivir.app.model.Proveedor;
import com.convivir.app.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedor> listarTodos() {
        return proveedorRepository.findAll();
    }

    public List<Proveedor> listarActivos() {
        return proveedorRepository.findByActivoTrue();
    }

    public Proveedor obtenerPorId(String id) {
        return proveedorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Proveedor no encontrado: " + id));
    }

    public Proveedor crear(Proveedor proveedor) {
        if (proveedorRepository.existsByNit(proveedor.getNit())) {
            throw new RuntimeException("Ya existe un proveedor con ese NIT");
        }
        return proveedorRepository.save(proveedor);
    }

    public Proveedor actualizar(String id, Proveedor datos) {
        Proveedor existente = obtenerPorId(id);
        existente.setNombre(datos.getNombre());
        existente.setTelefono(datos.getTelefono());
        existente.setEmail(datos.getEmail());
        existente.setDireccion(datos.getDireccion());
        existente.setCiudad(datos.getCiudad());
        existente.setTipoServicio(datos.getTipoServicio());
        return proveedorRepository.save(existente);
    }

    public void eliminar(String id) {
        Proveedor proveedor = obtenerPorId(id);
        proveedor.setActivo(false); // Soft delete
        proveedorRepository.save(proveedor);
    }
}