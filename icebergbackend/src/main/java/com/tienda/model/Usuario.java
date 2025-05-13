package com.tienda.model;

import lombok.Data;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uid; // Para Firebase
    private String email;

    private String nombre;
    private String correo;
    private String contrasena;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore // Evita bucle con Orden → Usuario → Orden
    private List<Orden> ordenes;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Evita bucle con Resena → Usuario → Resena
    private List<Resena> resenas;
}
