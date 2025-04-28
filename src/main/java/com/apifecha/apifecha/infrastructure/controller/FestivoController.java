package com.apifecha.apifecha.infrastructure.controller;

import com.apifecha.apifecha.application.service.FestivoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/festivo")
public class FestivoController {

    private final FestivoService festivoService;

    public FestivoController(FestivoService festivoService) {
        this.festivoService = festivoService;
    }

    @GetMapping
    public ResponseEntity<String> esFestivo(@RequestParam String fecha) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaLocalDate = LocalDate.parse(fecha, formatter);

            boolean esFestivo = festivoService.esFestivo(fechaLocalDate);

            if (esFestivo) {
                return ResponseEntity.ok("Es festivo");
            } else {
                return ResponseEntity.ok("No es festivo");
            }
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Fecha inválida");
        }
    }
}
