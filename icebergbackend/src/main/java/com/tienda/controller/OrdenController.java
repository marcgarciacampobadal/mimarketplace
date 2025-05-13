package com.tienda.controller;

import com.tienda.model.Orden;
import com.tienda.service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    // Crea una nueva orden desde el carrito
    @PostMapping
    public ResponseEntity<Orden> crearOrden(@RequestParam Long usuarioId) {
        try {
            Orden orden = ordenService.crearOrdenDesdeCarrito(usuarioId);
            return ResponseEntity.ok(orden);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Obtiene todas las órdenes
    @GetMapping
    public ResponseEntity<List<Orden>> obtenerTodas() {
        return ResponseEntity.ok(ordenService.obtenerTodas());
    }

    // Obtiene órdenes por usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Orden>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(ordenService.obtenerPorUsuario(usuarioId));
    }

    // Obtiene detalles de una orden específica
    @GetMapping("/{ordenId}")
    public ResponseEntity<Orden> obtenerPorId(@PathVariable Long ordenId) {
        return ordenService.obtenerPorId(ordenId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualiza el estado de una orden
    @PatchMapping("/{ordenId}/estado")
    public ResponseEntity<Orden> actualizarEstado(
            @PathVariable Long ordenId,
            @RequestParam String nuevoEstado) {
        try {
            return ResponseEntity.ok(ordenService.actualizarEstado(ordenId, nuevoEstado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Cancela una orden
    @DeleteMapping("/{ordenId}")
    public ResponseEntity<Void> cancelarOrden(@PathVariable Long ordenId) {
        try {
            ordenService.cancelarOrden(ordenId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
