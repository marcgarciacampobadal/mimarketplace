package com.tienda.repository;

import com.tienda.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {

    // Busca el carrito del usuario
    Optional<Carrito> findByUsuarioId(Long usuarioId);

    // Verifica si un usuario tiene el carrito activo
    boolean existsByUsuarioId(Long usuarioId);

    // Elimina el carrito por ID de usuario
    void deleteByUsuarioId(Long usuarioId);
}