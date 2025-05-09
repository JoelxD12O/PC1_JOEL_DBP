package com.example.pc120251backendseccion1.dto;

import com.example.pc120251backendseccion1.enums.EstadoCivil;
import lombok.Data;

@Data
public class ActualizarEstadoCivilRequest {
    private EstadoCivil estadoCivil;
}
