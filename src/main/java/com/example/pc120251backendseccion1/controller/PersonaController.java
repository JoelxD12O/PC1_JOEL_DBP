package com.example.pc120251backendseccion1.controller;

import com.example.pc120251backendseccion1.dto.CrearPersonaRequest;
import com.example.pc120251backendseccion1.dto.PersonaDTO;
import com.example.pc120251backendseccion1.domain.Persona;
import com.example.pc120251backendseccion1.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @GetMapping
    public List<PersonaDTO> listarPersonas() {
        return personaService.listarTodos().stream().map(this::mapToDto).collect(toList());
    }

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
}