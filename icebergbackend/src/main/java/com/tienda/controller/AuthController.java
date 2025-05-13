package com.tienda.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.tienda.model.AuthResponse;
import com.tienda.security.JwtTokenFilter;
import com.tienda.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenFilter jwtTokenFilter;

    public AuthController(JwtTokenProvider jwtTokenProvider, JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(HttpServletRequest request) {
        System.out.println("ENTRANDO AL MÉTODO /api/auth/login");

        String token = jwtTokenFilter.extractToken(request);
        System.out.println("TOKEN EXTRAÍDO: " + token);

        try {
            FirebaseToken decodedToken = jwtTokenProvider.validateToken(token);
            System.out.println("Token validado correctamente");

            String uid = decodedToken.getUid();
            String email = decodedToken.getEmail();

            System.out.println("UID: " + uid);
            System.out.println("Email: " + email);

            String jwtToken = jwtTokenProvider.generateToken(decodedToken);
            System.out.println("JWT generado");

            String refreshToken = jwtTokenProvider.generateRefreshToken(decodedToken);
            System.out.println("Refresh token generado");

            AuthResponse response = new AuthResponse(jwtToken, refreshToken, email, uid);
            System.out.println("Enviando respuesta al cliente");

            return ResponseEntity.ok(response);

        } catch (FirebaseAuthException e) {
            System.out.println("Error de FirebaseAuth: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
