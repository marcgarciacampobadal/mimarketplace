package com.tienda.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FirebaseUser {
    private String uid;
    private String email;
    private String nombre;
}
