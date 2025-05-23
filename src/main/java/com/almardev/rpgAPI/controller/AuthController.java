package com.almardev.rpgAPI.controller;

import com.almardev.rpgAPI.model.User;
import com.almardev.rpgAPI.service.UserService;
import com.almardev.rpgAPI.dto.AuthRequest;
import com.almardev.rpgAPI.dto.AuthResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // @Autowired // COMENTA ESTO
    // private UserService userService; // COMENTA ESTO

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthRequest request) {
        // System.out.println("Intento de registro recibido (seguridad comentada)"); // Opcional, para depurar
        return ResponseEntity.ok("Registro temporalmente desactivado para depuración.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {
        // System.out.println("Intento de login recibido (seguridad comentada)"); // Opcional, para depurar
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login temporalmente desactivado para depuración.");
    }
}