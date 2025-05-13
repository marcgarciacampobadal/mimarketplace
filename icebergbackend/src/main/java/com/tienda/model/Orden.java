package com.tienda.model;

import com.tienda.model.enums.EstadoOrden;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING) // Esto convierte el enum a texto en la BBDD
    private EstadoOrden estado;

    private Double total;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "carrito_id")
    private Carrito carrito;

    @OneToMany(mappedBy = "orden")
    private List<DetalleOrden> detallesOrden;

    @OneToMany(mappedBy = "orden")
    private List<Pago> pagos;

    @Transient
    public Pago getUltimoPago() {
        if (pagos != null && !pagos.isEmpty()) {
            return pagos.get(pagos.size() - 1);
        }
        return null;
    }
}
