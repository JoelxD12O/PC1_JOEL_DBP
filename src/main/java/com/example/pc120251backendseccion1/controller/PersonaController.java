package com.example.pc120251backendseccion1.controller;

import com.example.pc120251backendseccion1.dto.*;
import com.example.pc120251backendseccion1.enums.EstadoCivil;
import com.example.pc120251backendseccion1.domain.Persona;
import com.example.pc120251backendseccion1.exception.PersonaNoEncontradaException;
import com.example.pc120251backendseccion1.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
// @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRADOR', 'CONSULTOR')") // Activar esto en prod
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    // 1. POST /personas - Crear persona
    // @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRADOR')")
    @PostMapping("/personas")
    public void crearPersona(@RequestBody CrearPersonaRequest request) {
        Persona persona = new Persona();
        persona.setDni(request.getDni());
        persona.setNombres(request.getNombres());
        persona.setApellidos(request.getApellidos());
        persona.setFechaNacimiento(request.getFechaNacimiento());
        persona.setSexo(request.getSexo());
        persona.setEstadoCivil(request.getEstadoCivil());
        persona.setCorreo(request.getCorreo());
        personaService.crearPersona(persona);
    }

    // 2. GET /personas/{dni} - Consultar persona por DNI
    // @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRADOR', 'CONSULTOR')")
    @GetMapping("/personas/{dni}")
    public PersonaDTO obtenerPorDni(@PathVariable String dni) {
        Persona persona = personaService.buscarPorDni(dni)
                .orElseThrow(() -> new PersonaNoEncontradaException(dni));
        return mapToDto(persona);
    }

    // 3. PUT /personas/{dni}/nombres - Actualizar nombres y apellidos
    // @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRADOR')")
    @PutMapping("/personas/{dni}/nombres")
    public void actualizarNombre(@PathVariable String dni, @RequestBody ActualizarNombreRequest request) {
        personaService.actualizarNombresYApellidos(dni, request.getNombres(), request.getApellidos());
    }

    // 4. PUT /personas/{dni}/padres - Registrar padres
    // @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRADOR')")
    @PutMapping("/personas/{dni}/padres")
    public void actualizarPadres(@PathVariable String dni, @RequestBody RegistrarPadresRequest request) {
        Persona padre = request.getDniPadre() != null ? personaService.buscarPorDni(request.getDniPadre()).orElse(null) : null;
        Persona madre = request.getDniMadre() != null ? personaService.buscarPorDni(request.getDniMadre()).orElse(null) : null;
        personaService.actualizarPadres(dni, padre, madre);
    }

    // 5. DELETE /personas/{dni} - Eliminar persona (si no tiene descendientes)
    // @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/personas/{dni}")
    public void eliminarPersona(@PathVariable String dni) {
        personaService.eliminarPersona(dni);
    }

    // 6. GET /personas?nombre=xxx&estadoCivil=xxx - Listar con filtros opcionales
    // @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRADOR', 'CONSULTOR')")
    @GetMapping("/personas")
    public List<PersonaDTO> listarPersonas(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) EstadoCivil estadoCivil
    ) {
        List<Persona> personas;

        if (nombre != null) {
            personas = personaService.buscarPorNombreOApellido(nombre);
        } else if (estadoCivil != null) {
            personas = personaService.filtrarPorEstadoCivil(estadoCivil);
        } else {
            personas = personaService.listarTodos();
        }

        return personas.stream().map(this::mapToDto).collect(toList());
    }

    // Mapper DTO
    private PersonaDTO mapToDto(Persona p) {
        PersonaDTO dto = new PersonaDTO();
        dto.setDni(p.getDni());
        dto.setNombres(p.getNombres());
        dto.setApellidos(p.getApellidos());
        dto.setFechaNacimiento(p.getFechaNacimiento());
        dto.setSexo(p.getSexo());
        dto.setEstadoCivil(p.getEstadoCivil());
        dto.setCorreo(p.getCorreo());
        dto.setDniPadre(p.getPadre() != null ? p.getPadre().getDni() : null);
        dto.setDniMadre(p.getMadre() != null ? p.getMadre().getDni() : null);
        return dto;
    }
}
