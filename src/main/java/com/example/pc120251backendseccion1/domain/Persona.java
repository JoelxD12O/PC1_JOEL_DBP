package com.example.pc120251backendseccion1.domain;

import com.example.pc120251backendseccion1.enums.EstadoCivil;
import com.example.pc120251backendseccion1.enums.Sexo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Persona {
    @Id
    @Column(length = 8)
    private String dni; // clave primaria, inmutable

    private String nombres;

    private String apellidos;

    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Enumerated(EnumType.STRING)
    private EstadoCivil estadoCivil;

    @Column(unique = true)
    private String correo;

    // Relaciones recursivas: padre y madre también son personas
    @ManyToOne
    @JoinColumn(name = "dni_padre")
    private Persona padre;

    @ManyToOne
    @JoinColumn(name = "dni_madre")
    private Persona madre;
}
