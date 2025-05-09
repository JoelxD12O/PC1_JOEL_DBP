package com.example.pc120251backendseccion1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidarMatrimonioResponse {
    private boolean permitido;
    private String mensaje;
}
