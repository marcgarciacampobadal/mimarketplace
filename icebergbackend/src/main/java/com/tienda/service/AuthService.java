package com.tienda.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.tienda.model.FirebaseUser;
import com.tienda.model.LoginRequest;
import com.tienda.model.RegistroRequest;
import com.tienda.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthService {

    // Este método es llamado por el AuthController para el login
    public FirebaseUser authenticateUser(LoginRequest request) {
        validarRequest(request);

        // Buscamos el user:
        try {
            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(request.getEmail());

            // Devolvemos un objeto útil:
            FirebaseUser firebaseUser = new FirebaseUser();
            firebaseUser.setUid(userRecord.getUid());
            firebaseUser.setEmail(userRecord.getEmail());
            firebaseUser.setNombre(userRecord.getDisplayName());

            return firebaseUser;

        } catch (FirebaseAuthException e) {
            throw new RuntimeException("No se pudo autenticar el usuario: " + e.getMessage());
        }
    }

    public String registrarUsuario(RegistroRequest request) throws FirebaseAuthException {
        if (!StringUtils.hasText(request.getEmail()) || !StringUtils.hasText(request.getPassword())) {
            throw new IllegalArgumentException("Email y contraseña son obligatorios");
        }

        UserRecord.CreateRequest createRequest = new UserRecord.CreateRequest()
                .setEmail(request.getEmail())
                .setPassword(request.getPassword())
                .setDisplayName(request.getNombre());

        UserRecord userRecord = FirebaseAuth.getInstance().createUser(createRequest);
        return userRecord.getUid();
    }

    public Usuario obtenerUsuario(String uid) throws FirebaseAuthException {
        UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
        return convertirAUsuario(userRecord);
    }

    private void validarRequest(LoginRequest request) {
        if (!StringUtils.hasText(request.getEmail()) || !StringUtils.hasText(request.getPassword())) {
            throw new IllegalArgumentException("Email y contraseña son obligatorios");
        }
    }

    private Usuario convertirAUsuario(UserRecord userRecord) {
        Usuario usuario = new Usuario();
        usuario.setUid(userRecord.getUid());
        usuario.setEmail(userRecord.getEmail());
        usuario.setNombre(userRecord.getDisplayName());

        return usuario;
    }
}