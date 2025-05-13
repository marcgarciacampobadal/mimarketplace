package com.tienda.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.tienda.dto.UsuarioRequest;
import com.tienda.model.Usuario;
import com.tienda.repository.UsuarioRepository;
import com.tienda.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private FirebaseAuth firebaseAuth;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    // CREA USUARIO EN FIREBASE
    @PostMapping("/crear")
    public ResponseEntity<String> crearUsuario(@RequestHeader("Authorization") String authorization,
            @RequestBody UsuarioRequest usuarioRequest) {
        try {
            String token = authorization.replace("Bearer ", "");
            firebaseAuth.verifyIdToken(token);

            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(usuarioRequest.getCorreo())
                    .setPassword(usuarioRequest.getContraseña())
                    .setDisplayName(usuarioRequest.getNombre());

            UserRecord userRecord = firebaseAuth.createUser(request);
            return ResponseEntity.ok("Usuario creado con éxito: " + userRecord.getUid());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear usuario: " + e.getMessage());
        }
    }

    // ACTUALIZA USUARIO EN FIREBASE
    @PutMapping("/actualizar/{uid}")
    public ResponseEntity<String> actualizarUsuario(@RequestHeader("Authorization") String authorization,
            @PathVariable String uid, @RequestBody UsuarioRequest usuarioRequest) {
        try {
            String token = authorization.replace("Bearer ", "");
            firebaseAuth.verifyIdToken(token);

            UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid)
                    .setEmail(usuarioRequest.getCorreo())
                    .setDisplayName(usuarioRequest.getNombre());

            UserRecord userRecord = firebaseAuth.updateUser(request);
            return ResponseEntity.ok("Usuario actualizado con éxito: " + userRecord.getUid());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar usuario: " + e.getMessage());
        }
    }

    // OBTIENE USUARIO DESDE FIREBASE
    @GetMapping("/{uid}")
    public ResponseEntity<Object> obtenerUsuario(@RequestHeader("Authorization") String authorization,
            @PathVariable String uid) {
        try {
            String token = authorization.replace("Bearer ", "");
            firebaseAuth.verifyIdToken(token);

            UserRecord userRecord = firebaseAuth.getUser(uid);
            return ResponseEntity.ok(userRecord);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Usuario no encontrado: " + e.getMessage());
        }
    }

    // ELIMINA USUARIO EN FIREBASE
    @DeleteMapping("/eliminar/{uid}")
    public ResponseEntity<String> eliminarUsuario(@RequestHeader("Authorization") String authorization,
            @PathVariable String uid) {
        try {
            String token = authorization.replace("Bearer ", "");
            firebaseAuth.verifyIdToken(token);

            firebaseAuth.deleteUser(uid);
            return ResponseEntity.ok("Usuario eliminado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar usuario: " + e.getMessage());
        }
    }

    // REGISTRO USUARIO EN LA BASE DE DATOS SI NO EXISTE
    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuarioDatos) {
        Optional<Usuario> existente = usuarioRepository.findByUid(usuarioDatos.getUid());
        if (existente.isPresent())
            return ResponseEntity.ok(existente.get());

        Usuario nuevo = new Usuario();
        nuevo.setUid(usuarioDatos.getUid());
        nuevo.setEmail(usuarioDatos.getEmail());
        nuevo.setNombre(usuarioDatos.getNombre());
        nuevo.setCorreo(usuarioDatos.getCorreo());
        nuevo.setContrasena(usuarioDatos.getContrasena());
        usuarioRepository.save(nuevo);

        return ResponseEntity.ok(nuevo);
    }

    // OBTIENE USUARIO DESDE LA BASE DE DATOS
    @GetMapping("/db/{uid}")
    public ResponseEntity<Usuario> obtenerUsuarioDesdeDB(@PathVariable String uid) {
        return usuarioRepository.findByUid(uid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // para obtenerr usuario por UID
    @GetMapping("/porUid/{uid}")
    public ResponseEntity<Usuario> obtenerPorUid(
            @RequestHeader("Authorization") String authorization,
            @PathVariable String uid) {
        try {
            String token = authorization.replace("Bearer ", "");
            firebaseAuth.verifyIdToken(token); // Validar token

            return usuarioService.obtenerPorUid(uid)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
