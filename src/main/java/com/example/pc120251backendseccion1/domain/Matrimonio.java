package com.example.pc120251backendseccion1.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Matrimonio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dni_conyuge1", nullable = false)
    private Persona conyuge1;

    @ManyToOne
    @JoinColumn(name = "dni_conyuge2", nullable = false)
    private Persona conyuge2;

    private LocalDate fechaMatrimonio;
}