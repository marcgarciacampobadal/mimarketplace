package com.tienda.repository;

import com.tienda.model.Carrito;
import com.tienda.model.CarritoItem;
import com.tienda.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {

    // Busca items del carrito
    List<CarritoItem> findByCarritoId(Long carritoId);

    // Busca un item específico por carrito y producto para evitar duplicados
    Optional<CarritoItem> findByCarritoAndProducto(Carrito carrito, Producto producto);

    // Elimina todos los items del carrito
    @Modifying
    @Query("DELETE FROM CarritoItem ci WHERE ci.carrito.id = :carritoId")
    void deleteAllByCarritoId(Long carritoId);

    // Cuenta los intems del carrito
    int countByCarritoId(Long carritoId);
}