package com.tienda.service;

import com.tienda.model.Producto;
import com.tienda.repository.ProductoRepository;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }

    public Producto guardarProducto(Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        if (producto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
        return productoRepository.save(producto);
    }

    public Producto actualizarProducto(Long id, Producto productoActualizado) {
        return productoRepository.findById(id).map(productoExistente -> {
            productoExistente.setNombre(productoActualizado.getNombre());
            productoExistente.setPrecio(productoActualizado.getPrecio());
            productoExistente.setDescripcion(productoActualizado.getDescripcion());
            if (productoActualizado.getCategoria() == null) {
                throw new IllegalArgumentException("La categoría es obligatoria");
            }
            productoExistente.setCategoria(productoActualizado.getCategoria());
            return productoRepository.save(productoExistente);
        }).orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + id));
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}