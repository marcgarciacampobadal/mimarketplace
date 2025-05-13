package com.tienda.dto;

import lombok.Data;

@Data
public class StripePagoRequest {
    private Long ordenId;
    private Double monto;
    private String moneda;
}