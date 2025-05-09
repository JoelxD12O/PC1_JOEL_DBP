package com.example.pc120251backendseccion1.service;

import com.example.pc120251backendseccion1.domain.UserAccount;
import com.example.pc120251backendseccion1.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountRepository.findByEmail(username);

        if (userAccount == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        return userAccount;  // Aquí estamos devolviendo el UserAccount que implementa UserDetails
    }
}
