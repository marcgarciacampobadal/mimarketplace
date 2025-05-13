package com.tienda.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double monto;
    private String metodo;
    private String estado; // "PENDIENTE", "COMPLETADO", "FALLIDO", "REEMBOLSADO"
    private String idTransaccion; // ID de la transacción en Stripe

    @ManyToOne
    @JoinColumn(name = "orden_id")
    private Orden orden;
}