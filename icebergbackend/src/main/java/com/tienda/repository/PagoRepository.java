package com.tienda.repository;

import com.tienda.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    // Método para buscar por ID de transacción porque es necesario para Stripe
    Optional<Pago> findByIdTransaccion(String idTransaccion);

    // Método para listar pagos de una orden específica
    List<Pago> findByOrdenId(Long ordenId);

    // Método para buscar pagos por estado
    List<Pago> findByEstado(String estado);
}