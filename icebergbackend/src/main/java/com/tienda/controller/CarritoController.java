package com.tienda.controller;

import com.tienda.model.Carrito;
import com.tienda.model.Orden;
import com.tienda.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    // Añadir producto al carrito
    @PostMapping("/{uid}/agregar")
    public ResponseEntity<Carrito> agregarProducto(
            @PathVariable String uid,
            @RequestParam Long productoId,
            @RequestParam(defaultValue = "1") int cantidad) {

        String usuarioUid = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Carrito carrito = carritoService.agregarProducto(usuarioUid, productoId, cantidad);
        return ResponseEntity.ok(carrito);
    }

    // Ver el carrito
    @GetMapping("/{uid}")
    public ResponseEntity<Carrito> obtenerCarrito(@PathVariable String uid) {
        String usuarioUid = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Carrito carrito = carritoService.obtenerCarrito(usuarioUid);
        return ResponseEntity.ok(carrito);
    }

    // Elimina producto del carrito
    @DeleteMapping("/{uid}/eliminar")
    public ResponseEntity<Void> eliminarProducto(
            @PathVariable String uid,
            @RequestParam Long productoId) {

        String usuarioUid = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        carritoService.eliminarProducto(usuarioUid, productoId);
        return ResponseEntity.noContent().build();
    }

    // Actualiza cantidad de un producto en el carrito
    @PutMapping("/{uid}/actualizar")
    public ResponseEntity<Carrito> actualizarCantidad(
            @PathVariable String uid,
            @RequestParam Long productoId,
            @RequestParam int cantidad) {

        String usuarioUid = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Carrito carrito = carritoService.actualizarCantidad(usuarioUid, productoId, cantidad);
        return ResponseEntity.ok(carrito);
    }

    // Finaliza la compra (convertir carrito a orden)
    @PostMapping("/{uid}/finalizar")
    public ResponseEntity<Orden> finalizarCompra(@PathVariable String uid) {
        String usuarioUid = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Orden orden = carritoService.finalizarCompra(usuarioUid);
        return ResponseEntity.ok(orden);
    }
}
