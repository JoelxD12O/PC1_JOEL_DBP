package com.example.pc120251backendseccion1.repository;

import com.example.pc120251backendseccion1.domain.Matrimonio;
import com.example.pc120251backendseccion1.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatrimonioRepository extends JpaRepository<Matrimonio, Long> {

    // Buscar matrimonios por cualquiera de los cónyuges
    List<Matrimonio> findByConyuge1OrConyuge2(Persona c1, Persona c2);

    // Ver si existe un matrimonio entre dos personas exactas
    boolean existsByConyuge1AndConyuge2(Persona c1, Persona c2);

    boolean existsByConyuge2AndConyuge1(Persona c1, Persona c2);
}