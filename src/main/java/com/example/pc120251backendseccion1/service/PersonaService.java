package com.example.pc120251backendseccion1.service;
import com.example.pc120251backendseccion1.enums.EstadoCivil;
import com.example.pc120251backendseccion1.domain.Persona;
import com.example.pc120251backendseccion1.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository repo;

    public void crearPersona(Persona persona) {
        repo.save(persona);
    }

    public Optional<Persona> buscarPorDni(String dni) {
        return repo.findById(dni);
    }

    public List<Persona> listarTodos() {
        return repo.findAll();
    }

    public List<Persona> buscarPorNombreOApellido(String texto) {
        return repo.findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase(texto, texto);
    }

    public List<Persona> filtrarPorEstadoCivil(EstadoCivil estadoCivil) {
        return repo.findByEstadoCivil(estadoCivil);
    }

    public void actualizarNombresYApellidos(String dni, String nuevosNombres, String nuevosApellidos) {
        Persona persona = repo.findById(dni).orElseThrow();
        persona.setNombres(nuevosNombres);
        persona.setApellidos(nuevosApellidos);
        repo.save(persona);
    }

    public void actualizarPadres(String dni, Persona padre, Persona madre) {
        Persona persona = repo.findById(dni).orElseThrow();
        persona.setPadre(padre);
        persona.setMadre(madre);
        repo.save(persona);
    }

    public void actualizarEstadoCivil(String dni, EstadoCivil estadoCivil) {
        Persona persona = repo.findById(dni).orElseThrow();
        persona.setEstadoCivil(estadoCivil);
        repo.save(persona);
    }

    public void eliminarPersona(String dni) {
        List<Persona> descendientes = repo.findDescendientes(dni);
        if (!descendientes.isEmpty()) {
            throw new RuntimeException("No se puede eliminar: la persona tiene descendientes.");
        }
        repo.deleteById(dni);
    }
}