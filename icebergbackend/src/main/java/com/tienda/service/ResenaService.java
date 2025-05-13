package com.tienda.service;

import com.tienda.model.Resena;
import com.tienda.repository.ResenaRepository;
import com.tienda.dto.ResenaDTO;
import com.tienda.model.Usuario;
import com.tienda.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ResenaService {

    @Autowired
    private ResenaRepository resenaRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService;

    public List<Resena> obtenerTodas() {
        return resenaRepository.findAll();
    }

    public Optional<Resena> obtenerPorId(Long id) {
        return resenaRepository.findById(id);
    }

    public List<Resena> obtenerPorProducto(Long productoId) {
        return resenaRepository.findByProductoId(productoId);
    }

    public List<Resena> obtenerPorUsuario(Long usuarioId) {
        return resenaRepository.findByUsuarioId(usuarioId);
    }

    @Transactional
    public Resena crearResena(ResenaDTO resenaDTO) {
        validarCalificacion(resenaDTO.getCalificacion());

        Usuario usuario = usuarioService.obtenerPorId(resenaDTO.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Producto producto = productoService.obtenerPorId(resenaDTO.getProductoId())
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        // Verifica que el usuario haya ya reseñado el producto
        resenaRepository.findByUsuarioIdAndProductoId(usuario.getId(), producto.getId())
                .ifPresent(r -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT,
                            "Ya has dejado una reseña para este producto");
                });

        Resena resena = new Resena();
        resena.setComentario(resenaDTO.getComentario());
        resena.setCalificacion(resenaDTO.getCalificacion());
        resena.setUsuario(usuario);
        resena.setProducto(producto);

        return resenaRepository.save(resena);
    }

    @Transactional
    public Resena actualizarResena(Long id, ResenaDTO resenaDTO) {
        validarCalificacion(resenaDTO.getCalificacion());

        return resenaRepository.findById(id).map(resenaExistente -> {
            resenaExistente.setComentario(resenaDTO.getComentario());
            resenaExistente.setCalificacion(resenaDTO.getCalificacion());
            return resenaRepository.save(resenaExistente);
        }).orElseThrow(() -> new EntityNotFoundException("Resena no encontrada con ID: " + id));
    }

    @Transactional
    public void eliminarResena(Long id) {
        resenaRepository.deleteById(id);
    }

    private void validarCalificacion(Integer calificacion) {
        if (calificacion == null || calificacion < 1 || calificacion > 5) {
            throw new IllegalArgumentException("La calificación debe ser entre 1 y 5");
        }
    }
}
