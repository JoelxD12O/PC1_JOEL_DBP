package com.example.pc120251backendseccion1.dto;

import com.example.pc120251backendseccion1.enums.EstadoCivil;
import com.example.pc120251backendseccion1.enums.Sexo;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreatePersonaRequest {
    private String dni;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private Sexo sexo;
    private EstadoCivil estadoCivil;
    private String correo;
}
