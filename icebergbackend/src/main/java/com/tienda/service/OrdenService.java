package com.tienda.service;

import com.tienda.model.Orden;
import com.tienda.model.Pago;
import com.tienda.model.Usuario;
import com.tienda.model.enums.EstadoOrden;
import com.tienda.repository.OrdenRepository;
import com.tienda.repository.UsuarioRepository;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrdenService {

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PagoService pagoService;

    // Obtención todas las órdenes
    public List<Orden> obtenerTodas() {
        return ordenRepository.findAll();
    }

    // Obtención una orden por ID
    public Optional<Orden> obtenerPorId(Long id) {
        return ordenRepository.findById(id);
    }

    // Obtención órdenes por usuario
    public List<Orden> obtenerPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        return ordenRepository.findByUsuario(usuario);
    }

    // Crea una nueva orden desde el carrito
    @Transactional
    public Orden crearOrdenDesdeCarrito(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Orden orden = new Orden();
        orden.setUsuario(usuario);
        orden.setEstado(EstadoOrden.PENDIENTE_PAGO);
        orden.setTotal(0.0);

        return ordenRepository.save(orden);
    }

    // Crea una orden directamente
    @Transactional
    public Orden crearOrden(Orden orden) {
        if (orden.getUsuario() == null) {
            throw new IllegalArgumentException("La orden debe tener un usuario asociado");
        }

        if (orden.getDetallesOrden() == null || orden.getDetallesOrden().isEmpty()) {
            throw new IllegalArgumentException("La orden debe contener al menos un producto");
        }

        if (orden.getTotal() == null) {
            double total = orden.getDetallesOrden().stream()
                    .mapToDouble(d -> d.getPrecioUnitario() * d.getCantidad())
                    .sum();
            orden.setTotal(total);
        }

        orden.setEstado(EstadoOrden.PENDIENTE_PAGO);
        return ordenRepository.save(orden);
    }

    // Actualiza estado de una orden
    @Transactional
    public Orden actualizarEstado(Long id, String nuevoEstado) {
        Orden orden = ordenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orden no encontrada con ID: " + id));

        EstadoOrden estado = EstadoOrden.valueOf(nuevoEstado.toUpperCase());

        if (orden.getEstado() == EstadoOrden.COMPLETADA && estado != EstadoOrden.COMPLETADA) {
            throw new IllegalStateException("No se puede modificar una orden completada");
        }

        orden.setEstado(estado);
        return ordenRepository.save(orden);
    }

    // Cancelación de orden
    @Transactional
    public void cancelarOrden(Long ordenId) {
        Orden orden = ordenRepository.findById(ordenId)
                .orElseThrow(() -> new EntityNotFoundException("Orden no encontrada"));

        if (orden.getEstado() == EstadoOrden.COMPLETADA || orden.getEstado() == EstadoOrden.PAGADA) {
            throw new IllegalStateException("No se puede cancelar una orden pagada o completada");
        }

        orden.setEstado(EstadoOrden.CANCELADA);
        ordenRepository.save(orden);
    }

    // Eliminación de orden
    @Transactional
    public void eliminarOrden(Long id) {
        Orden orden = ordenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orden no encontrada"));

        if (orden.getEstado() == EstadoOrden.COMPLETADA || orden.getEstado() == EstadoOrden.PAGADA) {
            throw new IllegalStateException("No se puede eliminar una orden pagada o completada");
        }

        if (!orden.getDetallesOrden().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar: La orden tiene detalles asociados");
        }

        ordenRepository.delete(orden);
    }

    // Método específico para integración con Stripe
    @Transactional
    public Orden procesarPagoExitoso(String paymentIntentId) {
        Pago pago = pagoService.obtenerPorIdTransaccion(paymentIntentId)
                .orElseThrow(() -> new EntityNotFoundException("Pago no encontrado"));

        Orden orden = pago.getOrden();
        orden.setEstado(EstadoOrden.PAGADA);

        pago.setEstado("COMPLETADO");
        pagoService.guardarPago(pago);

        return ordenRepository.save(orden);
    }
}
