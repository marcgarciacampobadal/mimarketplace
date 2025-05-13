package com.tienda.service;

import com.tienda.model.DetalleOrden;
import com.tienda.repository.DetalleOrdenRepository;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DetalleOrdenService {

    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;

    public List<DetalleOrden> obtenerTodos() {
        return detalleOrdenRepository.findAll();
    }

    public Optional<DetalleOrden> obtenerPorId(Long id) {
        return detalleOrdenRepository.findById(id);
    }

    @Transactional
    public DetalleOrden guardarDetalleOrden(DetalleOrden detalleOrden) {
        if (detalleOrden.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        if (detalleOrden.getProducto() == null) {
            throw new IllegalArgumentException("El producto es obligatorio");
        }
        return detalleOrdenRepository.save(detalleOrden);
    }

    @Transactional
    public DetalleOrden actualizarDetalleOrden(Long id, DetalleOrden detalleActualizado) {
        return detalleOrdenRepository.findById(id).map(detalleExistente -> {
            detalleExistente.setCantidad(detalleActualizado.getCantidad());

            return detalleOrdenRepository.save(detalleExistente);
        }).orElseThrow(() -> new EntityNotFoundException("Detalle no encontrado con ID: " + id));
    }

    @Transactional
    public void eliminarDetalleOrden(Long id) {
        detalleOrdenRepository.deleteById(id);
    }
}