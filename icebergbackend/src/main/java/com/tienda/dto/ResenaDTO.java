package com.tienda.dto;

import lombok.Data;

@Data
public class ResenaDTO {
    private String comentario;
    private Integer calificacion;
    private Long usuarioId;
    private Long productoId;
}