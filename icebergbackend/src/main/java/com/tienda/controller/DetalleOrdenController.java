package com.tienda.controller;

import com.tienda.model.DetalleOrden;
import com.tienda.service.DetalleOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/detalle-orden")
public class DetalleOrdenController {

    @Autowired
    private DetalleOrdenService detalleOrdenService;

    // Obtiene todos los detalles de las órdenes
    @GetMapping
    public List<DetalleOrden> obtenerDetalleOrdenes() {
        return detalleOrdenService.obtenerTodos();
    }

    // Obtiene un detalle de orden por ID
    @GetMapping("/{id}")
    public ResponseEntity<DetalleOrden> obtenerDetalleOrdenPorId(@PathVariable Long id) {
        Optional<DetalleOrden> detalleOrden = detalleOrdenService.obtenerPorId(id);
        return detalleOrden.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crea un nuevo detalle de orden
    @PostMapping
    public DetalleOrden crearDetalleOrden(@RequestBody DetalleOrden detalleOrden) {
        return detalleOrdenService.guardarDetalleOrden(detalleOrden);
    }

    // Actualiza un detalle de orden existente
    @PutMapping("/{id}")
    public ResponseEntity<DetalleOrden> actualizarDetalleOrden(@PathVariable Long id,
            @RequestBody DetalleOrden detalleOrden) {
        try {
            DetalleOrden detalleOrdenActualizado = detalleOrdenService.actualizarDetalleOrden(id, detalleOrden);
            return ResponseEntity.ok(detalleOrdenActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Elimina un detalle de orden por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetalleOrden(@PathVariable Long id) {
        detalleOrdenService.eliminarDetalleOrden(id);
        return ResponseEntity.noContent().build();
    }
}
