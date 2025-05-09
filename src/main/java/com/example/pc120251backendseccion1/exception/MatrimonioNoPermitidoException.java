package com.example.pc120251backendseccion1.exception;

public class MatrimonioNoPermitidoException extends RuntimeException {
    public MatrimonioNoPermitidoException(String motivo) {
        super("Matrimonio no permitido: " + motivo);
    }
}