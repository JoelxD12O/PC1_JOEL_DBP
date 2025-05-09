package com.example.pc120251backendseccion1.service;

import com.example.pc120251backendseccion1.enums.EstadoCivil;
import com.example.pc120251backendseccion1.domain.Matrimonio;
import com.example.pc120251backendseccion1.domain.Persona;
import com.example.pc120251backendseccion1.exception.MatrimonioNoPermitidoException;
import com.example.pc120251backendseccion1.repository.MatrimonioRepository;
import com.example.pc120251backendseccion1.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatrimonioService {

    @Autowired
    private MatrimonioRepository matrimonioRepository;

    @Autowired
    private PersonaRepository personaRepository;

    // 7. Registrar matrimonio entre dos personas
    public Matrimonio registrarMatrimonio(String dni1, String dni2) {
        Persona p1 = personaRepository.findById(dni1).orElseThrow();
        Persona p2 = personaRepository.findById(dni2).orElseThrow();

        // Validación de impedimentos antes de registrar el matrimonio
        if (!validarMatrimonio(dni1, dni2)) {
            throw new MatrimonioNoPermitidoException("Impedimentos legales para el matrimonio.");
        }

        // Registrar el matrimonio
        Matrimonio matrimonio = new Matrimonio();
        matrimonio.setConyuge1(p1);
        matrimonio.setConyuge2(p2);
        matrimonio.setFechaMatrimonio(java.time.LocalDate.now());

        // Cambiar el estado civil de ambos
        p1.setEstadoCivil(EstadoCivil.CASADO);
        p2.setEstadoCivil(EstadoCivil.CASADO);

        personaRepository.save(p1);
        personaRepository.save(p2);
        return matrimonioRepository.save(matrimonio);
    }

    // 8. Validar impedimentos matrimoniales
    public boolean validarMatrimonio(String dni1, String dni2) {
        Persona p1 = personaRepository.findById(dni1).orElseThrow();
        Persona p2 = personaRepository.findById(dni2).orElseThrow();

        // Validación de si ya están casados
        if (p1.getEstadoCivil() == EstadoCivil.CASADO || p2.getEstadoCivil() == EstadoCivil.CASADO) {
            return false;
        }

        // Validación: consanguinidad (padre-hija, madre-hijo, etc.)
        if (p1.getPadre() != null && p1.getPadre().getDni().equals(p2.getDni())) {
            return false; // Prohibido matrimonio entre padres e hijos
        }
        if (p2.getPadre() != null && p2.getPadre().getDni().equals(p1.getDni())) {
            return false; // Prohibido matrimonio entre padres e hijos
        }

        // Validación: hermanos (consanguinidad colateral)
        if (p1.getPadre() != null && p2.getPadre() != null && p1.getPadre().getDni().equals(p2.getPadre().getDni())) {
            return false; // Prohibido matrimonio entre hermanos
        }

        // Si pasa todas las validaciones, el matrimonio es permitido
        return true;
    }
}
