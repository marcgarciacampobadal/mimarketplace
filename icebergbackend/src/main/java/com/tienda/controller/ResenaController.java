package com.tienda.controller;

import com.tienda.dto.ResenaDTO;
import com.tienda.model.Resena;
import com.tienda.service.ResenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/resenas")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    @GetMapping
    public List<Resena> obtenerTodas() {
        return resenaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resena> obtenerPorId(@PathVariable Long id) {
        return resenaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/producto/{productoId}")
    public List<Resena> obtenerPorProducto(@PathVariable Long productoId) {
        return resenaService.obtenerPorProducto(productoId);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Resena> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return resenaService.obtenerPorUsuario(usuarioId);
    }

    @PostMapping
    public ResponseEntity<Resena> crearResena(@RequestBody ResenaDTO resenaDTO) {
        try {
            Resena resenaCreada = resenaService.crearResena(resenaDTO);
            return ResponseEntity.ok(resenaCreada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resena> actualizarResena(
            @PathVariable Long id, @RequestBody ResenaDTO resenaDTO) {
        try {
            Resena resenaActualizada = resenaService.actualizarResena(id, resenaDTO);
            return ResponseEntity.ok(resenaActualizada);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarResena(@PathVariable Long id) {
        try {
            resenaService.eliminarResena(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
