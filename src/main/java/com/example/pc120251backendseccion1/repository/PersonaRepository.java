package com.example.pc120251backendseccion1.repository;

import com.example.pc120251backendseccion1.enums.EstadoCivil;
import com.example.pc120251backendseccion1.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, String> {

    // Buscar por nombre o apellido (usado para filtros)
    List<Persona> findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase(String nombre, String apellido);

    // Buscar por estado civil
    List<Persona> findByEstadoCivil(EstadoCivil estadoCivil);

    // Buscar hijos por padre o madre
    List<Persona> findByPadreDniOrMadreDni(String dniPadre, String dniMadre);

    // Buscar descendientes (para validar si puede eliminarse)
    @Query("SELECT p FROM Persona p WHERE p.padre.dni = ?1 OR p.madre.dni = ?1")
    List<Persona> findDescendientes(String dni);
}