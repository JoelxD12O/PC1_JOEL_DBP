package com.example.pc120251backendseccion1.controller;

import com.example.pc120251backendseccion1.dto.JwtAuthenticationResponse;
import com.example.pc120251backendseccion1.dto.SigninRequest;
import com.example.pc120251backendseccion1.dto.CreatePersonaRequest;
import com.example.pc120251backendseccion1.service.AuthenticationService;
import com.example.pc120251backendseccion1.domain.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    // Endpoint para registrar un nuevo usuario
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserAccount userAccount) {
        // Aquí puedes agregar lógica para validar los datos del nuevo usuario
        authenticationService.register(userAccount);  // Llama al servicio de registro
        return ResponseEntity.ok().build();
    }

    // Endpoint para hacer login y obtener el token JWT
    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody SigninRequest signinRequest) {
        // Autenticamos al usuario y generamos el token
        JwtAuthenticationResponse response = authenticationService.signin(signinRequest);
        return ResponseEntity.ok(response);
    }

    // Endpoint para obtener la información del usuario autenticado
    @GetMapping("/me")
    public ResponseEntity<UserAccount> getAuthenticatedUser() {
        // Aquí necesitas obtener al usuario autenticado desde el contexto de seguridad
        // Esto lo hace Spring Security automáticamente.
        UserAccount authenticatedUser = authenticationService.getAuthenticatedUser();
        return ResponseEntity.ok(authenticatedUser);
    }
}
