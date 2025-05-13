package com.tienda.service;

import com.tienda.model.*;
import com.tienda.repository.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private CarritoItemRepository carritoItemRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;

    // ---- Métodos principales ----

    @Transactional
    public Carrito agregarProducto(String uid, Long productoId, int cantidad) {
        Usuario usuario = usuarioRepository.findByUid(uid)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (producto.getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente");
        }

        Carrito carrito = carritoRepository.findByUsuarioId(usuario.getId())
                .orElseGet(() -> {
                    Carrito nuevoCarrito = new Carrito();
                    nuevoCarrito.setUsuario(usuario);
                    return carritoRepository.save(nuevoCarrito);
                });

        Optional<CarritoItem> itemExistente = carritoItemRepository.findByCarritoAndProducto(carrito, producto);
        CarritoItem item;

        if (itemExistente.isPresent()) {
            item = itemExistente.get();
            item.setCantidad(item.getCantidad() + cantidad);
        } else {
            item = new CarritoItem();
            item.setProducto(producto);
            item.setCantidad(cantidad);
            item.setCarrito(carrito);
        }

        carritoItemRepository.save(item);
        return carrito;
    }

    @Transactional
    public Carrito eliminarProducto(String uid, Long productoId) {
        Usuario usuario = usuarioRepository.findByUid(uid)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Carrito carrito = carritoRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        CarritoItem item = carritoItemRepository.findByCarritoAndProducto(carrito, producto)
                .orElseThrow(() -> new RuntimeException("Producto no está en el carrito"));

        carritoItemRepository.delete(item);

        // 🔄 Recargar el carrito actualizado desde la base de datos
        return carritoRepository.findById(carrito.getId())
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado después de eliminar"));
    }

    @Transactional
    public Carrito actualizarCantidad(String uid, Long productoId, int cantidad) {
        if (cantidad <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a cero");
        }

        Usuario usuario = usuarioRepository.findByUid(uid)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Carrito carrito = carritoRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (producto.getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente");
        }

        CarritoItem item = carritoItemRepository.findByCarritoAndProducto(carrito, producto)
                .orElseThrow(() -> new RuntimeException("Producto no está en el carrito"));

        item.setCantidad(cantidad);
        carritoItemRepository.save(item);
        return carrito;
    }

    @Transactional
    public Orden finalizarCompra(String uid) {
        Usuario usuario = usuarioRepository.findByUid(uid)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Carrito carrito = carritoRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        if (carrito.getItems().isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        Orden orden = new Orden();
        orden.setUsuario(usuario);
        orden.setCarrito(carrito);
        ordenRepository.save(orden);

        for (CarritoItem item : carrito.getItems()) {
            DetalleOrden detalle = new DetalleOrden();
            detalle.setOrden(orden);
            detalle.setProducto(item.getProducto());
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(item.getProducto().getPrecio());
            detalleOrdenRepository.save(detalle);

            Producto producto = item.getProducto();
            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);
        }

        carritoItemRepository.deleteAllByCarritoId(carrito.getId());
        return orden;
    }

    // ---- Métodos auxiliares ----

    public Carrito obtenerCarrito(String uid) {
        Usuario usuario = usuarioRepository.findByUid(uid)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return carritoRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
    }

    public List<CarritoItem> obtenerItemsDelCarrito(String uid) {
        Carrito carrito = obtenerCarrito(uid);
        return carritoItemRepository.findByCarritoId(carrito.getId());
    }
}
