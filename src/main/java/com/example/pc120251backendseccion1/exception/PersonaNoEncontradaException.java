package com.example.pc120251backendseccion1.exception;

public class PersonaNoEncontradaException extends RuntimeException {
    public PersonaNoEncontradaException(String dni) {
        super("No se encontró la persona con DNI: " + dni);
    }
}