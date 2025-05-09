package com.example.pc120251backendseccion1.controller;

import com.example.pc120251backendseccion1.dto.RegistrarMatrimonioRequest;
import com.example.pc120251backendseccion1.dto.ValidarMatrimonioResponse;
import com.example.pc120251backendseccion1.exception.MatrimonioNoPermitidoException;
import com.example.pc120251backendseccion1.service.MatrimonioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
// @PreAuthorize("hasAnyRole('ADMIN', 'REGISTRADOR')") // Seguridad comentada para activar más tarde
public class MatrimonioController {

    @Autowired
    private MatrimonioService matrimonioService;

    // 7. POST /matrimonios - Registrar matrimonio entre dos personas
    @PostMapping("/matrimonios")
    public void registrarMatrimonio(@RequestBody RegistrarMatrimonioRequest request) {
        matrimonioService.registrarMatrimonio(request.getDni1(), request.getDni2());
    }

    // 8. GET /matrimonios/validar/{dni1}/{dni2} - Validar impedimentos matrimoniales
    @GetMapping("/matrimonios/validar/{dni1}/{dni2}")
    public ValidarMatrimonioResponse validarMatrimonio(@PathVariable String dni1, @PathVariable String dni2) {
        boolean permitido = matrimonioService.validarMatrimonio(dni1, dni2);
        String mensaje = permitido ? "El matrimonio entre estas dos personas es permitido."
                : "No se puede casar: consanguinidad o impedimento legal.";

        return new ValidarMatrimonioResponse(permitido, mensaje);
    }
}
