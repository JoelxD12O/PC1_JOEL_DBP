package com.example.pc120251backendseccion1.service;

import com.example.pc120251backendseccion1.config.JwtService;
import com.example.pc120251backendseccion1.dto.JwtAuthenticationResponse;
import com.example.pc120251backendseccion1.dto.SigninRequest;
import com.example.pc120251backendseccion1.domain.UserAccount;
import com.example.pc120251backendseccion1.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Registrar un nuevo usuario
    public void register(UserAccount userAccount) {
        // Cifrar la contraseña antes de guardarla
        String encodedPassword = passwordEncoder.encode(userAccount.getPassword());
        userAccount.setPassword(encodedPassword);
        userAccountRepository.save(userAccount);  // Guardar el usuario en la base de datos
        }

    // Hacer login y obtener el token JWT
    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        UserAccount userAccount = userAccountRepository.findByEmail(request.getEmail());
        String jwt = jwtService.generateToken(userAccount.getUsername());

        return new JwtAuthenticationResponse(jwt);
    }

    // Obtener el usuario autenticado
    public UserAccount getAuthenticatedUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userAccountRepository.findByEmail(username);
    }
}
