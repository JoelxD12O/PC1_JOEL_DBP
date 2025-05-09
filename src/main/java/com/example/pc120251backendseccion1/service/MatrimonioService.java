package com.example.pc120251backendseccion1.service;

import com.example.pc120251backendseccion1.enums.EstadoCivil;
import com.example.pc120251backendseccion1.domain.Matrimonio;
import com.example.pc120251backendseccion1.domain.Persona;
import com.example.pc120251backendseccion1.repository.MatrimonioRepository;
import com.example.pc120251backendseccion1.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MatrimonioService {

    @Autowired
    private MatrimonioRepository matrimonioRepo;

    @Autowired
    private PersonaRepository personaRepo;

    public boolean validarMatrimonio(String dni1, String dni2) {
        Persona p1 = personaRepo.findById(dni1).orElseThrow();
        Persona p2 = personaRepo.findById(dni2).orElseThrow();

        // Restricciones básicas (ejemplo)
        if (p1.getEstadoCivil() == EstadoCivil.CASADO || p2.getEstadoCivil() == EstadoCivil.CASADO) {
            return false;
        }

        // Aquí podrías agregar validaciones de consanguinidad (hermanos, tíos, etc.)

        return true;
    }

    public void registrarMatrimonio(String dni1, String dni2) {
        if (!validarMatrimonio(dni1, dni2)) {
            throw new RuntimeException("No se puede registrar el matrimonio por impedimentos legales.");
        }

        Persona p1 = personaRepo.findById(dni1).orElseThrow();
        Persona p2 = personaRepo.findById(dni2).orElseThrow();

        Matrimonio matrimonio = new Matrimonio();
        matrimonio.setConyuge1(p1);
        matrimonio.setConyuge2(p2);
        matrimonio.setFechaMatrimonio(LocalDate.now());

        p1.setEstadoCivil(EstadoCivil.CASADO);
        p2.setEstadoCivil(EstadoCivil.CASADO);

        personaRepo.save(p1);
        personaRepo.save(p2);
        matrimonioRepo.save(matrimonio);
    }
}
